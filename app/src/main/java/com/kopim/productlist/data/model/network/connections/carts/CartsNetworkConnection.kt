package com.kopim.productlist.data.model.network.connections.carts

import android.util.Log
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.apimodels.addusertocart.AddUserToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.addusertocart.AddUserToCartResponseData
import com.kopim.productlist.data.model.network.apimodels.getcartinfo.GetCartInfoResponseData
import com.kopim.productlist.data.model.network.apimodels.getcarts.GetCartsResponseData
import com.kopim.productlist.data.model.network.apimodels.removeuserfromcart.RemoveUserFromCartRequestData
import com.kopim.productlist.data.model.network.apimodels.renamecart.RenameCartRequestData
import com.kopim.productlist.data.model.network.connections.BaseNetworkConnection
import com.kopim.productlist.data.model.network.networksettings.apiservices.CartsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "CartsNetworkConnection"

class CartsNetworkConnection(
    override val connection: CartsApiService,
    spm: SharedPreferencesManager
) : BaseNetworkConnection(connection, spm), CartsNetworkConnectionInterface {
//    override val lastIncomingCartsData: MutableStateFlow<List<ShortCartData>?> =
//        MutableStateFlow(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            checkLogin()
            Log.i(TAG, "User token: $token")
        }
    }

    override suspend fun getUserCarts(): Response<GetCartsResponseData>? =
        safeRequest {
            if (checkLogin()) {
                connection.getCarts()
            }
            else null
        }

    override suspend fun getCartInfo(cartId: Long): Response<GetCartInfoResponseData>? =
        safeRequest {
            if (checkLogin()) {
                connection.getCartInfo(cartId)
            } else null
        }

    override suspend fun addUserToCart(cartCode: String): Response<AddUserToCartResponseData>? =
        safeRequest {
            if (checkLogin()) {
                connection.addUserToCart(
                    AddUserToCartRequestData(
                        code = cartCode
                    )
                )
            }
            else null
        }

    override suspend fun removeUserFromCart(cartId: Long): Response<Unit>? =
        safeRequest {
            if (checkLogin()) {
                connection.removeUserFromCart(
                    RemoveUserFromCartRequestData(
                        cart_id = cartId
                    )
                )
            }
            else null
        }

    override suspend fun renameCart(cartId: Long, newName: String): Response<Unit>? =
        safeRequest {
            if (checkLogin()) {
                connection.renameCart(
                    RenameCartRequestData(
                        new_name = newName,
                        cart_id = cartId
                    )
                )
            }
            else null
        }
}