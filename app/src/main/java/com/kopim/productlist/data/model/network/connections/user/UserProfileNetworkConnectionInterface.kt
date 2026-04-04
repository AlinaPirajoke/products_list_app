package com.kopim.productlist.data.model.network.connections.user

import com.kopim.productlist.data.model.network.apimodels.login.LoginResponseData
import com.kopim.productlist.data.model.network.apimodels.profile.ProfileResponseData
import retrofit2.Response

interface UserProfileNetworkConnectionInterface {

    suspend fun getUserProfile(): Response<ProfileResponseData>?

    suspend fun updateUserProfile(
        displayName: String? = null,
        profileColor: String? = null,
    ): Response<ProfileResponseData>?

    suspend fun changeUserPassword(newPassword: String): Response<Unit>?

    /** Повторная анонимная авторизация (новый токен), как при первом запуске. */
    suspend fun relogin(): Boolean

    /** Запрос токена по ID и паролю без использования сохранённого токена. */
    suspend fun loginWithCredentials(userId: String, password: String): Response<LoginResponseData>?
}
