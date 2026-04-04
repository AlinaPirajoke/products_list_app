package com.kopim.productlist.data.model.network.apimodels.profile

data class UpdateUserProfileRequestData(
    val display_name: String? = null,
    /** Как `user_color` в ответе get_cart. */
    val profile_color: String? = null,
)
