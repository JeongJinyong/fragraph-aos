package com.depromeet.fragraph.feature.report.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.databinding.ItemHistoryBinding
import com.depromeet.fragraph.feature.report.model.HistoryUiModel

class HistoryRecyclerViewAdapter(
    val scale: Float,
): RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>(), IRecyclerViewAdapter<HistoryUiModel> {

    private val historyList = mutableListOf<HistoryUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.item_history, parent, false)
        binding.flHistoryFront.cameraDistance = scale
        binding.flHistoryBack.cameraDistance = scale
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount(): Int = historyList.size

    override fun setItems(data: List<HistoryUiModel>) {
        historyList.clear()
        historyList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        var history: HistoryUiModel? = null

        fun bind(history: HistoryUiModel) {
            this.history = history
            binding.history = history

            val mSetRightIn: AnimatorSet = AnimatorInflater.loadAnimator(itemView.context, R.animator.hisotry_flip_right_in) as AnimatorSet
            val mSetRightOut: AnimatorSet = AnimatorInflater.loadAnimator(itemView.context, R.animator.hisotry_flip_right_out) as AnimatorSet
            val mSetLeftIn: AnimatorSet = AnimatorInflater.loadAnimator(itemView.context, R.animator.hisotry_flip_left_in) as AnimatorSet
            val mSetLeftOut: AnimatorSet = AnimatorInflater.loadAnimator(itemView.context, R.animator.hisotry_flip_left_out) as AnimatorSet

            binding.flHistoryItem.setOnClickListener {
                if (!history.isBack) {
                    mSetRightOut.setTarget(binding.flHistoryFront)
                    mSetLeftIn.setTarget(binding.flHistoryBack)
                    mSetRightOut.start()
                    mSetLeftIn.start()
                    history.isBack = true
                } else {
                    mSetRightIn.setTarget(binding.flHistoryFront)
                    mSetLeftOut.setTarget(binding.flHistoryBack)
                    mSetRightIn.start()
                    mSetLeftOut.start()
                    history.isBack = false
                }
            }
        }
    }
}