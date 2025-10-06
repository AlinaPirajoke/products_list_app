package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartResponseData
import com.kopim.productlist.data.model.network.apimodels.updatefcm.UpdateFcmTokenRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApiService: ApiService {

    @POST("update_fcm_token")
    suspend fun updateFcmToken(
        @Body data: UpdateFcmTokenRequestData
    ): Response<Unit>
}