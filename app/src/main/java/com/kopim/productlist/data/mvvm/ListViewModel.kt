package com.kopim.productlist.data.mvvm

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.model.datasource.ListDataSource
import com.kopim.productlist.data.model.datasource.ListDataSourceInterface
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ListScreenMode
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val ADDITION_LIST_DELAY = 150L
private const val TAG = "ListViewModel"

class ListViewModel(val dataSource: ListDataSourceInterface) : BaseViewModel() {
    private val _state = MutableStateFlow(ListUiState())
    val state: StateFlow<ListUiState> = _state.asStateFlow()

    private var listId: Long? = null
    private var cartObserverJob: Job? = null

    fun setListId(newListId: Long) {
        listId = newListId
    }

    private fun updateHints(query: String) {
        viewModelScope.launch {
            val dataFlow = dataSource.getHints(query)

            dataFlow.collect { data ->
                data?.let {
                    Log.i(TAG, "New hints: $it")
                    _state.update { state ->
                        state.copy(
                            newProductHints = it
                        )
                    }
                }
            }
        }
    }

    private fun updateCart(newCartData: ProductListData) {
        val updatedData = newCartData.usualItems.map { newItem ->
            val sameItem = state.value.cart.firstOrNull { oldItem -> oldItem.id == newItem.id }
            sameItem?.let {
                return@map sameItem.withUpdatedData(newItem)
            }
            return@map newItem.toProductUiData()
        }
        Log.d(TAG, "Updated cart: ${updatedData.map { it.id to it.text }}")

        _state.update { state ->
            state.copy(
                cart = updatedData,
                localProducts = newCartData.localItems
            )
        }
    }

    fun subscribeOnList() {
        cartObserverJob = viewModelScope.launch {
            listId?.let { id ->
                val dataFlow =
                    dataSource.listSubscribe(id, ListDataSource.ACTIVE_UPDATE_DELAY + 30_000L)
                Log.d(TAG, "Subscribed")
                dataFlow.onEach {
                    Log.i(TAG, "New cart data: $it")
                    it?.let {
                        updateCart(it)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun updateList() {
        viewModelScope.launch {
            Log.d(TAG, "Update list")
            listId?.let { id ->
                dataSource.fetchUpdates(id)
            }
        }
    }

    fun unsubscribeOnList() {
        dataSource.listUnsubscribe()
        cartObserverJob?.cancel()
    }

    fun selectItem(itemId: Long, picked: Boolean? = null) {
        val newCart = state.value.cart.toMutableList()
        val pickedElementIndex = newCart.indexOfFirst { it.id == itemId }
        val pickedElement = newCart[pickedElementIndex]

        newCart[pickedElementIndex] = pickedElement.copy(picked = picked ?: !pickedElement.picked)

        _state.update { state ->
            state.copy(
                cart = newCart
            )
        }
    }

    fun onNewProductFieldValueChange(newValue: String) =
        onNewProductFieldValueChange(TextFieldValue(newValue))


    fun onNewProductFieldValueChange(newValue: TextFieldValue) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    newProductFieldValue = newValue
                )
            }
            updateHints(newValue.text)
        }
    }

    fun shiftNewProductFieldCursor(position: Int) {
        _state.update { state ->
            state.copy(
                newProductFieldValue = state.newProductFieldValue.copy(
                    selection = TextRange(position)
                )
            )
        }
    }

    fun onNewProductConfirm(productName: String?) {
        listId?.let { id ->
            viewModelScope.launch {
                dataSource.addChange(
                    change = LocalChange.AdditionChange(
                        name = productName ?: state.value.newProductFieldValue.text,
                        cartId = id
                    ),
                    listId = id
                )
                delay(ADDITION_LIST_DELAY)
                onNewProductFieldValueChange("")
                updateList()
            }
        }
    }

    fun onHintPick(hint: Hint) {
        onNewProductFieldValueChange(hint.name)
        shiftNewProductFieldCursor(state.value.newProductFieldValue.text.length)
    }

    fun enterAdditionMode() {
        _state.update { state ->
            state.copy(
                screenMode = ListScreenMode.AdditionMode
            )
        }
        updateHints(state.value.newProductFieldValue.text)
    }

    fun enterCartMode() {
        _state.update { state ->
            state.copy(
                screenMode = ListScreenMode.CartMode
            )
        }
    }

    fun onCheck(itemId: Long) {
        viewModelScope.launch {
            state.value.cart.firstOrNull { it.id == itemId }?.let { item ->
                dataSource.addChange(
                    change = LocalChange.CheckChange(
                        checked = !item.lineTrough,
                        itemId = itemId
                    ),
                    listId = listId ?: return@launch
                )
            }
            selectItem(itemId, false)
            updateList()
        }
    }
}