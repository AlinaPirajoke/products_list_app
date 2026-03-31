package com.kopim.productlist.data.model.network.apimodels.addusertocart

/**
 * Тело ответа add_user_to_cart: бэкенд может вернуть пустой объект или поля с id корзины.
 */
data class AddUserToCartResponseData(
    val cart_id: Long? = null,
    val id: Long? = null,
) {
    fun resolvedCartId(): Long? = cart_id ?: id
}
