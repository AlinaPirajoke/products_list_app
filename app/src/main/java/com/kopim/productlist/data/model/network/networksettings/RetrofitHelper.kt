package com.kopim.productlist.data.model.network.networksettings

import com.kopim.productlist.data.model.network.networksettings.apiservices.FcmApiService
import com.kopim.productlist.data.model.network.networksettings.apiservices.ListApiService
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

    fun getListApiService(retrofit: Retrofit): ListApiService =
        retrofit.create(ListApiService::class.java)

    fun getFcmApiService(retrofit: Retrofit): FcmApiService =
        retrofit.create(FcmApiService::class.java)
}