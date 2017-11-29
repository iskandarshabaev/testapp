package com.ishabaev.testapp.screen

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ishabaev.testapp.R

class CurrencyViewHolder(
        private val v: View) : RecyclerView.ViewHolder(v) {

    private var iconView: ImageView = v.findViewById(R.id.icon)
    private var nameView: TextView = v.findViewById(R.id.name)
    private var valueView: EditText = v.findViewById(R.id.value)

    public fun bind(currency: Currency) {
        nameView.text = currency.name
        valueView.setText("${currency.value}", TextView.BufferType.EDITABLE)
    }

}