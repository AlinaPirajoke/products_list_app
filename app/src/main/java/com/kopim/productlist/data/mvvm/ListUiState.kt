package com.kopim.productlist.data.mvvm

import androidx.compose.ui.text.input.TextFieldValue
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ListScreenMode
import com.kopim.productlist.data.utils.ProductUiData

data class ListUiState(
    val cart: List<ProductUiData> = emptyList(),
    val newProductFieldValue: TextFieldValue = TextFieldValue(""),
    val screenMode: ListScreenMode = ListScreenMode.CartMode,
    val newProductHints: List<Hint> = emptyList()
)
