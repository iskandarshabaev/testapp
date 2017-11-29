package com.ishabaev.testapp.screen.currency

import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.screen.BaseView

interface CurrencyView: BaseView {

    fun updateCurrency(currencies: List<Currency>)

}