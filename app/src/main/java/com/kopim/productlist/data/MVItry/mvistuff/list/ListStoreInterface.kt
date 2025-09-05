package com.kopim.productlist.data.MVItry.mvistuff.list

import com.kopim.productlist.data.MVItry.mvistuff.Store
import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListIntent
import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListState
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData

interface ListStoreInterface : Store<ListIntent, ListState> {
    sealed class ListIntent {
        object StartObserve : ListIntent()
        object StopObserve : ListIntent()
        data class AddProduct(val name: String) : ListIntent()
        data class DeleteProduct(val id: Int) : ListIntent()
        data class EditField(val value: String) : ListIntent()
    }

    data class ListState(
        val hints: HintsState = HintsState(),
        val products: ProductState = ProductState(),
        val productField: String = "",
        val productFieldExpanded: Boolean = false,
    ) {
        data class HintsState(
            val isLoading: Boolean = false,
            val data: HintsData = HintsData.Data()
        ) {
            sealed class HintsData {
                data class Data(val hints: List<Hint> = emptyList<Hint>()) : HintsData()
                object Error : HintsData()
            }
        }
        data class ProductState(
            val isLoading: Boolean = false,
            val data: ProductData = ProductData.Data()
        ) {
            sealed class ProductData {
                data class Data(val products: ProductListData = ProductListData()) : ProductData()
                object Error : ProductData()
            }
        }
    }
}