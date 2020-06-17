package com.memo.project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.memo.project.databinding.ItemPictureBinding
import com.memo.project.base.BaseRecyclerViewAdapter
import com.memo.project.base.BaseViewHolder
import com.memo.project.data.model.MemoImage
import com.memo.project.util.OnItemSelectedListener
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureAdapter(
    val status : Boolean = false
) : BaseRecyclerViewAdapter<MemoImage>(
    object : DiffUtil.ItemCallback<MemoImage>() {
        override fun areItemsTheSame(oldItem: MemoImage, newItem: MemoImage): Boolean {
            return false
        }
        override fun areContentsTheSame(oldItem: MemoImage, newItem: MemoImage): Boolean {
            return false
        }
    }
) {
    private var onItemSelectedListener : OnItemSelectedListener<MemoImage>? = null

    fun setOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener<MemoImage>) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MemoImage> {
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
    ) : BaseViewHolder<MemoImage>(binding) {
        override fun onBind(item: MemoImage?) {
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


