package com.kopim.productlist.database

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kopim.productlist.data.model.database.DatabaseConnection
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.model.database.utils.DatabaseProvider
import com.kopim.productlist.data.utils.LocalChange
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseConnectionTest {

    val dbc = DatabaseConnection(getDatabase())

    fun getDatabase(): AppDatabase {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        return DatabaseProvider.getDatabase(appContext)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun read_check_change(){
        val change1 = LocalChange.CheckChange(checked = true, itemId = 128)
        val change2 = LocalChange.CheckChange(checked = false, itemId = 342)
        var result: List<LocalChange.CheckChange>? = null
        GlobalScope.launch{
            dbc.addChange(change1)
            dbc.addChange(change2)
            result = dbc.getCheckChanges()
            assertEquals(result.size, 2)
            assertEquals(result[1].itemId, change1.itemId)
            assertEquals(result[1].checked, change1.checked)
            assertEquals(result[2].itemId, change2.itemId)
            assertEquals(result[2].checked, change2.checked)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun read_check_change_twice(){
        val change1 = LocalChange.CheckChange(checked = true, itemId = 128)
        var result1: List<LocalChange.CheckChange>? = null
        var result2: List<LocalChange.CheckChange>? = null
        GlobalScope.launch{
            dbc.addChange(change1)

            result1 = dbc.getCheckChanges()
            assertEquals(result1.size, 2)
            assertEquals(result1[1].itemId, change1.itemId)
            assertEquals(result1[1].checked, change1.checked)

            result2 = dbc.getCheckChanges()
            assertEquals(result2.size, 2)
            assertEquals(result2[1].itemId, change1.itemId)
            assertEquals(result2[1].checked, !change1.checked)
        }
    }

}