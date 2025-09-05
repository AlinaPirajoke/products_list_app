package com.kopim.productlist.data.model.database

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {

    private val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    var userToken: String?
        get() = preferences.getString(TOKEN_KEY, null)
        set(value) = preferences.edit { putString(TOKEN_KEY, value) }

    companion object {
        private const val TOKEN_KEY = "token"
    }
}