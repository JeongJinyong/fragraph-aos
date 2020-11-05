package com.depromeet.fragraph.core.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter

@BindingAdapter("bind_items")
fun <T> RecyclerView.setItems(items : List<T>) {
    (adapter as IRecyclerViewAdapter<T>).setItems(items)
}