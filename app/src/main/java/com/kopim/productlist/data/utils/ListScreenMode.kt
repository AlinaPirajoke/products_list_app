package com.kopim.productlist.data.utils

sealed class ListScreenMode {
    object CartMode: ListScreenMode()
    object AdditionMode: ListScreenMode()
}