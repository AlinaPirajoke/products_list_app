package com.kopim.productlist.data.utils

import androidx.compose.ui.graphics.Color

data class ProductUiData(
    val id: Long,
    val creatorColor: Color,
    val text: String,
    val lineTrough: Boolean,
    val picked: Boolean = false,
){
    fun withUpdatedData(newData: ProductData) = this.copy(
        text = newData.name,
        lineTrough = newData.completed
    )
}