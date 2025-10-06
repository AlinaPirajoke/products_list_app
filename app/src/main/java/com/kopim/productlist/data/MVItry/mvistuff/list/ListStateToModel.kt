package com.kopim.productlist.data.MVItry.mvistuff.list

import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListState

fun ListState.toListViewModel() = ListViewInterface.ListViewModel(
    hintsLoading = hints.isLoading,
    hints = (hints.data as ListState.HintsState.HintsData.Data).hints,
    productsLoading = products.isLoading,
    products = (products.data as ListState.ProductState.ProductData.Data).products.usualItems,
    productFieldValue = productField,
    productFieldExpanded = productFieldExpanded,
)