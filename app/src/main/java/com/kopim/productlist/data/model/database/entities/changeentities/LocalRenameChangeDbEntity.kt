package com.kopim.productlist.data.model.database.entities.changeentities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.utils.LocalChange

@Entity(
    tableName = "local_rename_changes",
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
data class LocalRenameChangeDbEntity(
    @PrimaryKey val id: Long?,
    val newName: String,
    val itemId: Long,
) {
    fun toLocalChange() = LocalChange.RenameChange(
        changeId = id,
        newName = newName,
        itemId = itemId
    )
}