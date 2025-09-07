package com.kopim.productlist.data.model.network

import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.flow.MutableStateFlow

interface ListNetworkConnectionInterface {
    val lastCartData: MutableStateFlow<ProductListData?>

    suspend fun getCart(cartId: Long)

    suspend fun getHints(key: String): List<Hint>?

    suspend fun addProduct(listId: Long, product: String)

    suspend fun checkProduct(itemId: Long, checked: Boolean)
}