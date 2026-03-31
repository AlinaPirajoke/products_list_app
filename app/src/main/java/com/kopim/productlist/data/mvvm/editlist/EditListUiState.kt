package com.kopim.productlist.data.mvvm.editlist

data class EditListUiState(
    val listTitle: String = "",
    val shareCode: String = "",
    val isLoading: Boolean = false,
    val isSavingTitle: Boolean = false,
    val errorMessage: String? = null,
)
