package com.kopim.productlist.data.model.profile

import com.kopim.productlist.data.model.auth.LoginCredentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Локальный кэш профиля (Room) + синхронизация с сервером.
 */
interface UserProfileRepository {

    val profileFlow: Flow<UserProfile?>

    /** Событие после успешного входа по ID и паролю (для обновления данных на главном экране). */
    val credentialsSignInEvents: SharedFlow<Unit>

    suspend fun refreshFromServer(): Result<Unit>

    suspend fun updateDisplayName(name: String): Result<Unit>

    suspend fun updateProfileColor(color: String): Result<Unit>

    suspend fun changePassword(newPassword: String): Result<Unit>

    /** Очистить кэш, сбросить токен и получить нового анонимного пользователя. */
    suspend fun switchToAnotherProfile(): Result<Unit>

    /**
     * Вход по ID и паролю: при успехе полная очистка локальной БД сессии, сохранение нового токена,
     * загрузка профиля. При ошибке логина прежний токен восстанавливается.
     */
    suspend fun signInWithCredentials(credentials: LoginCredentials): Result<Unit>
}
