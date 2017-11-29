package com.ishabaev.testapp.api

import com.ishabaev.testapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {

    @Volatile
    private var _currencies: CurrenciesInterface? = null
    val currencies: CurrenciesInterface
        get() {
            if (_currencies == null) {
                _currencies = create(CurrenciesInterface::class.java)
            }
            return _currencies!!
        }

    private fun <T> create(tClass: Class<T>): T {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(buildClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(tClass)
    }

    private fun buildClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(10, TimeUnit.SECONDS)
    }.build()
}