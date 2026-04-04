package com.kopim.productlist.data.model.network.apimodels.getcartinfo

/**
 * Участник корзины в ответе [GetCartInfoResponseData]; поля совпадают по смыслу с [com.kopim.productlist.data.model.network.apimodels.profile.ProfileResponseData].
 */
data class GetCartInfoUserData(
    val user_id: String,
    val display_name: String,
    val profile_color: String,
)
