package com.kopim.productlist.data.model.database.entities.changeentities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.utils.LocalChange

@Entity(
    tableName = "local_addition_changes",
    foreignKeys = [
        ForeignKey(
            entity = CartDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocalAdditionChangeDbEntity(
    @PrimaryKey val id: Long?,
    val name: String,
    val cartId: Long
) {
    fun toLocalChange() = LocalChange.AdditionChange(
        changeId = id,
        name = name,
        cartId = cartId
    )
}

