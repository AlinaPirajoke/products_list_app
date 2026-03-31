package com.kopim.productlist.data.mvvm.joinlist

data class JoinListByCodeUiState(
    val joinCode: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
