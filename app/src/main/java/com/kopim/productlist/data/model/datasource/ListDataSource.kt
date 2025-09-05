package com.kopim.productlist.data.model.datasource

import android.util.Log
import com.kopim.productlist.data.model.database.DataBaseConnection
import com.kopim.productlist.data.model.network.ListNetworkConnection
import com.kopim.productlist.data.utils.Hint
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

class ListDataSource(private val dbc: DataBaseConnection, private val nc: ListNetworkConnection) :
    ListDataSourceInterface {

    private var cartObserverJob: Job? = null

    override suspend fun listSubscribe(id: Int, updateDelay: Long): StateFlow<ProductListData?> {
        cartObserverJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                listUpdate(id)
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

    override suspend fun listUpdate(id: Int) {
        nc.getCart(id)
    }

    override suspend fun addProduct(product: String, listId: Int): Unit {
        nc.addProduct(listId, product)
    }

    override suspend fun checkProduct(id: Int, checked: Boolean): Unit {
        nc.checkProduct(id, checked)
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