package com.kopim.productlist.data.mvvm.joinlist

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.mvvm.BaseViewModel
import com.kopim.productlist.ui.navigation.CartNavPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JoinListByCodeViewModel(
    private val joinListPort: JoinListByCodePort,
) : BaseViewModel() {

    private val _state = MutableStateFlow(JoinListByCodeUiState())
    val state: StateFlow<JoinListByCodeUiState> = _state.asStateFlow()

    fun onJoinCodeChange(value: String) {
        _state.update {
            it.copy(joinCode = value, errorMessage = null)
        }
    }

    fun onSubmit() {
        val trimmed = _state.value.joinCode.trim()
        if (trimmed.isEmpty()) {
            _state.update { it.copy(errorMessage = "Введите код списка") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = joinListPort.joinByInviteCode(trimmed)
            _state.update { it.copy(isLoading = false) }
            result.fold(
                onSuccess = { listId ->
                    popBackStack()
                    navigate(CartNavPoint(listId))
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(errorMessage = e.message ?: "Не удалось добавить список")
                    }
                }
            )
        }
    }
}
