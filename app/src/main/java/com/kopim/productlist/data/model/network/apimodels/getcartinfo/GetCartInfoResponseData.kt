package com.kopim.productlist.data.model.network.apimodels.getcartinfo

data class GetCartInfoResponseData(
    val id: Long,
    val code: String,
    val name: String,
    /** Участники корзины; отсутствие поля в JSON даёт `null` (старые ответы сервера). */
    val users: List<GetCartInfoUserData>? = null,
)
