package com.kopim.productlist.data.mvvm.homefeed

import com.kopim.productlist.data.model.profile.ProfileColorString
import com.kopim.productlist.data.model.profile.UserProfile

/**
 * Состояние панели аккаунта; отображаемые поля профиля приходят из локальной БД и сервера через репозиторий профиля.
 */
data class AccountSidebarState(
    val userId: String = "",
    val displayName: String = "",
    val nameDraft: String = "",
    val isNameEditing: Boolean = false,
    val password: String = "",
    val isPasswordEditing: Boolean = false,
    val passwordDraft: String = "",
    /** Строка цвета, как `user_color` в API корзины. */
    val profileColor: String = ProfileColorString.DEFAULT,
    /**
     * См. [com.kopim.productlist.data.model.database.SharedPreferencesManager.accountPasswordSessionKnown].
     */
    val passwordSessionKnown: Boolean = false,
) {
    val anyFieldActive: Boolean
        get() = isNameEditing || isPasswordEditing
}

/**
 * Накладывает данные из локальной БД / сервера, не трогая режимы редактирования и черновики.
 */
fun AccountSidebarState.withSyncedProfile(profile: UserProfile?): AccountSidebarState {
    if (profile == null) return this
    return copy(
        userId = profile.userId,
        displayName = if (!isNameEditing) profile.displayName else displayName,
        nameDraft = if (!isNameEditing) profile.displayName else nameDraft,
        profileColor = profile.profileColor,
    )
}
