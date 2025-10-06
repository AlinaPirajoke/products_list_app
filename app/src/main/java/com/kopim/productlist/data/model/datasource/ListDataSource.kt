package com.kopim.productlist.data.model.datasource

import android.util.Log
import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
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

private const val TAG = "ListDataSource"

class ListDataSource(
    private val dbc: DatabaseConnectionInterface,
    private val nc: ListNetworkConnectionInterface
) : ListDataSourceInterface {

    private var cartObserverJob: Job? = null

    private suspend fun synchronize(listId: Long) {
        Log.d(TAG, "Synchronizing")
        pushUpdates()
        fetchUpdates(listId)
    }

    private suspend fun pushNewProducts(products: List<NewProductData>) =
        nc.addProducts(products)


    private suspend fun pushCheckedProducts(items: List<CheckedProductData>) =
        nc.checkProduct(items)


    private suspend fun fetchProductsFromDatabase(listId: Long) {
        Log.d(TAG, "Fetching products from database")
        nc.lastCartData.emit(dbc.getCartWithUpdates(listId))
    }

    private suspend fun fetchProductsFromNetwork(listId: Long) {
        Log.d(TAG, "Fetching products from network")
        nc.getCart(listId)
    }

    override suspend fun listSubscribe(id: Long, updateDelay: Long): StateFlow<ProductListData?> {
        Log.i(TAG, "Subscribing on list")
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
        fetchProductsFromNetwork(id)

        return nc.lastCartData.asStateFlow()
    }

    override fun listUnsubscribe(): Unit {
        cartObserverJob?.cancel()
        Log.i(TAG, "Unsubscribed from list")
    }

    override suspend fun addChange(change: LocalChange, listId: Long) {
        Log.d(TAG, "Adding change: $change")
        when (change) {
            is LocalChange.AdditionChange -> dbc.addChange(change)
            is LocalChange.RenameChange -> dbc.addChange(change)
            is LocalChange.CheckChange -> dbc.addChange(change)
        }
        pushUpdates()
    }

    override suspend fun fetchUpdates(id: Long) {
        Log.w(TAG, "Fetching updates")
        fetchProductsFromDatabase(id)
        fetchProductsFromNetwork(id)
    }

    override suspend fun pushUpdates() {
        Log.w(TAG, "Pushing updates")
        pushNewProducts(
            dbc.getAdditionChanges()
                .map { it.toNewProductData() })?.let { if (it.isSuccessful) dbc.removeAdditionChanges() }
        pushCheckedProducts(
            dbc.getCheckChanges()
                .map { it.toCheckedProductData() })?.let { if (it.isSuccessful) dbc.removeCheckChanges() }
    }

    override suspend fun getHints(query: String): MutableStateFlow<List<Hint>?> {
        Log.d(TAG, "Getting hints")
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
        const val ACTIVE_UPDATE_DELAY = 20_000L
        const val PASSIVE_UPDATE_DELAY = 150_000L
    }
}