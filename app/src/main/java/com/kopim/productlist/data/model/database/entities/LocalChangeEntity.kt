package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kopim.productlist.data.model.database.utils.ChangeType
import com.kopim.productlist.data.utils.LocalChange

@Entity(tableName = "local_changes")
data class LocalChangeEntity(
    @PrimaryKey val itemId: Long,
    val name: String,
    val checked: Boolean,
    val changeType: Long
) {
    fun toLocalChange() = LocalChange(
        itemId = itemId,
        name = name,
        checked = checked,
        changeType = ChangeType.entries.first { item -> item.typeId == changeType }
    )
}
