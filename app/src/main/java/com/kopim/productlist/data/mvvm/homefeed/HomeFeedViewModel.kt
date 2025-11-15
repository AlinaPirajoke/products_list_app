package com.kopim.productlist.data.mvvm.homefeed

import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.mvvm.BaseViewModel
import com.kopim.productlist.data.mvvm.list.ListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeFeedViewModel(val dataSource: CartsDataSourceInterface) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeFeedUiState())
    val state: StateFlow<HomeFeedUiState> = _state.asStateFlow()

    fun onUpdateDate(){
        dataSource.getUserCarts()
    }

    fun onNavigateToList(listId: Long){

    }
}