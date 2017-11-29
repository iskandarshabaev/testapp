package com.ishabaev.testapp.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ishabaev.testapp.R
import com.ishabaev.testapp.Repository
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : AppCompatActivity() {

    private lateinit var currenciesList: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()
        initViews()
        Repository.subscribeToConcurrencyResponse()
                .map { it.rates }
                .map { r -> r.map { Currency(it.key, it.value) } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyAdapter.updateDataSet(it) }, Throwable::printStackTrace)
        startService(Intent(this, CurrencyService::class.java))
    }

    private fun findView() {
        currenciesList = findViewById(R.id.currencies_list)
    }

    private fun initViews() {
        currenciesList.layoutManager = LinearLayoutManager(this)
        currenciesList.itemAnimator = DefaultItemAnimator()
        currencyAdapter = CurrencyAdapter(layoutInflater, mutableListOf())
        currenciesList.adapter = currencyAdapter
    }

}
