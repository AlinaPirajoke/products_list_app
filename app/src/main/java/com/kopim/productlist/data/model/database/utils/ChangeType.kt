package com.kopim.productlist.data.model.database.utils

enum class ChangeType(val typeId: Long, val typeName: String) {
    Rename(0, "rename"), Check(1, "check"), Create(2, "create")
}