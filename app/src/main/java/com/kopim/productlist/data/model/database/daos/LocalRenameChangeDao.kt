package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.changeentities.LocalRenameChangeDbEntity

@Dao
interface LocalRenameChangeDao {
    @Upsert
    suspend fun upsert(change: LocalRenameChangeDbEntity)

    @Query("SELECT * FROM local_rename_changes")
    suspend fun getChanges(): List<LocalRenameChangeDbEntity>

    @Query("DELETE FROM local_rename_changes")
    suspend fun cleanChanges()
}