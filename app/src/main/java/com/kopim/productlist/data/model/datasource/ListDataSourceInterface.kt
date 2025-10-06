package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.MVItry.mvistuff.list.ListStore
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData
import com.kopim.productlist.data.utils.LocalChange

interface ListDataSourceInterface: ListStore.ListDataManager {
    suspend fun addChange(change: LocalChange, listId: Long)
}