package com.kopim.productlist.data.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.model.database.entities.ProductDbEntity

data class ProductData(
    val id: Int,
    val creatorColor: String,
    val name: String,
    val completed: Boolean,
    val checkedAt: String?,
    val productId: Int = 0,
){
    fun toProductUiData() = ProductUiData(
        id = this.id,
        text = this.name,
        lineTrough = this.completed,
        creatorColor = Color(this.creatorColor.toColorInt()),
    )

    fun toListItemDbEntity(cartId: Long, productId: Long) = ListItemDbEntity(
        id = this.id.toLong(),
        checked = this.completed,
        product = productId,
        color = this.creatorColor,
        cart = cartId,
        checkedAt = this.checkedAt
    )

    fun toProductDbEntity() = ProductDbEntity(
        id = this.productId.toLong(),
        name = this.name,
        mentions = 1,
    )
}
