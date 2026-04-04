package com.kopim.productlist.data.mvvm.loginaccount

data class LoginAccountUiState(
    val userId: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
