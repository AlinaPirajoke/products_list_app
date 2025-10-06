package com.kopim.productlist.data.utils

import com.kopim.productlist.data.model.database.entities.changeentities.LocalAdditionChangeDbEntity
import com.kopim.productlist.data.model.database.entities.changeentities.LocalCheckChangeDbEntity
import com.kopim.productlist.data.model.database.entities.changeentities.LocalRenameChangeDbEntity
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData

sealed class LocalChange(
    val changeId: Long?,
){
    class AdditionChange(changeId: Long? = null, val name: String, val cartId: Long): LocalChange(changeId) {
        fun toLocalAdditionChangeEntity() = LocalAdditionChangeDbEntity(changeId, name, cartId)
        fun toNewProductData() = NewProductData(name, cartId)
    }

    class CheckChange(changeId: Long? = null, val checked: Boolean, val itemId: Long): LocalChange(changeId) {
        fun toLocalCheckChangeEntity() = LocalCheckChangeDbEntity(changeId, checked, itemId)
        fun toCheckedProductData() = CheckedProductData(itemId, checked)
    }

    class RenameChange(changeId: Long? = null, val newName: String, val itemId: Long): LocalChange(changeId){
        fun toLocalRenameChangeEntity() = LocalRenameChangeDbEntity(changeId, newName, itemId)
        // todo realisation
    }

    companion object {
        const val DEFAULT_CHANGE_ID = -1L
    }
}

