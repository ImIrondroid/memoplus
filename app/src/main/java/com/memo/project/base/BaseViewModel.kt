package com.memo.project.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N: BaseNavigator> : ViewModel() {

    private lateinit var navigator : WeakReference<N>
    private val compositeDisposable = CompositeDisposable()

    fun setNavigator(navigator : N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator.get()!!

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}