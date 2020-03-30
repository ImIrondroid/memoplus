package com.memo.project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.memo.project.databinding.ItemPictureBinding
import com.memo.project.base.BaseRecyclerViewAdapter
import com.memo.project.base.BaseViewHolder
import com.memo.project.util.OnItemSelectedListener
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureAdapter(
    val status : Boolean = false
) : BaseRecyclerViewAdapter<String>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return false
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return false
        }
    }
) {
    private var onItemSelectedListener : OnItemSelectedListener<String>? = null

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener<String>) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        return PictureViewHolder(
            ItemPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class PictureViewHolder(
        private val binding : ItemPictureBinding
    ) : BaseViewHolder<String>(binding) {
        override fun onBind(item: String?) {
            super.onBind(item)

            if(item==null) return
            if(status) {
                itemView.close_group.visibility = View.VISIBLE
                itemView.close.setOnClickListener {
                        onItemSelectedListener?.invoke(it, item, adapterPosition)
                }
            } else {
                val param = itemView.item_group.layoutParams
                param.width = 420
                param.height = 540
                itemView.item_group.layoutParams = param
            }
        }
    }
}


