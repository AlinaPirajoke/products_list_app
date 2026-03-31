package com.kopim.productlist.data.mvvm.list

object ListCartMetadataPortStub : ListCartMetadataPort {
    override suspend fun loadCartHeader(listId: Long): Result<CartListHeader> =
        Result.success(CartListHeader(title = null))
}
