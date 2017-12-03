package com.ishabaev.testapp.screen.currency

import android.support.v7.util.DiffUtil
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.screen.BaseView

interface CurrencyView : BaseView {

    fun updateCurrency(currencies: MutableList<Currency>, diffResult: DiffUtil.DiffResult)

}