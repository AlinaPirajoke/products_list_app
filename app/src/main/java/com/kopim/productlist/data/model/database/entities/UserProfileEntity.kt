package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Кэш профиля текущего пользователя (синхронизируется с сервером).
 * [profileColor] — строка цвета, как `user_color` в ответе корзины (см. [String.toColorInt]).
 * Пароль не хранится.
 */
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: String,
    val displayName: String,
    val profileColor: String,
    val lastSyncedAtMillis: Long,
)
