package com.kopim.productlist.data.mvvm.homefeed

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.mvvm.BaseViewModel
import com.kopim.productlist.ui.navigation.CartNavPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeFeedViewModel(val dataSource: CartsDataSourceInterface) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeFeedUiState())
    val state: StateFlow<HomeFeedUiState> = _state.asStateFlow()

    init {
        refreshCarts()
    }

    fun onUpdateDate() {
        refreshCarts()
    }

    fun onNavigateToList(listId: Long) {
        navigate(CartNavPoint(listId))
    }

    /** Заглушка: смена цвета профиля на сервере. */
    fun onAccountColorChangeRequest() {
        viewModelScope.launch {
            _state.update { s ->
                val a = s.account
                val next = (a.profileColorIndex + 1) % AccountSidebarState.PROFILE_COLOR_COUNT
                s.copy(account = a.copy(profileColorIndex = next))
            }
        }
    }

    fun onAccountNameClick() {
        _state.update { s ->
            val a = s.account
            if (a.isNameEditing) s
            else s.copy(
                account = a.copy(
                    isNameEditing = true,
                    nameDraft = a.displayName,
                )
            )
        }
    }

    fun onAccountNameDraftChange(value: String) {
        _state.update { it.copy(account = it.account.copy(nameDraft = value)) }
    }

    fun onAccountPasswordLabelClick() {
        _state.update { s ->
            val a = s.account
            when {
                a.isPasswordEditing -> s
                !a.isPasswordRevealed -> s.copy(account = a.copy(isPasswordRevealed = true))
                else -> s.copy(
                    account = a.copy(
                        isPasswordEditing = true,
                        passwordDraft = a.password,
                    )
                )
            }
        }
    }

    fun onAccountPasswordDraftChange(value: String) {
        _state.update { it.copy(account = it.account.copy(passwordDraft = value)) }
    }

    /** Заглушка: сохранение имени/пароля на сервере. */
    fun onAccountSubmitChanges() {
        viewModelScope.launch {
            _state.update { s ->
                val a = s.account
                var next = a
                if (a.isNameEditing) {
                    next = next.copy(displayName = a.nameDraft.trim().ifEmpty { a.displayName })
                }
                if (a.isPasswordEditing) {
                    next = next.copy(password = a.passwordDraft)
                }
                next = next.copy(
                    isNameEditing = false,
                    isPasswordEditing = false,
                    isPasswordRevealed = false,
                    passwordDraft = "",
                    nameDraft = next.displayName,
                )
                s.copy(account = next)
            }
        }
    }

    private fun refreshCarts() {
        viewModelScope.launch {
            dataSource.getUserCarts().collect { lists ->
                _state.value = _state.value.copy(lists = lists)
            }
        }
    }
}