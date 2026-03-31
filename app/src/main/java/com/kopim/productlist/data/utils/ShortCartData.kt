package com.kopim.productlist.data.utils

data class ShortCartData(
    val name: String,
    val id: Long,
    val items: List<ShortListItemData>,
    val inviteCode: String? = null,
){
    data class ShortListItemData(
        val name: String
    )
}
