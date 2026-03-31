package com.kopim.productlist.data.model.network.connections.carts

import com.kopim.productlist.data.model.network.apimodels.addusertocart.AddUserToCartResponseData
import com.kopim.productlist.data.model.network.apimodels.getcartinfo.GetCartInfoResponseData
import com.kopim.productlist.data.model.network.apimodels.getcarts.GetCartsResponseData
import retrofit2.Response

interface CartsNetworkConnectionInterface {

    suspend fun getUserCarts(): Response<GetCartsResponseData>?

    suspend fun getCartInfo(cartId: Long): Response<GetCartInfoResponseData>?

    suspend fun addUserToCart(cartCode: String): Response<AddUserToCartResponseData>?

    suspend fun removeUserFromCart(cartId: Long): Response<Unit>?

    suspend fun renameCart(cartId: Long, newName: String): Response<Unit>?
}