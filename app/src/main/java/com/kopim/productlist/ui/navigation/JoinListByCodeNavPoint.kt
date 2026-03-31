package com.kopim.productlist.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kopim.productlist.ui.screens.JoinListByCodeScreen

object JoinListByCodeNavPoint : Screen {

    @Composable
    override fun Content() {
        JoinListByCodeScreen()
    }
}
