package com.ishabaev.testapp.presenter

import com.ishabaev.testapp.screen.BaseView
import java.lang.ref.WeakReference

abstract class BasePresenter<V : BaseView> {

    protected var view: WeakReference<V>? = null

    open public fun attachView(view: V) {
        this.view = WeakReference(view)
    }

    open public fun detachView(view: V) {
        this.view = null
    }

    fun onRemove() {
    }
}