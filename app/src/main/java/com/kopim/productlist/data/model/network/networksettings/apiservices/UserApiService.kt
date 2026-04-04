package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.profile.ChangeUserPasswordRequestData
import com.kopim.productlist.data.model.network.apimodels.profile.ProfileResponseData
import com.kopim.productlist.data.model.network.apimodels.profile.UpdateUserProfileRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService : ApiService {

    @GET("get_user_profile")
    suspend fun getUserProfile(): Response<ProfileResponseData>

    @POST("update_user_profile")
    suspend fun updateUserProfile(@Body body: UpdateUserProfileRequestData): Response<ProfileResponseData>

    @POST("change_user_password")
    suspend fun changeUserPassword(@Body body: ChangeUserPasswordRequestData): Response<Unit>
}
