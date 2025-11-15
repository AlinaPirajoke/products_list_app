package com.kopim.productlist.data.model.network.apimodels.getcarts

import com.kopim.productlist.data.utils.ShortCartData

data class GetCartsResponseData(
    val lists: List<ShortCartResponseData>
){
    data class ShortCartResponseData(
        val cart_id: Long,
        val cart_name: String,
        val products: List<ShortCartProductResponseData>
    ){
        data class ShortCartProductResponseData(
            val product_id: Long,
            val name: String
        ){

            fun toShortListItemData() =
                ShortCartData.ShortListItemData(name = name)
        }

        fun toShortCartsDataItem() =
            ShortCartData(
                name = cart_name,
                id = cart_id,
                items = products.map(ShortCartProductResponseData::toShortListItemData)
            )
    }

    fun toShortCartDataList() =
        lists.map(ShortCartResponseData::toShortCartsDataItem)
}
