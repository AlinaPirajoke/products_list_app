package com.kopim.productlist.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kopim.productlist.ui.screens.EditListScreen

data class EditListNavPoint(
    val listId: Long,
) : Screen {

    @Composable
    override fun Content() {
        EditListScreen(listId = listId)
    }
}
