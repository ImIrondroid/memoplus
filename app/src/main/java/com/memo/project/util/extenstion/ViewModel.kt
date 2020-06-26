package com.memo.project.util.extenstion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

inline fun <reified T> ViewModel.lazyMutableLiveData(
    defaultValue: T? = null
): Lazy<MutableLiveData<T>> = lazy{ MutableLiveData<T>(defaultValue) }