package com.kopim.productlist.data.mvvm.list

/**
 * Заголовок списка на экране корзины. Реализация с API/БД подключается в DI.
 */
data class CartListHeader(
    val title: String?,
)

fun interface ListCartMetadataPort {
    suspend fun loadCartHeader(listId: Long): Result<CartListHeader>
}
