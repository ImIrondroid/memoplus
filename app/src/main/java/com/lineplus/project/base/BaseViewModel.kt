package com.lineplus.project.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N: BaseNavigator>(
    application: Application
) : AndroidViewModel(application) {

    private lateinit var navigator : WeakReference<N>
    open val compositeDisposable = CompositeDisposable()

    fun setNavigator(navigator : N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() = navigator.get()!!

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}