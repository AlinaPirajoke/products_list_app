package com.kopim.productlist.data.model.database.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kopim.productlist.data.model.database.daos.CartDao
import com.kopim.productlist.data.model.database.daos.ListItemDao
import com.kopim.productlist.data.model.database.daos.ProductDao
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.model.database.entities.ListItemDbEntity
import com.kopim.productlist.data.model.database.entities.ProductDbEntity

@Database(entities = [ProductDbEntity::class, CartDbEntity::class, ListItemDbEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun listItemDao(): ListItemDao
}