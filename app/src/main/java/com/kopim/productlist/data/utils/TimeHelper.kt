package com.kopim.productlist.data.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeHelper {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return dateFormat.format(calendar.time)
    }
}