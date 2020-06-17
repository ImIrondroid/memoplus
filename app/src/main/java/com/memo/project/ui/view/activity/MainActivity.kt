package com.memo.project.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.memo.project.BR
import com.memo.project.R
import com.memo.project.base.BaseActivity
import com.memo.project.databinding.ActivityMainBinding
import com.memo.project.ui.adapter.MemoAdapter
import com.memo.project.ui.viewmodel.MemoViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MemoViewModel>() {

    private val mViewModel : MemoViewModel by viewModel()
    private val memoAdapter: MemoAdapter by inject()

    override val screenTitle: String
        get() = "메모플러스"

    override val viewModel: MemoViewModel
        get() = mViewModel

    override val viewModelId: Int
        get() = BR.viewModel

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewDataBinding.memoAdapter = memoAdapter
        memoAdapter.setOnItemSelectedListener { view, item, position ->
            startActivity(Intent(this, DetailedMemoActivity::class.java).apply {
                putExtra("id",item.id )
            })
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.memo_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add -> {
                startActivity(Intent(this, AddMemoActivity::class.java))
            }
        }
        return true
    }
}
