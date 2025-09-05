package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "list_items",
    foreignKeys = [
        ForeignKey(
            entity = CartDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["cart"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["product"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cart"), Index("product")]
)
data class ListItemDbEntity(
    @PrimaryKey val id: Long,
    val cart: Long,
    val product: Long,
    val checked: Boolean,
    val checkedAt: String?,
    val color: String
)