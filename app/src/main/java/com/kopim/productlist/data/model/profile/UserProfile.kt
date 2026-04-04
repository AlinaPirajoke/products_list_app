package com.kopim.productlist.data.model.profile

/**
 * Профиль пользователя (доменная модель, без пароля).
 */
data class UserProfile(
    val userId: String,
    val displayName: String,
    /** Строка цвета, как `user_color` в API корзины. */
    val profileColor: String,
)
