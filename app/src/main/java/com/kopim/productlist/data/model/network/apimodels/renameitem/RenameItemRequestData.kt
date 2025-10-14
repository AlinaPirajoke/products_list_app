package com.kopim.productlist.data.model.network.apimodels.renameitem

data class RenameItemRequestData(
    val renaming_changes: List<RenameItemRequestItem>
){
    data class RenameItemRequestItem(
        val item_id: Long,
        val new_name: String,
    )
}
