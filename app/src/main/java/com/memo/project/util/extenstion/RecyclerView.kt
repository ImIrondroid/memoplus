package com.memo.project.util.extenstion

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.memo.project.base.BaseRecyclerViewAdapter

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun<T> RecyclerView.binding(list: List<T>? = null){
    (adapter as? BaseRecyclerViewAdapter<T>)?.run {
        submitList(list)
    }
}

@BindingAdapter("adapter")
fun RecyclerView.binding(adapter: RecyclerView.Adapter<*>? = null) {
    this.adapter = adapter
}