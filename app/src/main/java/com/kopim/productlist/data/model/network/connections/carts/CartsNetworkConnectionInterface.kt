package com.kopim.productlist.data.model.network.connections.carts

import com.kopim.productlist.data.model.network.apimodels.getcarts.GetCartsResponseData
import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

interface CartsNetworkConnectionInterface {

    suspend fun getUserCarts(): Response<GetCartsResponseData>?

    suspend fun addUserToCart(cartCode: String): Response<Unit>?

    suspend fun removeUserFromCart(cartId: Long): Response<Unit>?

    suspend fun renameCart(cartId: Long, newName: String): Response<Unit>?
}