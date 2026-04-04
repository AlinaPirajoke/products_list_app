package com.kopim.productlist.data.model.database

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {

    private val preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    var userToken: String?
        get() = preferences.getString(TOKEN_KEY, null)
        set(value) = preferences.edit { putString(TOKEN_KEY, value) }

    /**
     * Локальный признак: сессия после входа по паролю или после смены пароля (для UI сайдбара).
     * Сбрасывается при переходе на новую анонимную сессию ([com.kopim.productlist.data.model.profile.UserProfileRepository.switchToAnotherProfile]).
     */
    var accountPasswordSessionKnown: Boolean
        get() = preferences.getBoolean(ACCOUNT_PASSWORD_SESSION_KNOWN_KEY, false)
        set(value) = preferences.edit { putBoolean(ACCOUNT_PASSWORD_SESSION_KNOWN_KEY, value) }

    /**
     * Кэш пароля для отображения в сайдбаре (открытый текст в prefs по запросу продукта).
     * Очищается при [com.kopim.productlist.data.model.profile.UserProfileRepository.switchToAnotherProfile].
     */
    var storedAccountPassword: String
        get() = preferences.getString(STORED_ACCOUNT_PASSWORD_KEY, null).orEmpty()
        set(value) = preferences.edit {
            if (value.isEmpty()) remove(STORED_ACCOUNT_PASSWORD_KEY)
            else putString(STORED_ACCOUNT_PASSWORD_KEY, value)
        }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val ACCOUNT_PASSWORD_SESSION_KNOWN_KEY = "account_password_session_known"
        private const val STORED_ACCOUNT_PASSWORD_KEY = "stored_account_password"
    }
}