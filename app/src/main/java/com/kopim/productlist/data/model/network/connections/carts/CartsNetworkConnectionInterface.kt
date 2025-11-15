package com.kopim.productlist.data.model.network.connections.carts

import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CartsNetworkConnectionInterface {
    fun getUserCarts(): Response<List<ShortCartData>>?

    fun addUserToCart(cartCode: String): Response<ShortCartData>?

    fun removeUserFromCart(cartId: Long): Response<Unit>?

    fun renameCart(newName: String): Response<Unit>?
}