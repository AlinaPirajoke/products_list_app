package com.kopim.productlist.data.model.database

import android.util.Log
import com.kopim.productlist.data.model.database.entities.CartDbEntity
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.utils.Hint
import com.kopim.productlist.data.utils.LocalChange
import com.kopim.productlist.data.utils.ProductData.Companion.applyCheckChanges
import com.kopim.productlist.data.utils.ProductData.Companion.applyRenameChanges
import com.kopim.productlist.data.utils.ProductListData
import com.kopim.productlist.data.utils.TimeHelper

private const val LOG_TAG = "DataBaseConnection"

class DatabaseConnection(val database: AppDatabase) : DatabaseConnectionInterface {
    override suspend fun getProductHints(query: String): List<Hint> =
        database.productDao().getProductsByKey(query).map {
            Log.i(LOG_TAG, "Getting hints $it")
            it.toHint()
        }

    override suspend fun updateProducts(hints: List<Hint>) {
        Log.i(LOG_TAG, "Updating hints $hints")
        database.productDao().upsertAll(hints.map { it.toProductDbEntity() })
    }

    override suspend fun getCartWithUpdates(cartId: Long): ProductListData {
        val items = database.listItemDao()
                .getItemsByCartAfterDate(cartId, TimeHelper.getYesterdayDate())
                .map { it.toProductData() }
                .toMutableList()
        items.applyCheckChanges(getCheckChanges())
        items.applyRenameChanges(getRenameChanges())

        val additions = getAdditionChangesByCart(cartId)
        return ProductListData(items, additions)
    }

    override suspend fun updateCart(data: ProductListData, cartId: Long) {
        Log.i(LOG_TAG, "Updating cart $data")
        database.listItemDao().cleanCart(cartId)
        data.usualItems.forEach {
            database.cartDao().upsert(CartDbEntity(cartId.toLong(), null))
            database.productDao().upsert(it.toProductDbEntity())
            database.listItemDao()
                .upsert(it.toListItemDbEntity(cartId.toLong(), it.productId.toLong()))
        }
    }

    override suspend fun addChange(change: LocalChange.AdditionChange) {
        database.localAdditionChangeDao().upsert(change.toLocalAdditionChangeEntity())
    }

    override suspend fun addChange(change: LocalChange.CheckChange) {
        database.localCheckChangeDao().upsert(change.toLocalCheckChangeEntity())
    }

    override suspend fun addChange(change: LocalChange.RenameChange) {
        database.localRenameChangeDao().upsert(change.toLocalRenameChangeEntity())
    }

    override suspend fun getAdditionChangesByCart(cartId: Long): List<LocalChange.AdditionChange> {
        return database.localAdditionChangeDao().getChangesByCart(cartId).map {
            Log.d(LOG_TAG, "Add changes: $it")
            it.toLocalChange()
        }.also { Log.d(LOG_TAG, "All addition changes: $it") }
    }

    override suspend fun getAdditionChanges(): List<LocalChange.AdditionChange> {
        return database.localAdditionChangeDao().getChanges().map {
            Log.d(LOG_TAG, "Add changes: $it")
            it.toLocalChange()
        }.also { Log.d(LOG_TAG, "All addition changes: $it") }
    }

    override suspend fun getRenameChanges(): List<LocalChange.RenameChange> {
        return database.localRenameChangeDao().getChanges().map {
            it.toLocalChange()
        }
    }

    override suspend fun getCheckChanges(): List<LocalChange.CheckChange> {
        return database.localCheckChangeDao().getChanges().map {
            it.toLocalChange()
        }.also { Log.d(LOG_TAG, "All check changes: $it") }

    }

    override suspend fun removeAdditionChanges() {
        database.localAdditionChangeDao().cleanChanges()
    }

    override suspend fun removeRenameChanges() {
        database.localRenameChangeDao().cleanChanges()
    }

    override suspend fun removeCheckChanges() {
        Log.e(LOG_TAG, "Removing check changes")
        database.localCheckChangeDao().cleanChanges()
    }
}