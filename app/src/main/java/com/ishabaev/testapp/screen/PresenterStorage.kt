package com.ishabaev.testapp.screen

object PresenterStorage {
    private var presenters = HashMap<String, BasePresenter>()

    fun putPresenter(tag: String, presenter: BasePresenter) {
        presenters.put(tag, presenter)
    }

    fun removePresenter(tag: String) {
        presenters.remove(tag)
    }
}