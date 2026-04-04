package com.kopim.productlist.data.model.network.connections.user

import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.apimodels.login.LoginResponseData
import com.kopim.productlist.data.model.network.apimodels.login.LoginWithCredentialsRequestData
import com.kopim.productlist.data.model.network.apimodels.profile.ChangeUserPasswordRequestData
import com.kopim.productlist.data.model.network.apimodels.profile.ProfileResponseData
import com.kopim.productlist.data.model.network.apimodels.profile.UpdateUserProfileRequestData
import com.kopim.productlist.data.model.network.connections.BaseNetworkConnection
import com.kopim.productlist.data.model.network.networksettings.apiservices.UserApiService
import retrofit2.Response

class UserProfileNetworkConnection(
    override val connection: UserApiService,
    spm: SharedPreferencesManager,
) : BaseNetworkConnection(connection, spm), UserProfileNetworkConnectionInterface {

    override suspend fun getUserProfile(): Response<ProfileResponseData>? =
        safeRequest {
            if (checkLogin()) connection.getUserProfile() else null
        }

    override suspend fun updateUserProfile(
        displayName: String?,
        profileColor: String?,
    ): Response<ProfileResponseData>? =
        safeRequest {
            if (checkLogin()) {
                connection.updateUserProfile(
                    UpdateUserProfileRequestData(
                        display_name = displayName,
                        profile_color = profileColor,
                    ),
                )
            } else null
        }

    override suspend fun changeUserPassword(newPassword: String): Response<Unit>? =
        safeRequest {
            if (checkLogin()) {
                connection.changeUserPassword(ChangeUserPasswordRequestData(new_password = newPassword))
            } else null
        }

    override suspend fun relogin(): Boolean = loginUser()

    override suspend fun loginWithCredentials(
        userId: String,
        password: String,
    ): Response<LoginResponseData>? =
        safeRequest {
            connection.loginWithCredentials(
                LoginWithCredentialsRequestData(user_id = userId, password = password),
            )
        }
}
