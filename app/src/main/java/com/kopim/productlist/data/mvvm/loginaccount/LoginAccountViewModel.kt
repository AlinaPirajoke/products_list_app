package com.kopim.productlist.data.mvvm.loginaccount

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.model.auth.LoginCredentials
import com.kopim.productlist.data.model.profile.UserProfileRepository
import com.kopim.productlist.data.mvvm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginAccountViewModel(
    private val userProfileRepository: UserProfileRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginAccountUiState())
    val state: StateFlow<LoginAccountUiState> = _state.asStateFlow()

    fun onUserIdChange(value: String) {
        _state.update { it.copy(userId = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, errorMessage = null) }
    }

    fun onBackClick() {
        popBackStack()
    }

    fun onSubmit() {
        val s = _state.value
        val id = s.userId.trim()
        if (id.isEmpty() || s.password.isBlank()) {
            _state.update { it.copy(errorMessage = "Введите ID и пароль") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = userProfileRepository.signInWithCredentials(
                LoginCredentials(userId = s.userId, password = s.password),
            )
            _state.update { it.copy(isLoading = false) }
            result.fold(
                onSuccess = { popBackStack() },
                onFailure = { e ->
                    _state.update {
                        it.copy(errorMessage = e.message ?: "Не удалось войти")
                    }
                },
            )
        }
    }
}
