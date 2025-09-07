package com.kopim.productlist.data.model.network

import android.util.Log
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.removefromcart.RemoveFromCartRequest
import com.kopim.productlist.data.model.network.networksettings.ApiService
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.ProductListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ListNetworkConnection"

class ListNetworkConnection(
    connection: ApiService,
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


    override suspend fun addProduct(listId: Long, product: String) {
        if (product.isEmpty()) return

        safeRequest {
            if (checkLogin()) {
                connection.addToCart(
                    AddToCartRequestData(
                        listId,
                        product
                    )
                )
            }
        }
    }

    override suspend fun checkProduct(itemId: Long, checked: Boolean) =
        safeRequest {
            if (checkLogin()) {
                connection.checkProduct(
                    RemoveFromCartRequest(
                        itemId,
                        checked
                    )
                )
            }
        } ?: Unit
}