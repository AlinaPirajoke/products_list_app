package com.kopim.productlist.data.model.network.apimodels.profile

import com.kopim.productlist.data.model.database.entities.UserProfileEntity
import com.kopim.productlist.data.model.profile.ProfileColorString
import com.kopim.productlist.data.model.profile.UserProfile

data class ProfileResponseData(
    val user_id: String,
    val display_name: String,
    /** Как `user_color` в [com.kopim.productlist.data.model.network.apimodels.getcart.GetCartResponseData]. */
    val profile_color: String? = null,
) {
    fun toUserProfileEntity(nowMillis: Long = System.currentTimeMillis()): UserProfileEntity =
        UserProfileEntity(
            userId = user_id,
            displayName = display_name,
            profileColor = ProfileColorString.normalizeOrDefault(profile_color),
            lastSyncedAtMillis = nowMillis,
        )

    fun toUserProfile(): UserProfile =
        UserProfile(
            userId = user_id,
            displayName = display_name,
            profileColor = ProfileColorString.normalizeOrDefault(profile_color),
        )
}
