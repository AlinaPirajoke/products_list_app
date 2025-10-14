package com.kopim.productlist.data.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

data class ProductUiData(
    val id: Long,
    val creatorColor: Color,
    val text: String,
    val lineTrough: Boolean,
    val picked: Boolean = false,
    val isEditing: Boolean = false,
    val fieldText: TextFieldValue = TextFieldValue(text, selection = TextRange(text.length))
){
    fun withUpdatedData(newData: ProductData) = this.copy(
        text = newData.name,
        lineTrough = newData.completed
    )
}