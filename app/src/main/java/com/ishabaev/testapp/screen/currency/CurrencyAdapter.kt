package com.ishabaev.testapp.screen.currency

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ishabaev.testapp.R
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.presenter.currency.MyDiffCallback


class CurrencyAdapter(
        private var inflater: LayoutInflater,
        private var items: MutableList<Currency>
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onBindViewHolder(holder: CurrencyViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CurrencyViewHolder? =
            CurrencyViewHolder(inflater.inflate(R.layout.item_currency, parent, false))

    override fun getItemCount() = items.size

    public fun updateDataSet(data: List<Currency>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(data, this.items))
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

}