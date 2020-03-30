package com.memo.project.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.memo.project.R
import com.memo.project.base.BaseActivity
import com.memo.project.base.BaseNavigator
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.databinding.ActivityMainBinding
import com.memo.project.ui.adapter.MemoAdapter
import com.memo.project.ui.viewmodel.MemoViewModel
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val database : MemoDatabase by inject()

    lateinit var memoViewModel : MemoViewModel<BaseNavigator>
    lateinit var memoAdapter: MemoAdapter
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setTitle("메모플러스")

        memoViewModel = MemoViewModel(database, this.application)
        memoAdapter = MemoAdapter()
        binding.viewModel = memoViewModel.apply {
            setNavigator(this@MainActivity)
        }

        binding.memoAdapter = memoAdapter
        memoAdapter.setOnItemSelectedListener { view, item, position ->
            startActivity(Intent(this, DetailedMemoActivity::class.java).apply {
                putExtra("id",item.id )
            })
        }
    }

    override fun onStart() {
        super.onStart()
        memoViewModel.initialize()
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
