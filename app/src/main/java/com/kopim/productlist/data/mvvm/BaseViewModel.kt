package com.kopim.productlist.data.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {
    private val _navigateTo = MutableSharedFlow<Screen>()
    val navigateTo: SharedFlow<Screen> = _navigateTo

    fun navigate(route: Screen){
        viewModelScope.launch {
            _navigateTo.emit(route)
        }
    }
}