package com.kopim.productlist.data.model.network.apimodels.login

/**
 * Ответ эндпоинтов [com.kopim.productlist.data.model.network.networksettings.apiservices.ApiService.login]
 * и [com.kopim.productlist.data.model.network.networksettings.apiservices.ApiService.loginWithCredentials].
 * Токен сохраняется в [com.kopim.productlist.data.model.database.SharedPreferencesManager.userToken]
 * и далее передаётся заголовком `X-Auth-Token` ([com.kopim.productlist.data.model.network.networksettings.AuthInterceptor]).
 */
data class LoginResponseData(
    val token: String,
)
