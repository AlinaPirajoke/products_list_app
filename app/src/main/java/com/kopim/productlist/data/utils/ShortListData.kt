package com.kopim.productlist.data.utils

data class ShortListData(
    val name: String,
    val id: Long,
    val items: ShortListItemsData
){
    data class ShortListItemsData(
        val name: String
    )
}
