package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.login.LoginResponseData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("login")
    suspend fun login(): Response<LoginResponseData>
}