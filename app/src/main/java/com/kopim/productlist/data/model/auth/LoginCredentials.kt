package com.kopim.productlist.data.model.auth

/**
 * Учётные данные для входа по ID и паролю (доменная модель, не сериализуется напрямую).
 */
data class LoginCredentials(
    val userId: String,
    val password: String,
) {
    val trimmedUserId: String get() = userId.trim()
}
