package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kopim.productlist.data.model.database.entities.CartFeedCacheEntity

@Dao
interface CartFeedCacheDao {
    @Query("SELECT * FROM cart_feed_cache ORDER BY name COLLATE NOCASE ASC")
    suspend fun getAll(): List<CartFeedCacheEntity>

    @Query("DELETE FROM cart_feed_cache WHERE cartId = :cartId")
    suspend fun deleteByCartId(cartId: Long)

    @Query("DELETE FROM cart_feed_cache")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<CartFeedCacheEntity>)

    @Transaction
    suspend fun replaceAll(entities: List<CartFeedCacheEntity>) {
        deleteAll()
        if (entities.isNotEmpty()) {
            insertAll(entities)
        }
    }

    @Query(
        """
        UPDATE cart_feed_cache SET name = :name
        WHERE cartId = :cartId
        """
    )
    suspend fun updateName(cartId: Long, name: String)
}
