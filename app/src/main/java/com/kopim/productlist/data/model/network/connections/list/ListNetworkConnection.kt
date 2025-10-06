package com.kopim.productlist.data.model.network.connections.list

import android.util.Log
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartResponseData
import com.kopim.productlist.data.model.network.apimodels.removefromcart.CheckProductRequestData
import com.kopim.productlist.data.model.network.connections.BaseNetworkConnection
import com.kopim.productlist.data.model.network.networksettings.apiservices.ListApiService
import com.kopim.productlist.data.model.network.utils.CheckedProductData
import com.kopim.productlist.data.model.network.utils.NewProductData
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "ListNetworkConnection"

class ListNetworkConnection(
    override val connection: ListApiService,
    spm: SharedPreferencesManager
) : BaseNetworkConnection(connection, spm), ListNetworkConnectionInterface {
    override val lastCartData: MutableStateFlow<ProductListData?> = MutableStateFlow(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            checkLogin()
            Log.i(TAG, "User token: $token")
        }
    }

    override suspend fun getCart(cartId: Long) =
        safeRequest {
            if (checkLogin()) {
                val response = connection.getProducts(cartId)
                if (response.isSuccessful) {
                    lastCartData.emit(
                        response.body()?.toProductListData() ?: return@safeRequest
                    )
                }
            }
        } ?: Unit

    override suspend fun getHints(key: String): List<Hint>? =
        safeRequest {
            if (checkLogin()) {
                val response = connection.getHints(key)
                if (response.isSuccessful) {
                    val hints = response.body()?.map { it.toHint() }
                    return@safeRequest hints
                }
            }
            return@safeRequest null
        }


    override suspend fun addProducts(products: List<NewProductData>): Response<AddToCartResponseData>? {
        if (products.isEmpty()) return null

        return safeRequest {
            if (checkLogin()) {
                connection.addToCart(
                    AddToCartRequestData(
                        products.map {
                            AddToCartRequestData.AddToCartRequestItem(
                                it.listId, it.product
                            )
                        }
                    )
                )
            }
            else null
        }
    }

    override suspend fun checkProduct(items: List<CheckedProductData>): Response<Unit>? {
        if (items.isEmpty()) return null

        return safeRequest {
            if (checkLogin()) {
                connection.checkProduct(
                    CheckProductRequestData(
                        items.map {
                            CheckProductRequestData.RemoveFromCartRequestItem(
                                it.itemId,
                                it.checked
                            )
                        },
                    )
                )
            }
            else null
        }
    }
}