package com.kopim.productlist.data.MVItry.mvistuff.list

import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.disposable.scope.disposableScope
import com.badoo.reaktive.observable.map
import com.kopim.productlist.data.model.datasource.ListDataSource

class ListComponent {

//    private val store = ListStore(ListDataSource())
//    private var view: ListViewInterface? = null
//    private var startStopScope: DisposableScope? = null
//
//    fun onViewCreated(view: ListViewInterface) {
//        this.view = view
//    }
//
//    fun onStart() {
//        val view = requireNotNull(view)
//
//        startStopScope = disposableScope {
////            store.subscribeScoped(onNext = view::render)
//            view.events.map(ListViewInterface.ListViewEvent::toListIntent).subscribeScoped(onNext = store::onNext)
//        }
//    }
//
//    fun onStop() {
//        startStopScope?.dispose()
//    }
//
//    fun onViewDestroyed() {
//        view = null
//    }
//
//    fun onDestroy() {
//        store.dispose()
//    }
}