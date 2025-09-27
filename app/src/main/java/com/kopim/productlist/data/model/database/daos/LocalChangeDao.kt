package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.model.database.entities.LocalChangeEntity

@Dao
interface LocalChangeDao {
    @Upsert
    suspend fun upsert(change: LocalChangeEntity)

    @Query("SELECT * FROM local_changes")
    suspend fun getChanges(): List<LocalChangeEntity>

    @Query("DELETE FROM local_changes")
    suspend fun cleanChanges()
}