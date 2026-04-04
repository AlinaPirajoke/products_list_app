package com.kopim.productlist.data.mvvm.editlist

data class CartMemberRow(
    val displayName: String,
    val profileColor: String,
)

data class EditListDraft(
    val title: String,
    val shareCode: String,
    val members: List<CartMemberRow> = emptyList(),
)
