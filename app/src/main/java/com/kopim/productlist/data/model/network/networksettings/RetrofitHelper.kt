package com.kopim.productlist.data.model.network.networksettings

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    const val BASE_URL = "https://vyacheslav-ruzmanov.ru/products_back/"

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}