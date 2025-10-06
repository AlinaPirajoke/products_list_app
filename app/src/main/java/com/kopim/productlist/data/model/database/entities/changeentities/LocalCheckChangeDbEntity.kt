package com.kopim.productlist.data.model.database.entities.changeentities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.utils.LocalChange

@Entity(
    tableName = "local_check_changes",
    foreignKeys = [
        ForeignKey(
            entity = ListItemDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE
        )
    ]
)
data class LocalCheckChangeDbEntity(
    @PrimaryKey val id: Long?,
    val checked: Boolean,
    val itemId: Long,
) {
    fun toLocalChange() = LocalChange.CheckChange(
        changeId = id,
        checked = checked,
        itemId = itemId
    )
}

