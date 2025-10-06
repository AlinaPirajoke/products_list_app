package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.changeentities.LocalCheckChangeDbEntity

@Dao
interface LocalCheckChangeDao {
    @Upsert
    suspend fun upsert(change: LocalCheckChangeDbEntity)

    @Query("SELECT * FROM local_check_changes")
    suspend fun getChanges(): List<LocalCheckChangeDbEntity>

    @Query("DELETE FROM local_check_changes")
    suspend fun cleanChanges()
}