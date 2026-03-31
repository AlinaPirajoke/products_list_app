package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.flow.Flow

interface CartsDataSourceInterface {
    fun getUserCarts(): Flow<List<ShortCartData>>

    suspend fun joinCartByInviteCode(cartCode: String): Result<Long>

    suspend fun leaveCart(cartId: Long): Result<Unit>

    suspend fun renameCart(cartId: Long, newName: String): Result<Unit>
}
