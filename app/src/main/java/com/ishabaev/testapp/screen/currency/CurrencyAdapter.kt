package com.ishabaev.testapp.screen.currency

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ishabaev.testapp.R
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.presenter.currency.MyDiffCallback


class CurrencyAdapter(
        private var recyclerView: RecyclerView,
        private var inflater: LayoutInflater,
        private var items: MutableList<Currency>
) : RecyclerView.Adapter<CurrencyViewHolder>(), View.OnTouchListener, TextWatcher {

    var listener: OnItemSelectedListener? = null

    private var currentItem: Currency = Currency("EUR", 1f)

    override fun onBindViewHolder(holder: CurrencyViewHolder?, position: Int) {
        Glide.with(recyclerView.context)
                .load("https://www.ecb.europa.eu/shared/img/flags/${items[position].name}.gif")
                .listener(null)
                .into(holder!!.iconView)

        if (position > 0) {
            holder.bind(currentItem.value, items[position])
            holder.valueView.clearFocus()
            holder.valueView.isFocusable = false
            holder.valueView.isFocusableInTouchMode = false
            holder.valueView.isClickable = false
            holder.valueView.tag = position
            holder.valueView.removeTextChangedListener(this)
        } else {
            holder.valueView.tag = 0
            holder.valueView.isFocusable = true
            holder.valueView.isClickable = true
            holder.valueView.isFocusableInTouchMode = true
            holder.valueView.requestFocus()
            holder.valueView.setText("${currentItem.value}", TextView.BufferType.EDITABLE)
            holder.nameView.text = currentItem.name
            holder.valueView.addTextChangedListener(this)
            holder.valueView.setSelection(holder.valueView.text.length)
        }
    }

    override fun afterTextChanged(p0: Editable) {
        //try {
        val text = p0.toString()
        if (!text.isEmpty()) {
            val value = text.toFloatOrNull()
            if (value != null) {
                currentItem.value = value
                recyclerView.post {
                    notifyItemRangeChanged(1, items.size)
                }
            }
        }
        /*} catch (e: Throwable) {
            e.printStackTrace()
        }*/

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CurrencyViewHolder? {
        val view = inflater.inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view).apply {
            valueView.setOnTouchListener(this@CurrencyAdapter)
        }
    }

    override fun getItemCount() = items.size

    fun updateDataSet(data: MutableList<Currency>) {
        data.add(0, currentItem)
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(data, this.items))
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        if (p1.action == MotionEvent.ACTION_UP) {
            val index = p0.tag as Int
            val item = items[index]
            if (item.name != currentItem.name) {
                currentItem = item
                items.removeAt(index)
                items.add(0, item)
                listener?.onIemSelected(index, item)
                notifyDataSetChanged()
            }
            return false
        }
        return false
    }

    interface OnItemSelectedListener {
        fun onIemSelected(position: Int, currentItem: Currency)
    }
}