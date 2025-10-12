package com.kopim.productlist.data.model.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

suspend fun getFcmToken(): String? {
    return try {
        FirebaseMessaging.getInstance().token.await()
    } catch (e: Exception) {
        Log.e("fcm", "Token getting error", e)
        null
    }
}