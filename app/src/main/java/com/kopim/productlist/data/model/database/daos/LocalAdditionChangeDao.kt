package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.changeentities.LocalAdditionChangeDbEntity

@Dao
interface LocalAdditionChangeDao {
    @Upsert
    suspend fun upsert(change: LocalAdditionChangeDbEntity)

    @Query("SELECT * FROM local_addition_changes WHERE cartId = :cartId")
    suspend fun getChangesByCart(cartId: Long): List<LocalAdditionChangeDbEntity>

    @Query("SELECT * FROM local_addition_changes")
    suspend fun getChanges(): List<LocalAdditionChangeDbEntity>

    @Query("DELETE FROM local_addition_changes")
    suspend fun cleanChanges()
}