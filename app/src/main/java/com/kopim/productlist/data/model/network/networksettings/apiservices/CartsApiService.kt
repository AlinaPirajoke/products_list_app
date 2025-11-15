package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.addusertocart.AddUserToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.removeuserfromcart.RemoveUserFromCartRequestData
import com.kopim.productlist.data.model.network.apimodels.renamecart.RenameCartRequestData
import com.kopim.productlist.data.model.network.apimodels.updatefcm.UpdateFcmTokenRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CartsApiService: ApiService {
    @POST("rename_cart")
    suspend fun renameCart(
        @Body data: RenameCartRequestData
    ): Response<Unit>

    @GET("get_cart_info")
    suspend fun getCartInfo(
        @Query("cart") cart: Long
    ): Response<Unit>

    @POST("add_user_to_cart")
    suspend fun addUserToCart(
        @Body data: AddUserToCartRequestData
    ): Response<Unit>

    @POST("remove_user_from_cart")
    suspend fun removeUserFromCart(
        @Body data: RemoveUserFromCartRequestData
    ): Response<Unit>

    @GET("get_carts")
    suspend fun getCarts(
    ): Response<Unit>
}