package com.kopim.productlist.data.model.datasource

import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnectionInterface
import com.kopim.productlist.data.utils.ShortCartData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartsDataSource(private val nc: CartsNetworkConnectionInterface) :
    CartsDataSourceInterface {

    override fun getUserCarts(): Flow<List<ShortCartData>?> {
        val cartsDataFlow = MutableStateFlow<List<ShortCartData>?>(null)

        CoroutineScope(Dispatchers.IO).launch {
            nc.getUserCarts()?.let {
                if (!it.isSuccessful)
                    return@launch
                cartsDataFlow.emit(it.body()?.toShortCartDataList() ?: return@launch)
            }
        }

        return cartsDataFlow
    }

    override fun addUserToCart(cartCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            nc.addUserToCart(cartCode)
        }
    }

    override fun removeUserFromCart(cartId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            nc.removeUserFromCart(cartId)
        }
    }

    override fun renameCart(cartId: Long, newName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            nc.renameCart(cartId, newName)
        }
    }
}