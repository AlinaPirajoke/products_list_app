package com.kopim.productlist.data.mvvm.editlist

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.mvvm.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TITLE_SAVE_DEBOUNCE_MS = 450L

class EditListViewModel(
    private val listId: Long,
    private val editListPort: EditListPort,
) : BaseViewModel() {

    private val _state = MutableStateFlow(EditListUiState())
    val state: StateFlow<EditListUiState> = _state.asStateFlow()

    private var titleSaveJob: Job? = null

    init {
        loadEditState()
    }

    private fun loadEditState() {
        titleSaveJob?.cancel()
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            editListPort.loadEditState(listId).fold(
                onSuccess = { draft ->
                    _state.update {
                        it.copy(
                            listTitle = draft.title,
                            shareCode = draft.shareCode,
                            members = draft.members,
                            isLoading = false
                        )
                    }
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Не удалось загрузить данные списка"
                        )
                    }
                }
            )
        }
    }

    fun onListTitleChange(value: String) {
        _state.update { it.copy(listTitle = value, errorMessage = null) }
        titleSaveJob?.cancel()
        titleSaveJob = viewModelScope.launch {
            delay(TITLE_SAVE_DEBOUNCE_MS)
            _state.update { it.copy(isSavingTitle = true) }
            val result = editListPort.updateTitle(listId, value.trim())
            _state.update {
                it.copy(
                    isSavingTitle = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
        }
    }

    fun onLeaveList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            editListPort.leaveList(listId).fold(
                onSuccess = {
                    popBackStack(2)
                },
                onFailure = { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Не удалось выйти из списка"
                        )
                    }
                }
            )
        }
    }
}
