package com.ishabaev.testapp.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesInterface {

    @GET("latest")
    fun loadCurrencies(@Query("base") base: String): Observable<CurrencyResponse>
}