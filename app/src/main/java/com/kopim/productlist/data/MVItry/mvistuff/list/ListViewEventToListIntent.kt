package com.kopim.productlist.data.MVItry.mvistuff.list

fun ListViewInterface.ListViewEvent.toListIntent(): ListStoreInterface.ListIntent =
    when(this){
        is ListViewInterface.ListViewEvent.StartObserve -> ListStoreInterface.ListIntent.StartObserve
        is ListViewInterface.ListViewEvent.StopObserve -> ListStoreInterface.ListIntent.StopObserve
        is ListViewInterface.ListViewEvent.AddProduct -> ListStoreInterface.ListIntent.AddProduct(name)
        is ListViewInterface.ListViewEvent.DeleteProduct -> ListStoreInterface.ListIntent.DeleteProduct(id)
        is ListViewInterface.ListViewEvent.EditField -> ListStoreInterface.ListIntent.EditField(value)
    }