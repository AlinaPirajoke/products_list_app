package com.kopim.productlist.data.utils

data class ProductListData(
    val usualItems: List<ProductData> = emptyList(),
    val localItems: List<LocalChange.AdditionChange> = emptyList()
)