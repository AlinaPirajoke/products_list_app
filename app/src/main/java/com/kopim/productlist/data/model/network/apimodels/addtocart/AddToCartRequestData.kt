package com.kopim.productlist.data.model.network.apimodels.addtocart

import com.kopim.productlist.data.model.database.utils.ChangeType
import com.kopim.productlist.data.utils.LocalChange

data class AddToCartRequestData(
    val product_list: List<AddToCartRequestItem>
){
    data class AddToCartRequestItem(
        val list: Long,
        val product: String,
    )
}

