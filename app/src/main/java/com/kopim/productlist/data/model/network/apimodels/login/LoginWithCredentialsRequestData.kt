package com.kopim.productlist.data.model.network.apimodels.login

/**
 * Тело запроса на выдачу токена по ID и паролю ([ApiService.loginWithCredentials]).
 */
data class LoginWithCredentialsRequestData(
    val user_id: String,
    val password: String,
)
