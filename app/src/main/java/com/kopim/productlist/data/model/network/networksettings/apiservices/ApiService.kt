package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.login.LoginResponseData
import com.kopim.productlist.data.model.network.apimodels.login.LoginWithCredentialsRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    /** Анонимная выдача токена (новый гость). Заголовок [X-Auth-Token] не обязателен. */
    @GET("login")
    suspend fun login(): Response<LoginResponseData>

    /** Токен по ID и паролю; без действующего токена или с игнорированием старого на клиенте. */
    @POST("login_with_credentials")
    suspend fun loginWithCredentials(
        @Body body: LoginWithCredentialsRequestData,
    ): Response<LoginResponseData>
}