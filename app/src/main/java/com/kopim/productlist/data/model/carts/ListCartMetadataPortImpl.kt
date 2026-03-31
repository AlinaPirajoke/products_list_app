package com.kopim.productlist.data.model.carts

import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnectionInterface
import com.kopim.productlist.data.mvvm.list.CartListHeader
import com.kopim.productlist.data.mvvm.list.ListCartMetadataPort

class ListCartMetadataPortImpl(
    private val nc: CartsNetworkConnectionInterface,
) : ListCartMetadataPort {

    override suspend fun loadCartHeader(listId: Long): Result<CartListHeader> {
        val r = nc.getCartInfo(listId) ?: return Result.failure(Exception("Нет ответа сервера"))
        if (!r.isSuccessful) {
            return Result.failure(Exception(r.message()))
        }
        val body = r.body() ?: return Result.failure(Exception("Пустой ответ"))
        return Result.success(CartListHeader(title = body.name))
    }
}
