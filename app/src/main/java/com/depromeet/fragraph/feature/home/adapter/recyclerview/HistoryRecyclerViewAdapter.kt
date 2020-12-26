package com.depromeet.fragraph.feature.home.adapter.recyclerview

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.core.ext.milliSecondsToDay
import com.depromeet.fragraph.databinding.ItemHistoryBinding
import com.depromeet.fragraph.feature.home.model.HistoryUiModel
import timber.log.Timber
import java.util.concurrent.Flow

class HistoryRecyclerViewAdapter(
    private var lifecycleOwner: LifecycleOwner,
    private val scale: Float,
    private var positionLocaleDay: Int,
): RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>(), IRecyclerViewAdapter<HistoryUiModel> {

    var previousPosition = -1
    var currentPosition = -1
    private val historyList = mutableListOf<HistoryUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemHistoryBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_history,
            parent,
            false
        )
        binding.flHistoryFront.cameraDistance = scale
        binding.flHistoryBack.cameraDistance = scale
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Timber.d("position: ${holder.bindingAdapterPosition}")
        holder.bind(historyList[position], lifecycleOwner)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun setItems(data: List<HistoryUiModel>) {
        previousPosition = currentPosition
        historyList.clear()
        historyList.addAll(data)
        currentPosition = historyList.indexOfFirst {
            it.createdAt.milliSecondsToDay().toInt() == positionLocaleDay
        }
        notifyDataSetChanged()
    }

    fun setLocaleDay(day: Int) {
        this.positionLocaleDay = day
    }

    fun setLifeCycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun changeCenterValue(position: Int, isCenter: Boolean) {
        if (position == -1) {
            return
        }
        if (historyList.size < position) {
            return
        }
        historyList[position].changeCenterPosition(isCenter)
    }

    class ViewHolder(
        val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val mSetRightIn: AnimatorSet = AnimatorInflater.loadAnimator(
            itemView.context,
            R.animator.hisotry_flip_right_in
        ) as AnimatorSet
        private val mSetLeftOut: AnimatorSet = AnimatorInflater.loadAnimator(
            itemView.context,
            R.animator.hisotry_flip_left_out
        ) as AnimatorSet

        fun bind(history: HistoryUiModel, lifecycleOwner: LifecycleOwner) {
            binding.history = history
            binding.lifecycleOwner = lifecycleOwner
            if (history.isBack) {
                binding.flHistoryBack.rotationY = 0f
                binding.flHistoryBack.alpha = 1f
                binding.flHistoryFront.rotationY = 180f
                binding.flHistoryFront.alpha = 0f
            } else {
                binding.flHistoryFront.rotationY = 0f
                binding.flHistoryFront.alpha = 1f
                binding.flHistoryBack.rotationY = 180f
                binding.flHistoryBack.alpha = 0f
            }

            // 메모가 없다면 flip 되지 않음
            if (history.memo == null) {
                binding.flHistoryItem.setOnClickListener(null)
                return
            }

            binding.flHistoryItem.setOnClickListener {
                if (!history.isBack) {
                    mSetRightIn.setTarget(binding.flHistoryBack)
                    mSetLeftOut.setTarget(binding.flHistoryFront)
                    mSetRightIn.start()
                    mSetLeftOut.start()
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