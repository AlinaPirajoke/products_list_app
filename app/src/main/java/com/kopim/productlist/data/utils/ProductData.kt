package com.kopim.productlist.data.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.model.database.entities.ProductDbEntity

data class ProductData(
    val id: Long,
    val creatorColor: String,
    val name: String,
    val completed: Boolean,
    val checkedAt: String?,
    val productId: Long = 0,
) {
    fun toProductUiData() = ProductUiData(
        id = this.id,
        text = this.name,
        lineTrough = this.completed,
        creatorColor = Color(this.creatorColor.toColorInt()),
    )

    fun toListItemDbEntity(cartId: Long, productId: Long) = ListItemDbEntity(
        id = this.id,
        checked = this.completed,
        product = productId,
        color = this.creatorColor,
        cart = cartId,
        checkedAt = this.checkedAt
    )

    fun toProductDbEntity() = ProductDbEntity(
        id = this.productId,
        name = this.name,
        mentions = 1,
    )

    companion object {
        fun MutableList<ProductData>.applyCheckChanges(changes: List<LocalChange.CheckChange>) {
            changes.forEach { change ->
                val itemForChange = this.indexOfFirst { item -> item.id == change.itemId }
                this[itemForChange] = this[itemForChange].copy(completed = change.checked)
            }
        }

        fun MutableList<ProductData>.applyRenameChanges(changes: List<LocalChange.RenameChange>) {
            changes.forEach { change ->
                val itemForChange = this.indexOfFirst { item -> item.id == change.itemId }
                this[itemForChange] = this[itemForChange].copy(name = change.newName)
            }
        }
    }
}
