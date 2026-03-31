package com.kopim.productlist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cafe.adriel.voyager.navigator.Navigator
import com.kopim.productlist.data.model.network.connections.fcm.FcmNetworkConnectionInterface
import com.kopim.productlist.data.model.notification.getFcmToken
import com.kopim.productlist.data.model.service.DataUpdater
import com.kopim.productlist.ui.navigation.HomeFeedNavPoint
import com.kopim.productlist.ui.theme.ProductsTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startUpdatingData()
        sendFcmToken()

        enableEdgeToEdge()
        setContent {
            ProductsTheme {
                Navigator(HomeFeedNavPoint)
            }
        }
    }

    private fun sendFcmToken() {
        lifecycleScope.launch {
            val fnc: FcmNetworkConnectionInterface by inject()
            getFcmToken()?.let { token ->
                Log.v("fcm", "Current fcm token: $token")
                fnc.sendFcmToken(token)
            }
        }
    }

    private fun startUpdatingData() {
        val periodicWork = PeriodicWorkRequestBuilder<DataUpdater>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWork
        )
    }
}