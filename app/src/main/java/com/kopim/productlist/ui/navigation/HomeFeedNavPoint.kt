package com.kopim.productlist.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.kopim.productlist.ui.screens.HomeFeedScreen

/**
 * Стартовый экран: список корзин пользователя.
 * Дальнейшие экраны — отдельные [Screen] (например [CartNavPoint]) с push/pop через [cafe.adriel.voyager.navigator.Navigator].
 */
object HomeFeedNavPoint : Screen {

    @Composable
    override fun Content() {
        HomeFeedScreen()
    }
}
