package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.model.database.entities.ProductDbEntity

@Dao
interface CartDao {
    @Upsert
    suspend fun upsert(cart: CartDbEntity)

    @Query("SELECT * FROM carts WHERE id = :id")
    suspend fun getCartById(id: Int): CartDbEntity?

    @Query("SELECT * FROM carts")
    suspend fun getCarts(): List<CartDbEntity>
}