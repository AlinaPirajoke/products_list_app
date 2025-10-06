package com.kopim.productlist.data.model.database

import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductListData

interface DatabaseConnectionInterface {
    suspend fun getProductHints(query: String): List<Hint>

    suspend fun updateProducts(hints: List<Hint>)

    suspend fun getCartWithUpdates(cartId: Long): ProductListData

    suspend fun updateCart(data: ProductListData, cartId: Long)

    suspend fun addChange(change: LocalChange.AdditionChange)

    suspend fun addChange(change: LocalChange.CheckChange)

    suspend fun addChange(change: LocalChange.RenameChange)

    suspend fun getAdditionChangesByCart(cartId: Long): List<LocalChange.AdditionChange>

    suspend fun getAdditionChanges(): List<LocalChange.AdditionChange>

    suspend fun getRenameChanges(): List<LocalChange.RenameChange>

    suspend fun getCheckChanges(): List<LocalChange.CheckChange>

    suspend fun removeAdditionChanges()

    suspend fun removeRenameChanges()

    suspend fun removeCheckChanges()

}