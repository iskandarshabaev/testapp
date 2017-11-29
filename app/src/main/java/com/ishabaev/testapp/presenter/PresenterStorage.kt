package com.ishabaev.testapp.presenter

object PresenterStorage {
    private var presenters = HashMap<String, BasePresenter<*>>()

    fun putPresenter(tag: String, presenter: BasePresenter<*>) {
        presenters.put(tag, presenter)
    }

    fun getPresenter(tag: String): BasePresenter<*>? {
        return presenters[tag]
    }

    fun removePresenter(tag: String) {
        val removed = presenters.remove(tag)
        removed?.onRemove()
    }
}