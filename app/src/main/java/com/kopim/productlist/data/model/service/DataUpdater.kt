package com.kopim.productlist.data.model.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kopim.productlist.data.model.datasource.ListDataSource

const val TAG = "DataUpdater"

class DataUpdater(appContext: Context, workerParams: WorkerParameters, private val dataSource: ListDataSource) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.i(TAG,  "Updating data via work manager")
        dataSource.listUpdate(1)
        return Result.success()
    }
}