package com.kopim.productlist.data.mvvm.homefeed

import androidx.lifecycle.viewModelScope
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.model.profile.ProfileColorString
import com.kopim.productlist.data.model.profile.UserProfileRepository
import com.kopim.productlist.data.mvvm.BaseViewModel
import com.kopim.productlist.ui.navigation.CartNavPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeFeedViewModel(
    private val dataSource: CartsDataSourceInterface,
    private val userProfileRepository: UserProfileRepository,
    private val sharedPreferencesManager: SharedPreferencesManager,
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeFeedUiState())
    val state: StateFlow<HomeFeedUiState> = _state.asStateFlow()

    private var cartsCollectJob: Job? = null

    init {
        viewModelScope.launch {
            userProfileRepository.profileFlow.collect { profile ->
                _state.update { s ->
                    s.copy(
                        account = s.account.withSyncedProfile(profile).copy(
                            passwordSessionKnown = sharedPreferencesManager.accountPasswordSessionKnown,
                            password = sharedPreferencesManager.storedAccountPassword,
                        ),
                    )
                }
            }
        }
        viewModelScope.launch {
            userProfileRepository.credentialsSignInEvents.collect {
                _state.update { s ->
                    s.copy(
                        account = s.account.copy(
                            password = sharedPreferencesManager.storedAccountPassword,
                            passwordSessionKnown = sharedPreferencesManager.accountPasswordSessionKnown,
                        ),
                    )
                }
                refreshCarts()
            }
        }
        viewModelScope.launch {
            userProfileRepository.refreshFromServer()
        }
        viewModelScope.launch {
            dataSource.cartsInvalidated.collect {
                refreshCarts()
            }
        }
        refreshCarts()
    }

    fun onUpdateDate() {
        refreshCarts()
        viewModelScope.launch {
            userProfileRepository.refreshFromServer()
        }
    }

    /** Вызывать, когда главный экран снова стал вершиной стека навигации (возврат с корзины и др.). */
    fun onHomeFeedBecameTop() {
        refreshCarts()
    }

    fun onNavigateToList(listId: Long) {
        navigate(CartNavPoint(listId))
    }

    fun onAccountColorChangeRequest() {
        viewModelScope.launch {
            userProfileRepository.updateProfileColor(ProfileColorString.randomOpaque())
        }
    }

    fun onAccountNameValueClick() {
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

    fun onAccountNameLabelClick() {
        _state.update { s ->
            val a = s.account
            if (!a.isNameEditing) s
            else s.copy(
                account = a.copy(
                    isNameEditing = false,
                    nameDraft = a.displayName,
                )
            )
        }
    }

    fun onAccountNameDraftChange(value: String) {
        _state.update { it.copy(account = it.account.copy(nameDraft = value)) }
    }

    fun onAccountPasswordMaskClick() {
        _state.update { s ->
            val a = s.account
            if (a.isPasswordEditing) s
            else s.copy(
                account = a.copy(
                    isPasswordEditing = true,
                    passwordDraft = a.password,
                )
            )
        }
    }

    fun onAccountPasswordLabelClick() {
        _state.update { s ->
            val a = s.account
            if (!a.isPasswordEditing) s
            else s.copy(
                account = a.copy(
                    isPasswordEditing = false,
                    passwordDraft = a.password,
                )
            )
        }
    }

    fun onAccountPasswordDraftChange(value: String) {
        _state.update { it.copy(account = it.account.copy(passwordDraft = value)) }
    }

    fun onAccountSwitchProfileClick() {
        viewModelScope.launch {
            userProfileRepository.switchToAnotherProfile()
            refreshCarts()
        }
    }

    fun onAccountSubmitChanges() {
        viewModelScope.launch {
            val a = _state.value.account
            var error: Throwable? = null
            if (a.isNameEditing) {
                userProfileRepository.updateDisplayName(a.nameDraft).onFailure { error = it }
            }
            if (error == null && a.isPasswordEditing && a.passwordDraft.isNotBlank()) {
                userProfileRepository.changePassword(a.passwordDraft).onFailure { error = it }
            }
            if (error != null) return@launch
            _state.update { s ->
                val acc = s.account
                s.copy(
                    account = acc.copy(
                        isNameEditing = false,
                        isPasswordEditing = false,
                        passwordDraft = acc.password,
                        password = sharedPreferencesManager.storedAccountPassword,
                        nameDraft = acc.displayName,
                        passwordSessionKnown = sharedPreferencesManager.accountPasswordSessionKnown,
                    ),
                )
            }
        }
    }

    private fun refreshCarts() {
        cartsCollectJob?.cancel()
        cartsCollectJob = viewModelScope.launch {
            dataSource.getUserCarts().collect { lists ->
                _state.update { it.copy(lists = lists) }
            }
        }
    }
}
