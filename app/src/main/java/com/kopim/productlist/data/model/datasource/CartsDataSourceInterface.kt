package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow

interface CartsDataSourceInterface {
    fun getUserCarts(): Flow<List<ShortCartData>?>

    fun addUserToCart(cartCode: String)

    fun removeUserFromCart(cartId: Long)

    fun renameCart(cartId: Long, newName: String)
}