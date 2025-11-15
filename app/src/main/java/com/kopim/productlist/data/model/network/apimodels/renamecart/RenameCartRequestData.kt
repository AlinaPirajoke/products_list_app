package com.kopim.productlist.data.model.network.apimodels.renamecart

data class RenameCartRequestData(
    val new_name: String,
    val cart_id: Long
)
