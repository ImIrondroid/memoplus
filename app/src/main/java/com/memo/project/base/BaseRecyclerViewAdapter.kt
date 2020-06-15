package com.memo.project.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerViewAdapter<T>(
    diffCallback : DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<T>>(diffCallback) {

    var limitedItemcount : Int? = null

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(currentList.getOrNull(position))
    }

    override fun getItemCount(): Int {
        return limitedItemcount?.
            coerceAtMost(currentList.size)
            ?: super.getItemCount()
    }
}

