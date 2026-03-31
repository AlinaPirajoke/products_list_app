package com.kopim.productlist.data.model.carts

import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnectionInterface
import com.kopim.productlist.data.mvvm.editlist.EditListDraft
import com.kopim.productlist.data.mvvm.editlist.EditListPort

class EditListPortImpl(
    private val nc: CartsNetworkConnectionInterface,
    private val cartsDataSource: CartsDataSourceInterface,
) : EditListPort {

    override suspend fun loadEditState(listId: Long): Result<EditListDraft> {
        val r = nc.getCartInfo(listId) ?: return Result.failure(Exception("Нет ответа сервера"))
        if (!r.isSuccessful) {
            return Result.failure(Exception(r.message()))
        }
        val body = r.body() ?: return Result.failure(Exception("Пустой ответ"))
        return Result.success(
            EditListDraft(
                title = body.name,
                shareCode = body.code,
            )
        )
    }

    override suspend fun updateTitle(listId: Long, title: String): Result<Unit> =
        cartsDataSource.renameCart(listId, title)

    override suspend fun leaveList(listId: Long): Result<Unit> =
        cartsDataSource.leaveCart(listId)
}
