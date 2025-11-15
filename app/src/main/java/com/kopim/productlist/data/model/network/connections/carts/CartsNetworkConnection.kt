package com.kopim.productlist.data.model.network.connections.carts

import com.kopim.productlist.data.utils.ShortCartData
import retrofit2.Response

class CartsNetworkConnection: CartsNetworkConnectionInterface {
    override fun getUserCarts(): Response<List<ShortCartData>>? {
        TODO("Not yet implemented")
    }

    override fun addUserToCart(cartCode: String): Response<ShortCartData>? {
        TODO("Not yet implemented")
    }

    override fun removeUserFromCart(cartId: Long): Response<Unit>? {
        TODO("Not yet implemented")
    }

    override fun renameCart(newName: String): Response<Unit>? {
        TODO("Not yet implemented")
    }
}