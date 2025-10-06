package com.kopim.productlist.data.model.database.entities.dtos

import com.kopim.productlist.data.utils.ProductData
import com.kopim.productlist.data.utils.ProductListData

data class ListProductDTO(
    val id: Long,
    val product: String,
    val productId: Long,
    val checked: Boolean,
    val color: String
) {
    fun toProductData() = ProductData(id, color, product, checked, null, productId)
}