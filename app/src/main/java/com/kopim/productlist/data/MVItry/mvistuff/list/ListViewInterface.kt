package com.kopim.productlist.data.MVItry.mvistuff.list

import com.kopim.productlist.data.MVItry.mvistuff.list.ListViewInterface.ListViewEvent
import com.kopim.productlist.data.MVItry.mvistuff.list.ListViewInterface.ListViewModel
import com.kopim.productlist.data.MVItry.mvistuff.view.MviView
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductData

interface ListViewInterface: MviView<ListViewModel, ListViewEvent> {
    data class ListViewModel (
        val hintsLoading: Boolean,
        val hints: List<Hint>,
        val productsLoading: Boolean,
        val products: List<ProductData>,
        val productFieldValue: String,
        val productFieldExpanded: Boolean,
    )

    sealed class ListViewEvent {
        object StartObserve : ListViewEvent()
        object StopObserve : ListViewEvent()
        data class AddProduct(val name: String) : ListViewEvent()
        data class DeleteProduct(val id: Int) : ListViewEvent()
        data class EditField(val value: String) : ListViewEvent()
    }
}