package com.depromeet.fragraph.feature.recommendation.feeling_select.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.databinding.ItemFeelingBinding
import com.depromeet.fragraph.feature.recommendation.feeling_select.model.FeelingUiModel
import com.depromeet.fragraph.feature.recommendation.feeling_select.model.feelingClick

class FeelingRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<FeelingRecyclerViewAdapter.ViewHolder>(), IRecyclerViewAdapter<FeelingUiModel> {

    private val feelingList = mutableListOf<FeelingUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFeelingBinding = DataBindingUtil.inflate(inflater, R.layout.item_feeling, parent, false)
        return ViewHolder(binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feelingList[position])
    }

    override fun getItemCount(): Int = feelingList.size

    override fun setItems(data: List<FeelingUiModel>) {
        feelingList.clear()
        feelingList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemFeelingBinding,
        lifecycleOwner: LifecycleOwner
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.lifecycleOwner = lifecycleOwner
            itemView.setOnClickListener {
                binding.feeling?.feelingClick()
            }
        }

        fun bind(feeling: FeelingUiModel) {
            binding.feeling = feeling
        }
    }
}