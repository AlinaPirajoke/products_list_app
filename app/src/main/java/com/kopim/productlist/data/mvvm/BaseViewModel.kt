package com.kopim.productlist.data.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {
    private val _navigateTo = MutableSharedFlow<String>()
    val navigateTo: SharedFlow<String> = _navigateTo

    fun navigate(route: String){
        viewModelScope.launch {
            _navigateTo.emit(route)
        }
    }
}