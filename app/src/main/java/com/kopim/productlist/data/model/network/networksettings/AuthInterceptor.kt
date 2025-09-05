package com.kopim.productlist.data.model.network.networksettings

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider()

        if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("X-Auth-Token", token)
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(originalRequest)
    }
}
