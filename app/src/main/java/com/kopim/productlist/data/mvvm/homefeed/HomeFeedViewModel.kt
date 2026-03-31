package com.kopim.productlist.data.mvvm.homefeed

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.mvvm.BaseViewModel
import com.kopim.productlist.ui.navigation.CartNavPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeFeedViewModel(val dataSource: CartsDataSourceInterface) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeFeedUiState())
    val state: StateFlow<HomeFeedUiState> = _state.asStateFlow()

    init {
        refreshCarts()
    }

    fun onUpdateDate() {
        refreshCarts()
    }

    fun onNavigateToList(listId: Long) {
        navigate(CartNavPoint(listId))
    }

    private fun refreshCarts() {
        viewModelScope.launch {
            dataSource.getUserCarts().collect { lists ->
                _state.value = _state.value.copy(lists = lists)
            }
        }
    }
}