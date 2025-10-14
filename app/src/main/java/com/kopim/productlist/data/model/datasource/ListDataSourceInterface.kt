package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.MVItry.mvistuff.list.ListStore
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.flow.MutableStateFlow

interface ListDataSourceInterface: ListStore.ListDataManager {
    suspend fun addChange(change: LocalChange, listId: Long)
}