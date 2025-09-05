package com.kopim.productlist.data.MVItry.mvistuff.view

import com.badoo.reaktive.observable.Observable

interface MviView<in Model : Any, out Event : Any> {
    val events: Observable<Event>

    fun render(model: Model?)
}