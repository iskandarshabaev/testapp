package com.ishabaev.testapp.screen.currency

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ishabaev.testapp.R
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.presenter.currency.CurrencyPresenter
import com.ishabaev.testapp.screen.BaseFragment

class CurrencyListFragment : BaseFragment<CurrencyView, CurrencyPresenter>(), CurrencyView {

    private lateinit var currenciesList: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_currency_list, container, false)
        findViews(view)
        initViews()
        return view
    }

    override val fragmentTag: String
        get() = "CurrencyListFragment"

    override fun onCreatePresenter(savedInstanceState: Bundle?): CurrencyPresenter =
            CurrencyPresenter()

    private fun findViews(view: View) {
        currenciesList = view.findViewById(R.id.currencies_list)
    }

    private var isListIdle = true

    private fun initViews() {
        val lagoutManager = LinearLayoutManager(context)
        currenciesList.layoutManager = lagoutManager
        currenciesList.itemAnimator = null
        currencyAdapter = CurrencyAdapter(currenciesList, layoutInflater, mutableListOf())
        currenciesList.adapter = currencyAdapter
        currenciesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                isListIdle = RecyclerView.SCROLL_STATE_IDLE == newState
            }
        })
        currencyAdapter.listener = object : CurrencyAdapter.OnItemSelectedListener {
            override fun onIemSelected(position: Int, currentItem: Currency) {
                presenter.changeBase(currentItem)
                lagoutManager.scrollToPositionWithOffset(0, 0)
            }
        }
    }

    override fun updateCurrency(currencies: MutableList<Currency>) {
        if (isListIdle) {
            currencyAdapter.updateDataSet(currencies)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}