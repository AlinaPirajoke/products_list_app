package com.kopim.productlist.data.utils

import com.kopim.productlist.data.model.database.entities.ProductDbEntity

data class Hint (
    val id: Int,
    val name: String,
    val mentions: Int,
){
    fun toProductDbEntity() = ProductDbEntity(id.toLong(), name, mentions)
}