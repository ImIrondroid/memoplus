package com.lineplus.project.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.lineplus.project.BR
import com.lineplus.project.R
import com.lineplus.project.base.BaseActivity
import com.lineplus.project.base.BaseNavigator
import com.lineplus.project.data.local.db.MemoDatabase
import com.lineplus.project.databinding.ActivityMainBinding
import com.lineplus.project.ui.adapter.MemoAdapter
import com.lineplus.project.ui.view.AddMemoActivity
import com.lineplus.project.ui.view.DetailedMemoActivity
import com.lineplus.project.ui.viewmodel.MemoViewModel
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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

        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val formatdate = simpleDateFormat.format(date)

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
