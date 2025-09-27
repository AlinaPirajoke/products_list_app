package com.kopim.productlist.data.model.network.apimodels.removefromcart

import com.kopim.productlist.data.model.database.utils.ChangeType
import com.kopim.productlist.data.utils.LocalChange

data class RemoveFromCartRequestData(
    val items: List<RemoveFromCartRequestItem>,
) {
    data class RemoveFromCartRequestItem(
        val item: Long,
        val check: Boolean,
    )
}

