package com.depromeet.fragraph.feature.recommendation.incense_select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.databinding.ItemIncenseBinding
import com.depromeet.fragraph.feature.recommendation.incense_select.model.IncenseItemUiModel

class IncenseRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<IncenseRecyclerViewAdapter.ViewHolder>(),
    IRecyclerViewAdapter<IncenseItemUiModel> {

    private val incenseList = mutableListOf<IncenseItemUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemIncenseBinding = DataBindingUtil.inflate(inflater, R.layout.item_incense, parent, false)
        return ViewHolder(binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(incenseList[position])
    }

    override fun getItemCount(): Int = incenseList.size

    override fun setItems(data: List<IncenseItemUiModel>) {
        incenseList.clear()
        incenseList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemIncenseBinding,
        private val lifecycleOwner: LifecycleOwner
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.lifecycleOwner = lifecycleOwner
        }

        fun bind(incense: IncenseItemUiModel) {
            binding.incense = incense
        }
    }
}