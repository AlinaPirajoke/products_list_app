package com.kopim.productlist.data.model.database

import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData

interface DatabaseConnectionInterface {
    suspend fun getProductHints(query: String): List<Hint>

    suspend fun updateProducts(hints: List<Hint>)

    suspend fun getCart(cartId: Long): ProductListData

    suspend fun updateCart(data: ProductListData, cartId: Long)
}