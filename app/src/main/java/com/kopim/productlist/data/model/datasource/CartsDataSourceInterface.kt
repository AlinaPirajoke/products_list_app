package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow

interface CartsDataSourceInterface {
    /**
     * Событие после изменения локального кэша списков корзин (присоединение, выход, переименование и т.д.).
     * Подписчики могут заново запросить [getUserCarts].
     */
    val cartsInvalidated: Flow<Unit>

    fun getUserCarts(): Flow<List<ShortCartData>>

    suspend fun joinCartByInviteCode(cartCode: String): Result<Long>

    suspend fun leaveCart(cartId: Long): Result<Unit>

    suspend fun renameCart(cartId: Long, newName: String): Result<Unit>
}
