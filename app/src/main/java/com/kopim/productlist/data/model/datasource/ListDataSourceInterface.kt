package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ListDataSourceInterface {
    suspend fun addChange(change: LocalChange, listId: Long)
    suspend fun pushUpdates(): Unit
    suspend fun listSubscribe(id: Long, updateDelay: Long): StateFlow<ProductListData?>
    fun listUnsubscribe(): Unit
    suspend fun fetchUpdates(id: Long): Unit
    suspend fun getHints(query: String): MutableStateFlow<List<Hint>?>
}