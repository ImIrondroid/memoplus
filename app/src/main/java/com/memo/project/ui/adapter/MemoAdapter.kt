package com.memo.project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.memo.project.databinding.ItemMemoBinding
import com.memo.project.base.BaseRecyclerViewAdapter
import com.memo.project.base.BaseViewHolder
import com.memo.project.data.local.entity.Memo
import com.memo.project.util.OnItemSelectedListener

class MemoAdapter : BaseRecyclerViewAdapter<Memo>(
    object : DiffUtil.ItemCallback<Memo>() {
        override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.descrption == newItem.descrption
        }
    }
) {

    private var onItemSelectedListener : OnItemSelectedListener<Memo>? = null

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener<Memo>) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Memo> {
        return MemoViewHolder(
            ItemMemoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MemoViewHolder(
        private val binding : ItemMemoBinding
    ) : BaseViewHolder<Memo>(binding) {
        override fun onBind(item: Memo?) {
            super.onBind(item)
            if(item==null) return
            itemView.setOnClickListener {
                onItemSelectedListener?.invoke(it, item, adapterPosition)
            }
        }
    }
}