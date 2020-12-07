package com.depromeet.fragraph.feature.report.adapter.recyclerview

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.core.ext.FRAGRAPH_HISTORY_FORMAT
import com.depromeet.fragraph.core.ext.miliSecondsToDay
import com.depromeet.fragraph.core.ext.miliSecondsToStringFormat
import com.depromeet.fragraph.databinding.ItemHistoryBinding
import com.depromeet.fragraph.feature.report.model.HistoryUiModel
import timber.log.Timber
import java.time.LocalDate

class HistoryRecyclerViewAdapter(
    private var lifecycleOwner: LifecycleOwner,
    private val scale: Float,
    private var positionLocaleDay: Int,
    private val firstScrollCallback: (position: Int) -> Unit
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
//        Timber.d("position: ${holder.bindingAdapterPosition}")
        holder.bind(historyList[position], lifecycleOwner)
    }

    override fun getItemCount(): Int = historyList.size

    override fun setItems(data: List<HistoryUiModel>) {
        historyList.clear()
        historyList.addAll(data)
        notifyDataSetChanged()
        val position = historyList.indexOfFirst {
            it.createdAt.miliSecondsToDay().toInt() == positionLocaleDay
        }
        firstScrollCallback(position)
    }

    fun setLocaleDay(day: Int) {
        this.positionLocaleDay = day
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun changeCenterValue(position: Int, isCenter: Boolean) {
        historyList[position].changeCenterPosition(isCenter)
    }

    class ViewHolder(
        val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryUiModel, lifecycleOwner: LifecycleOwner) {
            binding.history = history
            binding.lifecycleOwner = lifecycleOwner

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