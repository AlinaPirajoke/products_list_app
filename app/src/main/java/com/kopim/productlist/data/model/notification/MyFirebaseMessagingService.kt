package com.kopim.productlist.data.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kopim.productlist.R
import com.kopim.productlist.data.model.network.connections.fcm.FcmNetworkConnectionInterface
import com.kopim.productlist.ui.theme.deepBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.v(
            "fcm",
            "title=${message.notification?.title}" +
                    "\ntext=${message.notification?.body}"
        )

        val channel = NotificationChannel(
            "channel_id",
            "Answer Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.add)
            .setColorized(true)
            .setColor(deepBlue.toArgb())
            .build()

        notificationManager.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("fcm", "token=$token")
        val back : FcmNetworkConnectionInterface by inject()
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            back.sendFcmToken(token)
        }
    }
}