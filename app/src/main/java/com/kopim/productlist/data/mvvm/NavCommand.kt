package com.kopim.productlist.data.mvvm

import cafe.adriel.voyager.core.screen.Screen

sealed class NavCommand {
    data class Push(val screen: Screen) : NavCommand()
    data object Pop : NavCommand()
    data class PopMultiple(val count: Int) : NavCommand()
}
