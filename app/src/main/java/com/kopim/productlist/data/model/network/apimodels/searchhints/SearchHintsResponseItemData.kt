package com.kopim.productlist.data.model.network.apimodels.searchhints

import com.kopim.productlist.data.utils.Hint

data class SearchHintsResponseItemData(
    val id: Long,
    val name: String,
    val mentions: Int,
){
    fun toHint(): Hint {
        return Hint(id, name, mentions)
    }
}
