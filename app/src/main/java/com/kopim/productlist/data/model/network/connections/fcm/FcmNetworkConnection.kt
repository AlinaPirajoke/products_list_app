package com.kopim.productlist.data.model.network.connections.fcm

import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.network.apimodels.updatefcm.UpdateFcmTokenRequestData
import com.kopim.productlist.data.model.network.connections.BaseNetworkConnection
import com.kopim.productlist.data.model.network.networksettings.apiservices.FcmApiService

class FcmNetworkConnection(
    override val connection: FcmApiService,
    spm: SharedPreferencesManager
) : BaseNetworkConnection(connection, spm), FcmNetworkConnectionInterface {

    override suspend fun sendFcmToken(token: String) {
        safeRequest {
            if (checkLogin()) {
                connection.updateFcmToken(
                    UpdateFcmTokenRequestData(token)
                )
            }
        }
    }
}