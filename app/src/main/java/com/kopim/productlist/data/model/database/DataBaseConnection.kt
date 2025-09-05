package com.kopim.productlist.data.model.database

import android.util.Log
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.model.database.entities.dtos.ListProductsDTO.Companion.toProductListData
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import com.kopim.productlist.data.utils.TimeHelper

const val TAG = "DataBaseConnection"

class DataBaseConnection(val database: AppDatabase) {
    suspend fun getProductHints(query: String): List<Hint> =
        database.productDao().getProductsByKey(query).map {
            Log.i(TAG, "Getting hints $it")
            it.toHint()
        }

    suspend fun updateProducts(hints: List<Hint>) {
        Log.i(TAG, "Updating hints $hints")
        database.productDao().upsertAll(hints.map { it.toProductDbEntity() })
    }

    suspend fun getCart(cartId: Int): ProductListData {
        return database.listItemDao().getItemsByCartAfterDate(cartId, TimeHelper.getYesterdayDate())
            .toProductListData()
    }

    suspend fun updateCart(data: ProductListData, cartId: Int) {
        Log.i(TAG, "Updating cart $data")
        data.items.forEach {
            database.cartDao().upsert(CartDbEntity(cartId.toLong(), null))
            database.productDao().upsert(it.toProductDbEntity())
            database.listItemDao().upsert(it.toListItemDbEntity(cartId.toLong(), it.productId.toLong()))
        }
    }
}