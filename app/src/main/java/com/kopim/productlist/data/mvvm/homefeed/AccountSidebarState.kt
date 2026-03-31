package com.kopim.productlist.data.mvvm.homefeed

/**
 * Состояние панели аккаунта (только UI + заглушки сети во [HomeFeedViewModel]).
 */
data class AccountSidebarState(
    val userId: String = "1001",
    val displayName: String = "Иван",
    val nameDraft: String = "",
    val isNameEditing: Boolean = false,
    /** Скрыт до первого нажатия на «Пароль». */
    val password: String = "qwerty123",
    val isPasswordRevealed: Boolean = false,
    val isPasswordEditing: Boolean = false,
    val passwordDraft: String = "",
    val profileColorIndex: Int = 0,
) {
    val anyFieldActive: Boolean
        get() = isNameEditing || isPasswordEditing

    companion object {
        const val PROFILE_COLOR_COUNT = 6
    }
}
