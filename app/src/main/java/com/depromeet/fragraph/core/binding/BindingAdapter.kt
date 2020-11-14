package com.depromeet.fragraph.core.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.depromeet.fragraph.base.GlideApp
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter

@BindingAdapter("bind_items")
fun <T> RecyclerView.setItems(items : List<T>) {
    (adapter as IRecyclerViewAdapter<T>).setItems(items)
}

@BindingAdapter("image_from_url")
fun ImageView.bindImageFromUrl(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(this.context)
            .load(imageUrl)
            .placeholder(ColorDrawable(Color.GRAY))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

@BindingAdapter("bind_total_value")
fun ProgressBar.bindTotalValue(value: Int) {
    this.max = value
}

@BindingAdapter("bind_progress_value")
fun ProgressBar.bindProgressValue(value: Int) {
    this.progress = value
}