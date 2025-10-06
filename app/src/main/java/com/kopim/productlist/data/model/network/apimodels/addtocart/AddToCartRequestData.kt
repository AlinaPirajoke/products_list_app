package com.kopim.productlist.data.model.network.apimodels.addtocart

data class AddToCartRequestData(
    val product_list: List<AddToCartRequestItem>
){
    data class AddToCartRequestItem(
        val list: Long,
        val product: String,
    )
}

