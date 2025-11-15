package com.kopim.productlist.data.model.network.apimodels.getcarts

data class GetCartsResponseData(
    val lists: List<ShortCartResponseData>
){
    data class ShortCartResponseData(
        val cart_id: Long,
        val cart_name: Long,
        val products: List<ShortCartProductResponseData>
    ){
        data class ShortCartProductResponseData(
            val product_id: Long,
            val name: String
        )
    }
}
