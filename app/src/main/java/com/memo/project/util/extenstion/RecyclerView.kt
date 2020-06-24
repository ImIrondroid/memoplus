package com.memo.project.util.extenstion

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.memo.project.base.BaseRecyclerViewAdapter
import com.memo.project.util.ItemDecoration

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

@BindingAdapter(value = ["lineSpacing","itemSpacing"], requireAll = false)
fun RecyclerView.binding(lineSpacing : Float = 0f, itemSpacing : Float = 0f) {
    if(this.itemDecorationCount == 0) {
        this.addItemDecoration(ItemDecoration(lineSpacing, itemSpacing))
    }
}