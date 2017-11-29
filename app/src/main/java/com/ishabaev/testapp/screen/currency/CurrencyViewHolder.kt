package com.ishabaev.testapp.screen.currency

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ishabaev.testapp.R
import com.ishabaev.testapp.model.Currency

class CurrencyViewHolder(
        private val v: View) : RecyclerView.ViewHolder(v) {

    private var iconView: ImageView = v.findViewById(R.id.icon)
    private var nameView: TextView = v.findViewById(R.id.name)
    private var valueView: EditText = v.findViewById(R.id.value)

    init {
        Log.d("initialized", this.toString())
    }

    public fun bind(currency: Currency) {
        nameView.text = currency.name
        valueView.isEnabled = false
        val text = "${currency.value}"
        if (valueView.text.toString() != text) {
            valueView.setText(text, TextView.BufferType.EDITABLE)
        }
    }

}