package com.kopim.productlist.data.model.database.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `cart_feed_cache` (
                    `cartId` INTEGER NOT NULL PRIMARY KEY,
                    `name` TEXT NOT NULL,
                    `previewJson` TEXT NOT NULL,
                    `inviteCode` TEXT
                )
                """.trimIndent()
            )
        }
    }
}
