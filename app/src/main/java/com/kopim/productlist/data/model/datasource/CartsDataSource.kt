package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnectionInterface
import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CartsDataSource(
    private val dbc: DatabaseConnectionInterface,
    private val nc: CartsNetworkConnectionInterface,
) : CartsDataSourceInterface {

    override fun getUserCarts(): Flow<List<ShortCartData>> = flow {
        emit(dbc.getCarts())
        val response = nc.getUserCarts()
        if (response?.isSuccessful == true) {
            val list = response.body()?.toShortCartDataList() ?: emptyList()
            dbc.addCartsData(list)
            emit(dbc.getCarts())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun joinCartByInviteCode(cartCode: String): Result<Long> = withContext(Dispatchers.IO) {
        val beforeIds = dbc.getCarts().map { it.id }.toSet()
        val resp = nc.addUserToCart(cartCode)
        if (resp?.isSuccessful != true) {
            return@withContext Result.failure(
                Exception(resp?.message() ?: "Не удалось присоединиться к списку")
            )
        }
        val fromBody = resp.body()?.resolvedCartId()
        if (fromBody != null) {
            persistCartsFromNetwork()
            return@withContext Result.success(fromBody)
        }
        if (!persistCartsFromNetwork()) {
            return@withContext Result.failure(Exception("Не удалось обновить списки"))
        }
        val list = dbc.getCarts()
        val afterIds = list.map { it.id }.toSet()
        val newIds = afterIds - beforeIds
        when {
            newIds.size == 1 -> Result.success(newIds.first())
            else -> {
                val byCode = list.find {
                    it.inviteCode.equals(cartCode, ignoreCase = true)
                }
                if (byCode != null) Result.success(byCode.id)
                else if (newIds.isEmpty()) {
                    Result.failure(Exception("Список не найден после присоединения"))
                } else {
                    Result.failure(Exception("Неоднозначный ответ сервера"))
                }
            }
        }
    }

    override suspend fun leaveCart(cartId: Long): Result<Unit> = withContext(Dispatchers.IO) {
        val resp = nc.removeUserFromCart(cartId)
        if (resp?.isSuccessful == true) {
            dbc.removeCart(cartId)
            persistCartsFromNetwork()
            Result.success(Unit)
        } else {
            Result.failure(Exception(resp?.message() ?: "Не удалось выйти из списка"))
        }
    }

    override suspend fun renameCart(cartId: Long, newName: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            val resp = nc.renameCart(cartId, newName)
            if (resp?.isSuccessful == true) {
                dbc.updateCartFeedName(cartId, newName)
                Result.success(Unit)
            } else {
                Result.failure(Exception(resp?.message() ?: "Не удалось переименовать список"))
            }
        }

    private suspend fun persistCartsFromNetwork(): Boolean {
        val r = nc.getUserCarts() ?: return false
        if (!r.isSuccessful) return false
        val list = r.body()?.toShortCartDataList() ?: emptyList()
        dbc.addCartsData(list)
        return true
    }
}
