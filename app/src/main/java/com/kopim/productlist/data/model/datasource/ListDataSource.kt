package com.kopim.productlist.data.model.datasource

import android.util.Log
import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
import com.kopim.productlist.data.model.database.utils.ChangeType
import com.kopim.productlist.data.model.network.ListNetworkConnectionInterface
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

const val TAG = "ListDataSource"

class ListDataSource(
    private val dbc: DatabaseConnectionInterface,
    private val nc: ListNetworkConnectionInterface
) :
    ListDataSourceInterface {

    private var cartObserverJob: Job? = null

    private suspend fun synchronize(listId: Long) {
        pushUpdates()
        fetchUpdates(listId)
    }

    override suspend fun listSubscribe(id: Long, updateDelay: Long): StateFlow<ProductListData?> {
        cartObserverJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                synchronize(id)
                delay(updateDelay)
            }
        }
        Log.i(TAG, "Subscribed on list")

        CoroutineScope(Dispatchers.IO).launch {
            nc.lastCartData.collect {
                it?.let {
                    dbc.updateCart(it, id)
                }
            }
        }
        nc.lastCartData.emit(dbc.getCart(id))

        return nc.lastCartData.asStateFlow()
    }

    override fun listUnsubscribe(): Unit {
        cartObserverJob?.cancel()
        Log.i(TAG, "Unsubscribed from list")
    }

    override suspend fun addChange(change: LocalChange) {
        dbc.addChange(change)
    }

    override suspend fun fetchUpdates(id: Long) {
        nc.getCart(id)
    }

    override suspend fun pushUpdates() {
        val changes = dbc.getChanges()
        if (changes.isNotEmpty()) {
            addProducts(changes.filter { it.changeType == ChangeType.Create }
                .map { it.toNewProductData() })
            checkProduct(changes.filter { it.changeType == ChangeType.Check }
                .map { it.toCheckedProductData() }
            )
        }
    }

    override suspend fun addProducts(products: List<NewProductData>) {
        nc.addProducts(products)
    }

    override suspend fun checkProduct(items: List<CheckedProductData>) {
        nc.checkProduct(items)
    }

    override suspend fun getHints(query: String): MutableStateFlow<List<Hint>?> {
        val coroutineContext = currentCoroutineContext()
        val resultsFlow = MutableStateFlow<List<Hint>?>(null)
        CoroutineScope(Dispatchers.IO).launch {
            resultsFlow.emit(dbc.getProductHints(query))
            resultsFlow.emit(nc.getHints(query)?.also { dbc.updateProducts(it) })
            coroutineContext.cancel()
        }
        return resultsFlow
    }

    companion object {
        const val ACTIVE_UPDATE_DELAY = 15_000L
        const val PASSIVE_UPDATE_DELAY = 150_000L
    }
}