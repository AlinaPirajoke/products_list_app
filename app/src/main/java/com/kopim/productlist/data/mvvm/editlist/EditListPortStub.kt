package com.kopim.productlist.data.mvvm.editlist

/**
 * Заглушка: пустые поля, успешный выход и сохранение названия без побочных эффектов.
 */
object EditListPortStub : EditListPort {
    override suspend fun loadEditState(listId: Long): Result<EditListDraft> =
        Result.success(EditListDraft(title = "", shareCode = "", members = emptyList()))

    override suspend fun updateTitle(listId: Long, title: String): Result<Unit> = Result.success(Unit)

    override suspend fun leaveList(listId: Long): Result<Unit> = Result.success(Unit)
}
