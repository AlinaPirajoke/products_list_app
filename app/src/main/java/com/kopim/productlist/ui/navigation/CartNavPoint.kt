package com.kopim.productlist.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kopim.productlist.ui.screens.CartScreen

data class CartNavPoint( // Screen-ом я уже обозвал функции рисующие экраны, так что будет NavPoint-ом
    val listId: Long
) : Screen {

    @Composable
    override fun Content() {
        CartScreen(listId = listId)
    }
}