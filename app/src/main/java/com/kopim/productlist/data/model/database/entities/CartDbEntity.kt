package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "carts", indices = [Index(value = ["code"], unique = true)])
data class CartDbEntity (
    @PrimaryKey val id: Long,
    val code: String?,
)