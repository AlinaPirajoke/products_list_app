package com.kopim.productlist.data.mvvm.joinlist

/**
 * Контракт для присоединения к списку по коду. Реализация на стороне модели/сети подключается в DI.
 */
fun interface JoinListByCodePort {
    suspend fun joinByInviteCode(trimmedCode: String): Result<Long>
}
