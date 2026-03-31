package com.kopim.productlist.data.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _navCommands = MutableSharedFlow<NavCommand>(extraBufferCapacity = 16)
    val navCommands: SharedFlow<NavCommand> = _navCommands.asSharedFlow()

    fun navigate(route: Screen) {
        viewModelScope.launch {
            _navCommands.emit(NavCommand.Push(route))
        }
    }

    fun popBackStack() {
        viewModelScope.launch {
            _navCommands.emit(NavCommand.Pop)
        }
    }

    fun popBackStack(count: Int) {
        if (count <= 0) return
        viewModelScope.launch {
            _navCommands.emit(NavCommand.PopMultiple(count))
        }
    }
}
