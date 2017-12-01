package com.ishabaev.testapp.presenter.currency

import android.support.v7.util.DiffUtil

import com.ishabaev.testapp.model.Currency

class MyDiffCallback(
        private val newItems: List<Currency>,
        private val oldItems: List<Currency>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size
    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldItems[oldPosition].name == newItems[newPosition].name
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldItems[oldPosition].value == newItems[newPosition].value
    }

    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}
