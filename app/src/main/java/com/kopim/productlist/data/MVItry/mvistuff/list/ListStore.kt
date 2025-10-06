package com.kopim.productlist.data.MVItry.mvistuff.list

import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.ObservableObserver
import com.kopim.productlist.data.MVItry.mvistuff.StoreHelper
import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListIntent
import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListState
import com.kopim.productlist.data.MVItry.mvistuff.list.ListStoreInterface.ListState.ProductState.ProductData.Data
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListStore(
    private val network: ListDataManager,
) : ListStoreInterface, DisposableScope by DisposableScope() {

    private val helper = StoreHelper(ListState(), ::handleIntent, ::reduce).scope()

    override fun onNext(value: ListIntent) {
        helper.onIntent(value)
    }

    override fun subscribe(observer: ObservableObserver<ListState>) {
        helper.subscribe(observer)
    }

    private fun handleIntent(state: ListState, intent: ListIntent): Observable<ListEffect> =
        when (intent) {
            is ListIntent.StartObserve -> {}// todo
            is ListIntent.StopObserve -> {}// todo
            is ListIntent.AddProduct -> {}// todo
            is ListIntent.DeleteProduct -> {}// todo
            is ListIntent.EditField -> {}// todo
        } as Observable<ListEffect> // todo delete

    private fun reduce(state: ListState, effect: ListEffect): ListState =
        when (effect) {
            is ListEffect.ProductsLoadingStarted -> state.copy(
                products = state.products.copy(
                    isLoading = true
                )
            )

            is ListEffect.ProductsUpdate -> state.copy(
                products = state.products.copy(
                    isLoading = false,
                    data = Data(effect.productListData)
                )
            )

            is ListEffect.ProductsLoadingFailed -> state.copy(
                products = state.products.copy(
                    isLoading = false,
                    data = ListState.ProductState.ProductData.Error
                )
            )

            is ListEffect.HintsLoadingStarted -> state.copy(
                hints = state.hints.copy(
                    isLoading = true
                )
            )

            is ListEffect.HintsLoadingFinished -> state.copy(
                hints = state.hints.copy(
                    isLoading = false,
                    data = ListState.HintsState.HintsData.Data(effect.hints)
                )
            )

            is ListEffect.HintsLoadingFailed -> state.copy(
                hints = state.hints.copy(
                    isLoading = false,
                    data = ListState.HintsState.HintsData.Error
                )
            )

            is ListEffect.FieldUpdate -> state.copy(
                productField = effect.newValue
            )
        }

    private sealed class ListEffect {
        object ProductsLoadingStarted : ListEffect()
        data class ProductsUpdate(val productListData: ProductListData) : ListEffect()
        object ProductsLoadingFailed : ListEffect()

        object HintsLoadingStarted : ListEffect()
        data class HintsLoadingFinished(val hints: List<Hint>) : ListEffect()
        object HintsLoadingFailed : ListEffect()

        data class FieldUpdate(val newValue: String) : ListEffect()
    }

    interface ListDataManager {
        suspend fun pushUpdates(): Unit
        suspend fun listSubscribe(id: Long, updateDelay: Long): StateFlow<ProductListData?>
        fun listUnsubscribe(): Unit
        suspend fun fetchUpdates(id: Long): Unit
        suspend fun getHints(query: String): MutableStateFlow<List<Hint>?>
    }
}