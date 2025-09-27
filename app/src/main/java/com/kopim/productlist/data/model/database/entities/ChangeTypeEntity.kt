package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "change_type")
data class ChangeTypeEntity(
    @PrimaryKey val id: Long,
    val name: String
) {

}
