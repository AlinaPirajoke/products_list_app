package com.kopim.productlist.data.utils

object Validator {
    private const val MIN_CART_ID = 0

    fun validateChange(change: LocalChange) =
        when(change){
            is LocalChange.AdditionChange -> change.name.isNotBlank() && change.cartId > MIN_CART_ID
            is LocalChange.CheckChange -> true
            is LocalChange.RenameChange -> change.newName.isNotBlank()
        }
}