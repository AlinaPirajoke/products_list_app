package com.kopim.productlist.data.utils

data class ShortCartData(
    val name: String,
    val id: Long,
    val items: List<ShortListItemData>
){
    data class ShortListItemData(
        val name: String
    )
}
