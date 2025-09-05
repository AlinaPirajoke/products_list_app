package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.ProductDbEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: ProductDbEntity)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductDbEntity?

    @Query("SELECT * FROM products WHERE name LIKE '%' || :key || '%' ORDER BY mentions DESC LIMIT 20")
    suspend fun getProductsByKey(key: String): List<ProductDbEntity>

    @Upsert
    suspend fun upsert(product: ProductDbEntity): Long

    @Upsert
    suspend fun upsertAll(products: List<ProductDbEntity>): List<Long>
}