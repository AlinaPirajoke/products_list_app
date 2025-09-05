package com.kopim.productlist.data.model.network.networksettings

import com.kopim.productlist.data.model.database.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientHelper() {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideOkHttpClient(spm: SharedPreferencesManager) =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(spm::userToken))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
}