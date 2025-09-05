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
    private val connection: ApiService,
    private val spm: SharedPreferencesManager
) {
    val lastCartData: MutableStateFlow<ProductListData?> = MutableStateFlow(null)
    private var token = spm.userToken

    init {
        CoroutineScope(Dispatchers.IO).launch {
            checkLogin()
            Log.i(TAG, "User token: $token")
        }
    }

    private suspend fun <T> safeRequest(body: suspend () -> T?): T? {
        try {
            return body()
        } catch (e: Exception) {
            Log.e(TAG, "Safety request failed", e)
            return null
        }
    }

    private suspend fun checkLogin(): Boolean {
        if (token == null) {
            Log.w(TAG, "User's token is null!")
            return loginUser()
        }
        return true
    }

    suspend fun loginUser(): Boolean =
        safeRequest {
            val response = connection.login()
            if (response.isSuccessful) {
                token = response.body()?.token
                token?.let {
                    spm.userToken = token
                }
                Log.i(TAG, "New user token: $token")
                return@safeRequest true
            }
            Log.e(TAG, "Failed to login")
            return@safeRequest false
        } ?: false


    suspend fun getCart(cartId: Int) =
        safeRequest {
            if (checkLogin()) {
                val response = connection.getProducts(cartId)
                if (response.isSuccessful) {
                    lastCartData.emit(
                        response.body()?.toProductListData() ?: return@safeRequest
                    )
                }
            }
        }

    suspend fun getHints(key: String): List<Hint>? =
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


    suspend fun addProduct(listId: Int, product: String) {
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

    suspend fun checkProduct(itemId: Int, checked: Boolean) =
        safeRequest {
            if (checkLogin()) {
                connection.checkProduct(
                    RemoveFromCartRequest(
                        itemId,
                        checked
                    )
                )
            }
        }
}