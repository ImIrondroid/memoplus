package com.memo.project.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.memo.project.BR

open class BaseViewHolder<T>(
    private val binding : ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context : Context
        get() = itemView.context

    open fun onBind(item : T?) {
        binding.apply {
            setVariable(BR.item, item)
            executePendingBindings()
        }
    }
}