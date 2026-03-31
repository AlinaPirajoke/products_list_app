package com.kopim.productlist.data.model.database.utils

import org.json.JSONArray

internal object CartFeedPreviewJson {
    fun encode(names: List<String>): String =
        JSONArray().apply { names.forEach { put(it) } }.toString()

    fun decode(json: String): List<String> {
        if (json.isBlank()) return emptyList()
        return buildList {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                add(arr.getString(i))
            }
        }
    }
}
