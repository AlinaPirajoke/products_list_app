package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kopim.productlist.data.utils.Hint

@Entity(tableName = "products", indices = [Index(value = ["name"], unique = true)])
data class ProductDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mentions: Int,
) {
    fun toHint() = Hint(id, name, mentions)
}