package com.ishabaev.testapp.utils

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView.ViewHolder

class CustomItemAnimator : DefaultItemAnimator() {
    override fun canReuseUpdatedViewHolder(viewHolder: ViewHolder, payloads: List<Any>): Boolean = true
}
