package com.kopim.productlist.data.model.network.apimodels.removefromcart

data class RemoveFromCartRequest(
    val item: Int,
    val check: Boolean,
)
