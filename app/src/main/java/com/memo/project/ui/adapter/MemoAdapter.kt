package com.memo.project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.memo.project.databinding.ItemMemoBinding
import com.memo.project.base.BaseRecyclerViewAdapter
import com.memo.project.base.BaseViewHolder
import com.memo.project.data.local.entity.MemoEntity
import com.memo.project.util.OnItemSelectedListener

class MemoAdapter : BaseRecyclerViewAdapter<MemoEntity>(
    object : DiffUtil.ItemCallback<MemoEntity>() {
        override fun areItemsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.descrption == newItem.descrption
        }
    }
) {

    private var onItemSelectedListener : OnItemSelectedListener<MemoEntity>? = null

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener<MemoEntity>) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MemoEntity> {
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
    ) : BaseViewHolder<MemoEntity>(binding) {
        override fun onBind(item: MemoEntity?) {
            super.onBind(item)

            if(item==null) return
            itemView.setOnClickListener {
                onItemSelectedListener?.invoke(it, item, adapterPosition)
            }
        }
    }
}