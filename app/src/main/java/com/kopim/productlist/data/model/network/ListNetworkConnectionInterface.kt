package com.kopim.productlist.data.model.network

import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartResponseData
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

interface ListNetworkConnectionInterface {
    val lastCartData: MutableStateFlow<ProductListData?>

    suspend fun getCart(cartId: Long)

    suspend fun getHints(key: String): List<Hint>?

    suspend fun addProducts(products: List<NewProductData>): Response<AddToCartResponseData>?

    suspend fun checkProduct(items: List<CheckedProductData>): Response<Unit>?
}