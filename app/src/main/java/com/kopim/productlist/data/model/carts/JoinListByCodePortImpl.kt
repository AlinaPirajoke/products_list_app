package com.kopim.productlist.data.model.carts

import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.mvvm.joinlist.JoinListByCodePort

class JoinListByCodePortImpl(
    private val cartsDataSource: CartsDataSourceInterface,
) : JoinListByCodePort {

    override suspend fun joinByInviteCode(trimmedCode: String): Result<Long> =
        cartsDataSource.joinCartByInviteCode(trimmedCode)
}
