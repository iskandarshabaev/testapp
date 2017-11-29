package com.ishabaev.testapp.screen

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ishabaev.testapp.Repository
import com.ishabaev.testapp.api.API
import com.ishabaev.testapp.api.CurrencyResponse
import com.ishabaev.testapp.utils.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        update()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun update() {
        API.currencies.loadCurrencies("EUR")
                .applySchedulers()
                .doOnNext({ handleCurrencies(it) })
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { e -> e.printStackTrace() })
    }

    private fun handleCurrencies(currencies: CurrencyResponse) {
        Repository.update(currencies)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}