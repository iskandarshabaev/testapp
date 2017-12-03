package com.ishabaev.testapp.screen.currency

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ishabaev.testapp.R
import com.ishabaev.testapp.floorTo
import com.ishabaev.testapp.model.Currency
import com.ishabaev.testapp.utils.CurrencyNames
import java.util.*


class CurrencyAdapter(
        private var recyclerView: RecyclerView,
        private var inflater: LayoutInflater,
        private var items: MutableList<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.BaseCurrencyViewHolder>(), View.OnTouchListener, TextWatcher {

    var listener: OnItemSelectedListener? = null
    val names = CurrencyNames.names(recyclerView.context)

    private var currentItem: Currency = Currency("EUR", 1f)

    override fun afterTextChanged(p0: Editable) {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCurrencyViewHolder {
        val view = inflater.inflate(R.layout.item_currency, parent, false)
        return if (viewType == 0) {
            HeadViewHolder(view)
        } else {
            CurrencyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseCurrencyViewHolder, position: Int) = Unit
    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    override fun onBindViewHolder(holder: BaseCurrencyViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (position > 0) {
            holder.bind(items[position], position)
            val text = "${(items[position].value * currentItem.value).floorTo(2)}"
            if (holder.valueView.text.toString() != text) {
                holder.valueView.setText(text, TextView.BufferType.EDITABLE)
            }
        } else {
            holder.bind(currentItem, position)
            val text = "${currentItem.value}"
            if (holder.valueView.text.toString() != text) {
                holder.valueView.setText(text, TextView.BufferType.EDITABLE)
            }
            holder.valueView.setSelection(holder.valueView.text.length)
        }
    }

    open inner class BaseCurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var iconView: ImageView = view.findViewById(R.id.icon)
        private var nameView: TextView = view.findViewById(R.id.name)
        private var fullNameView: TextView = view.findViewById(R.id.full_name)
        var valueView: EditText = view.findViewById(R.id.value)

        fun bind(currency: Currency, position: Int) {
            valueView.tag = position
            nameView.text = currency.name
            fullNameView.text = names[currency.name]
            Glide.with(recyclerView.context)
                    .load("https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/${currency.name.toLowerCase()}.png")
                    .listener(null)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iconView)
        }
    }

    inner class HeadViewHolder(view: View) : BaseCurrencyViewHolder(view) {
        init {
            valueView.addTextChangedListener(this@CurrencyAdapter)
        }
    }

    inner class CurrencyViewHolder(v: View) : BaseCurrencyViewHolder(v) {
        init {
            valueView.setOnTouchListener(this@CurrencyAdapter)
        }
    }

    override fun getItemCount() = items.size

    fun updateDataSet(data: MutableList<Currency>, diffResult: DiffUtil.DiffResult) {
        currentItem = data[0]
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onTouch(p0: View, p1: MotionEvent): Boolean {
        if (p1.action == MotionEvent.ACTION_UP) {
            val currentIndex = 0
            val clickIndex = p0.tag as Int
            val item = items[clickIndex]
            if (item.name != currentItem.name) {
                Collections.swap(items, currentIndex, clickIndex)
                currentItem = item
                listener?.onIemSelected(clickIndex, item)
                notifyItemMoved(clickIndex, currentIndex)
            }
        }
        return false
    }

    interface OnItemSelectedListener {
        fun onIemSelected(position: Int, currentItem: Currency)
    }
}