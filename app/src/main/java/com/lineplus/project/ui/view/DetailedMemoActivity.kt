package com.lineplus.project.ui.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import com.lineplus.project.R
import com.lineplus.project.base.BaseActivity
import com.lineplus.project.base.BaseNavigator
import com.lineplus.project.data.local.db.MemoDatabase
import com.lineplus.project.data.local.entity.MemoEntity
import com.lineplus.project.databinding.ActivityDetailedMemoBinding
import com.lineplus.project.ui.adapter.PictureAdapter
import com.lineplus.project.ui.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.actionbar.*
import org.koin.android.ext.android.inject

class DetailedMemoActivity : BaseActivity() {

    private val database : MemoDatabase by inject()

    lateinit var detailViewModel: DetailViewModel<BaseNavigator>
    lateinit var binding : ActivityDetailedMemoBinding
    lateinit var pictureAdapter : PictureAdapter

    var sendId : Long = 0
    val displayMetrics = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_memo)
        binding.lifecycleOwner = this

        setTitle("메모 상세보기")

        detailViewModel = DetailViewModel(database, this.application)
        binding.viewModel = detailViewModel.apply {
            setNavigator(this@DetailedMemoActivity)
        }

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        pictureAdapter = PictureAdapter(false, displayMetrics.widthPixels, displayMetrics.heightPixels)
        binding.rcvImages.adapter = pictureAdapter

        sendId = getIntent().getLongExtra("id", 0)
        detailViewModel.getMemo(sendId)
    }

    override fun onStart() {
        super.onStart()
        detailViewModel.getMemo(sendId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.memo_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_edit -> {
                startActivity(Intent(this, EditMemoActivity::class.java).apply {
                    putExtra("id", sendId)
                })
            }
            R.id.menu_delete -> {
                val dialog = AlertDialog.Builder(this@DetailedMemoActivity).apply {
                    setTitle("정말로 삭제하시겠습니까?")
                    setPositiveButton("취소") { dialog, which ->
                        dialog.dismiss()
                    }
                    setNegativeButton("확인") { dialog, which ->
                        detailViewModel.delete()
                        dialog.dismiss()
                    }
                }.show()
            }
        }
        return true
    }

}

