package com.kopim.productlist.data.model.network.connections.fcm

interface FcmNetworkConnectionInterface {

    suspend fun sendFcmToken(token: String)
}