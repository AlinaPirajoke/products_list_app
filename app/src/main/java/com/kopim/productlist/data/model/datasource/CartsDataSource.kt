package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow

class CartsDataSource: CartsDataSourceInterface {
    override fun getUserCarts(): Flow<ShortCartData> {
        TODO("Not yet implemented")
    }

    override fun addUserToCart(cartCode: String) {
        TODO("Not yet implemented")
    }

    override fun removeUserFromCart(cartId: Long) {
        TODO("Not yet implemented")
    }

    override fun renameCart(newName: String) {
        TODO("Not yet implemented")
    }

}