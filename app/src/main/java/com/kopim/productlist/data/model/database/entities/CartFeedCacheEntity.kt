package com.kopim.productlist.data.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_feed_cache")
data class CartFeedCacheEntity(
    @PrimaryKey val cartId: Long,
    val name: String,
    val previewJson: String,
    val inviteCode: String?,
)
