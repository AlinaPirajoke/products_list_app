package com.kopim.productlist.data.model.network.apimodels.removefromcart

data class CheckProductRequestData(
    val items: List<RemoveFromCartRequestItem>,
) {
    data class RemoveFromCartRequestItem(
        val item: Long,
        val check: Boolean,
    )
}

