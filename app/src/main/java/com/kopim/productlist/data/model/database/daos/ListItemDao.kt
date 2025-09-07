package com.kopim.productlist.data.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.model.database.entities.dtos.ListProductsDTO

@Dao
interface ListItemDao {
    @Upsert
    suspend fun upsert(listItem: ListItemDbEntity)

    @Query("SELECT * FROM list_items")
    suspend fun getItems(): List<ListItemDbEntity>

    @Query("DELETE FROM list_items WHERE cart = :cartId")
    suspend fun cleanCart(cartId: Long)

    @Query("""SELECT +
            li.id as id,
            li.checked as checked,
            p.name as product,
            p.id as productId,
            li.color as color
            FROM list_items li
            JOIN products p ON li.product = p.id
            WHERE li.cart = :cartId AND (li.checkedAt > :date OR li.checkedAt IS NULL)
            ORDER BY li.checked ASC, li.id DESC
            """)
    suspend fun getItemsByCartAfterDate(cartId: Long, date: String): List<ListProductsDTO>
}