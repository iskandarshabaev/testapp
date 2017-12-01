package com.ishabaev.testapp.presenter.currency

import android.util.Log
import com.ishabaev.testapp.api.API
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.presenter.BasePresenter
import com.ishabaev.testapp.screen.currency.CurrencyView
import com.ishabaev.testapp.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyPresenter : BasePresenter<CurrencyView>() {

    private var disposable: Disposable? = null
    private var currentItem = Currency("EUR", 1f)

    fun onResume() {
        disposable = Observable.just(true)
                .flatMap { API.currencies.loadCurrencies(currentItem.name) }
                .doOnNext { Log.d("das", it.base) }
                .map { it.rates }
                .map { it.toList().map { Currency(it.first, (Math.floor(it.second  * 100.0) / 100).toFloat()) } }
                .map { it.toMutableList() }
                .applySchedulers()
                .doOnNext({ handleCurrencies(it) })
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { e -> e.printStackTrace() })
    }

    private fun handleCurrencies(currencies: MutableList<Currency>) {
        view?.get()?.updateCurrency(currencies)
    }

    fun changeBase(currency: Currency) {
        currentItem = currency
    }

    fun onPause() {
        disposable?.dispose()
    }
}