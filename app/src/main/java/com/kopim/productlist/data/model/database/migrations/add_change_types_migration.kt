package com.kopim.productlist.data.model.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kopim.productlist.data.model.database.utils.ChangeType

val ADD_CHANGE_TYPES_MIGRATION = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        ChangeType.entries.forEach {
            database.query("INSERT INTO change_types VALUES (${it.typeId}, ${it.typeName})")
        }
    }
}