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

class HistoryRecyclerViewAdapter(
    private var lifecycleOwner: LifecycleOwner,
    private val scale: Float,
    private var positionLocaleDay: Int,
    private val firstScrollCallback: (position: Int) -> Unit,
): RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>(), IRecyclerViewAdapter<HistoryUiModel> {

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
        historyList.clear()
        historyList.addAll(data)
        notifyDataSetChanged()
        val position = historyList.indexOfFirst {
            it.createdAt.milliSecondsToDay().toInt() == positionLocaleDay
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

        private val mSetRightIn: AnimatorSet = AnimatorInflater.loadAnimator(
            itemView.context,
            R.animator.hisotry_flip_right_in
        ) as AnimatorSet
        private val mSetLeftOut: AnimatorSet = AnimatorInflater.loadAnimator(
            itemView.context,
            R.animator.hisotry_flip_left_out
        ) as AnimatorSet

        init {
//            val moreContainer: FrameLayout = itemView.findViewById(R.id.iv_history_more_container)
//            moreContainer.setOnTouchListener { v, event ->
//                v?.parent?.requestDisallowInterceptTouchEvent(true)
//                false
//            }
        }

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