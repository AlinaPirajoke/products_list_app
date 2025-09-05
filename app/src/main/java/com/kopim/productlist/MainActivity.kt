package com.kopim.productlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kopim.productlist.data.model.service.DataUpdater
import com.kopim.productlist.ui.screens.CartScreen
import com.kopim.productlist.ui.theme.ProductsTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startUpdatingData()

        enableEdgeToEdge()
        setContent {
            ProductsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CartScreen(innerPadding)
                }
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