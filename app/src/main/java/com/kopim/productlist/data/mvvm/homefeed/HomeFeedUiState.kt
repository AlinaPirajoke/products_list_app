package com.kopim.productlist.data.mvvm.homefeed

import com.kopim.productlist.data.utils.ShortCartData

data class HomeFeedUiState(
    val lists: List<ShortCartData> = emptyList(),
    val account: AccountSidebarState = AccountSidebarState(),
)
