package com.kopim.productlist.data.model.database

import android.util.Log
import androidx.lifecycle.liveData
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.model.database.entities.dtos.ListProductsDTO.Companion.toProductListData
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import com.kopim.productlist.data.utils.TimeHelper

const val TAG = "DataBaseConnection"

class DataBaseConnection(val database: AppDatabase): DatabaseConnectionInterface {
    override suspend fun getProductHints(query: String): List<Hint> =
        database.productDao().getProductsByKey(query).map {
            Log.i(TAG, "Getting hints $it")
            it.toHint()
        }

    override suspend fun updateProducts(hints: List<Hint>) {
        Log.i(TAG, "Updating hints $hints")
        database.productDao().upsertAll(hints.map { it.toProductDbEntity() })
    }

    override suspend fun getCart(cartId: Long): ProductListData {
        return database.listItemDao().getItemsByCartAfterDate(cartId, TimeHelper.getYesterdayDate())
            .toProductListData()
    }

    override suspend fun updateCart(data: ProductListData, cartId: Long) {
        Log.i(TAG, "Updating cart $data")
        database.listItemDao().cleanCart(cartId)
        data.items.forEach {
            database.cartDao().upsert(CartDbEntity(cartId.toLong(), null))
            database.productDao().upsert(it.toProductDbEntity())
            database.listItemDao().upsert(it.toListItemDbEntity(cartId.toLong(), it.productId.toLong()))
        }
    }
}