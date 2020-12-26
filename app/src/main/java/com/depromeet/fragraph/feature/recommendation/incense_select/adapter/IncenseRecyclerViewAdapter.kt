package com.depromeet.fragraph.feature.recommendation.incense_select.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.databinding.ItemIncenseBinding
import com.depromeet.fragraph.feature.recommendation.incense_select.model.IncenseItemUiModel

class IncenseRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val itemSetCallback: () -> Unit,
): RecyclerView.Adapter<IncenseRecyclerViewAdapter.ViewHolder>(), View.OnTouchListener,
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

    // 얘는 set 이 한번만 일어난다 !!! (혹은 리프레시만 일어남)
    override fun setItems(data: List<IncenseItemUiModel>) {
        incenseList.clear()
        incenseList.addAll(data)
        notifyDataSetChanged()
        itemSetCallback()
    }

    fun changeCenterValue(position: Int, isCenter: Boolean) {
        incenseList[position].changeCenterPosition(isCenter)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        v?.parent?.requestDisallowInterceptTouchEvent(true)
        return false
    }

    inner class ViewHolder(
        val binding: ItemIncenseBinding,
        private val lifecycleOwner: LifecycleOwner
    ): RecyclerView.ViewHolder(binding.root) {


        init {
            binding.hsvIncenseKeywords.setOnTouchListener { v, event ->
                v?.parent?.requestDisallowInterceptTouchEvent(true)
                false
            }
        }

        fun bind(incense: IncenseItemUiModel) {
            binding.incense = incense
            binding.lifecycleOwner = lifecycleOwner
        }
    }
}