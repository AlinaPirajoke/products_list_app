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

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `user_profile` (
                    `userId` TEXT NOT NULL PRIMARY KEY,
                    `displayName` TEXT NOT NULL,
                    `profileColorIndex` INTEGER NOT NULL,
                    `lastSyncedAtMillis` INTEGER NOT NULL
                )
                """.trimIndent()
            )
        }
    }

    /**
     * Цвет профиля — строка (как user_color в API корзины), вместо индекса палитры.
     */
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `user_profile_new` (
                    `userId` TEXT NOT NULL PRIMARY KEY,
                    `displayName` TEXT NOT NULL,
                    `profileColor` TEXT NOT NULL,
                    `lastSyncedAtMillis` INTEGER NOT NULL
                )
                """.trimIndent()
            )
            db.execSQL(
                """
                INSERT INTO `user_profile_new` (`userId`, `displayName`, `profileColor`, `lastSyncedAtMillis`)
                SELECT `userId`, `displayName`,
                    CASE `profileColorIndex`
                        WHEN 0 THEN '#FF81D4FA'
                        WHEN 1 THEN '#FF72A7D0'
                        WHEN 2 THEN '#FFA5D6A7'
                        WHEN 3 THEN '#FFFFAB91'
                        WHEN 4 THEN '#FFD0BCFF'
                        ELSE '#FF7D5260'
                    END,
                    `lastSyncedAtMillis`
                FROM `user_profile`
                """.trimIndent()
            )
            db.execSQL("DROP TABLE `user_profile`")
            db.execSQL("ALTER TABLE `user_profile_new` RENAME TO `user_profile`")
        }
    }
}
