package com.kopim.productlist.data.model.network.apimodels.getcart

import com.kopim.productlist.data.utils.ProductData
import com.kopim.productlist.data.utils.ProductListData

data class GetCartResponseData(
    val items: List<GetCartResponseItemData>
) {
    data class GetCartResponseItemData(
        val id: Long,
        val checked: Int,
        val name: String,
        val user_color: String,
        val checked_at: String?,
        val product_id: Long,
    ) {
        fun toProductData() = ProductData(
            id = id,
            completed = checked == 1,
            name = name,
            creatorColor = user_color,
            checkedAt = checked_at,
            productId = product_id
        )
    }

    fun toProductListData() = ProductListData(
        items = this.items.map { it.toProductData() }
    )
}



