package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.ChangeTypeEntity
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity

@Dao
interface ChangeTypeDao {
    @Query("SELECT * FROM change_type")
    suspend fun getTypes(): List<ChangeTypeEntity>
}