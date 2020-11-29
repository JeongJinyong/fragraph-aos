package com.depromeet.fragraph.feature.recommendation.keyword_select.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.ui.IRecyclerViewAdapter
import com.depromeet.fragraph.databinding.ItemKeywordBinding
import com.depromeet.fragraph.feature.recommendation.keyword_select.model.KeywordUiModel
import timber.log.Timber

class KeywordRecyclerViewAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val keywordClickCallback: (id: Int) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRecyclerViewAdapter<KeywordUiModel> {

    private val keywordList = mutableListOf<KeywordUiModel>()

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return KEYWORD_TITLE_VIEW
        }
        return KEYWORD_ITEM_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == KEYWORD_TITLE_VIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keyword_title, parent, false)
            return KeywordTitleViewHolder(view)
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keyword, parent, false)
        return KeywordItemViewHolder(view, keywordClickCallback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            return
        }
        if (position == itemCount -1) {
            return (holder as KeywordItemViewHolder).bind(keywordList[position], lifecycleOwner, true)
        }
        return (holder as KeywordItemViewHolder).bind(keywordList[position], lifecycleOwner, false)
    }
    override fun getItemCount(): Int = keywordList.size

    override fun setItems(data: List<KeywordUiModel>) {
        keywordList.clear()
        keywordList.addAll(data)
        notifyDataSetChanged()
    }

    fun changeKeyword(position: Int) {
        notifyItemChanged(position)
    }

    class KeywordTitleViewHolder(view: View): RecyclerView.ViewHolder(view)

    class KeywordItemViewHolder(
        private val view: View,
        private val keywordClickCallback: (id: Int) -> Unit,
    ): RecyclerView.ViewHolder(view) {

        private val binding by lazy {
            ItemKeywordBinding.bind(view)
        }

        init {
            binding.flKeywordItemContainer.setOnClickListener {
                binding.keyword?.let {keyword ->
                    keywordClickCallback(keyword.id)
                }
            }
        }

        fun bind(keyword: KeywordUiModel, lifecycleOwner: LifecycleOwner, isLast: Boolean) {
            binding.keyword = keyword
            binding.lifecycleOwner = lifecycleOwner
            if (isLast) {
                binding.viewKeywordItemLast.visibility = View.VISIBLE
            } else {
                binding.viewKeywordItemLast.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val KEYWORD_TITLE_VIEW = 0
        private const val KEYWORD_ITEM_VIEW = 1
    }
}