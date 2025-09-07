package com.kopim.productlist.data.model.network.apimodels.removefromcart

data class RemoveFromCartRequest(
    val item: Long,
    val check: Boolean,
)
