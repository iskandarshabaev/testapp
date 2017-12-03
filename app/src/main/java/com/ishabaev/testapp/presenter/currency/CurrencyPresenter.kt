package com.ishabaev.testapp.presenter.currency

import android.support.v7.util.DiffUtil
import com.ishabaev.testapp.api.API
import com.ishabaev.testapp.floorTo
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.presenter.BasePresenter
import com.ishabaev.testapp.screen.currency.CurrencyView
import com.ishabaev.testapp.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class CurrencyPresenter : BasePresenter<CurrencyView>() {

    private var disposable: Disposable? = null
    private var currentItem = Currency("EUR", 1f)
    private var oldItems: List<Currency> = listOf()

    fun onResume() {
        disposable = Observable.just(true)
                .flatMap { API.currencies.loadCurrencies(currentItem.name) }
                .map { it.rates }
                .map { it.toList().map { Currency(it.first, it.second.floorTo(3)) } }
                .map { it.toMutableList() }
                .map { it.apply { it.sortBy { it.name } } }
                .applySchedulers()
                .doOnNext({ handleCurrencies(it) })
                .doOnError { view?.get()?.showError(it) }
                .onErrorReturn { mutableListOf() }
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { e -> e.printStackTrace() })
    }

    private fun handleCurrencies(currencies: MutableList<Currency>) {
        Observable.just(currencies)
                .map { it.apply { it.add(0, currentItem) } }
                .map { Pair(currencies, DiffUtil.calculateDiff(CurrencyDiffCallback(it, oldItems))) }
                .applySchedulers()
                .subscribe({
                    oldItems = it.first
                    view?.get()?.updateCurrency(it.first, it.second)
                })
    }

    fun changeBase(currency: Currency, clickIndex: Int) {
        currentItem = currency
        Collections.swap(oldItems, 0, clickIndex)
    }

    fun onPause() {
        disposable?.dispose()
    }
}