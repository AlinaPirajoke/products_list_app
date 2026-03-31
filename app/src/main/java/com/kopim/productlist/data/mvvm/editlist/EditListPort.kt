package com.kopim.productlist.data.mvvm.editlist

/**
 * Контракт экрана настроек списка. Реализация на стороне модели подключается в DI.
 */
interface EditListPort {
    suspend fun loadEditState(listId: Long): Result<EditListDraft>
    suspend fun updateTitle(listId: Long, title: String): Result<Unit>
    suspend fun leaveList(listId: Long): Result<Unit>
}
