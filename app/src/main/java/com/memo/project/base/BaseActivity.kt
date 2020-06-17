package com.memo.project.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.memo.project.BR
import kotlin.reflect.KClass

abstract class BaseActivity<VDB : ViewDataBinding, VM: BaseViewModel<*>>
    : AppCompatActivity(), BaseNavigator {

    protected abstract val screenTitle: String
    protected abstract val layoutResId: Int
    protected open val viewModelId: Int = BR.viewModel

    protected abstract val viewModel: VM
    protected lateinit var viewDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = screenTitle
        bindView()
    }

    private fun bindView() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResId)
        viewDataBinding.apply {
            setVariable(viewModelId, viewModel)
            lifecycleOwner = this@BaseActivity
            executePendingBindings()
        }
    }

    override fun backActivity() {
        onBackPressed()
    }

    override fun <T : Activity> nextActivity(kClass: KClass<T>, bundle: Bundle?, clearTask: Boolean) {
        startActivity(Intent(this, kClass.java).apply {
            bundle?.let(this::putExtras)
            if(clearTask) {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            }
        })
    }
}