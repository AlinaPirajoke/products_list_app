package com.kopim.productlist.data.model.network.connections

import android.util.Log
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.networksettings.apiservices.ApiService
import okhttp3.Request

private const val TAG = "BaseNetworkConnection"

open class BaseNetworkConnection(
    protected open val connection: ApiService,
    protected val spm: SharedPreferencesManager
) {
    protected var token = spm.userToken

    protected suspend fun <T> safeRequest(body: suspend () -> T?): T? {
        try {
            return body()
        } catch (e: Exception) {
            Log.e(TAG, "Safety request failed", e)
            return null
        }
    }

    protected suspend fun checkLogin(): Boolean {
        if (token == null) {
            Log.w(TAG, "User's token is null!")
            return loginUser()
        }
        return true
    }

    suspend fun loginUser(): Boolean =
        safeRequest {
            val response = connection.login()
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    spm.userToken = token
                }
                Log.i(TAG, "New user token: $token")
                return@safeRequest true
            }
            Log.e(TAG, "Failed to login")
            return@safeRequest false
        } ?: false
}