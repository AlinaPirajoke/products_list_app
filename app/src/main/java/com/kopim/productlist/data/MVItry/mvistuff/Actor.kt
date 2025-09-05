package com.kopim.productlist.data.MVItry.mvistuff

import com.badoo.reaktive.observable.Observable

typealias Actor<State, Intent, Effect> = (State, Intent) -> Observable<Effect>
