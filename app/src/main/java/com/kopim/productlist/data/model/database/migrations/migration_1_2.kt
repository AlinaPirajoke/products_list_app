package com.kopim.productlist.data.model.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE list_items ADD COLUMN INTEGER local NOT NULL DEFAULT 1")
    }
}