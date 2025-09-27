package com.kopim.productlist.data.model.database.utils

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kopim.productlist.data.model.database.migrations.ADD_CHANGE_TYPES_MIGRATION
import com.kopim.productlist.data.model.database.migrations.MIGRATION_1_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    @Volatile private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).addMigrations(MIGRATION_1_2, ADD_CHANGE_TYPES_MIGRATION).build().also { INSTANCE = it }
        }
    }
}