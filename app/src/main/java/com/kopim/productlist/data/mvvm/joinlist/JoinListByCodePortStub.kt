package com.kopim.productlist.data.mvvm.joinlist

/**
 * Заглушка до появления сетевой реализации.
 */
object JoinListByCodePortStub : JoinListByCodePort {
    override suspend fun joinByInviteCode(trimmedCode: String): Result<Long> =
        Result.failure(
            NotImplementedError("JoinListByCodePort.joinByInviteCode — подключите реализацию в DI")
        )
}
