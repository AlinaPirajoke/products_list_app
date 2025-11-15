package com.kopim.productlist.data.model.database.entities.dtos

data class ShortCartDataDto(
    val name: String,
    val id: Long,
    val products: List<String>
)