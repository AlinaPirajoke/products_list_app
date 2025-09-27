package com.kopim.productlist.data.utils

import com.kopim.productlist.data.model.database.entities.LocalChangeEntity
import com.kopim.productlist.data.model.database.utils.ChangeType
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartRequestData
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData

data class LocalChange(
    val itemId: Long,
    val name: String,
    val checked: Boolean,
    val changeType: ChangeType
){
    fun toLocalChangeEntity() = LocalChangeEntity(itemId, name, checked, changeType.typeId)

    fun toNewProductData() = NewProductData(itemId, name)

    fun toCheckedProductData() = CheckedProductData(itemId, checked)
}

