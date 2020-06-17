package com.memo.project.ui.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import com.memo.project.BR
import com.memo.project.R
import com.memo.project.base.BaseActivity
import com.memo.project.databinding.ActivityDetailedMemoBinding
import com.memo.project.ui.adapter.PictureAdapter
import com.memo.project.ui.viewmodel.DetailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedMemoActivity : BaseActivity<ActivityDetailedMemoBinding, DetailViewModel>() {

    private val mViewModel : DetailViewModel by viewModel()
    private val pictureAdapter = PictureAdapter(false)

    var sendId : Long = 0
    val displayMetrics = DisplayMetrics()

    override val screenTitle: String
        get() = "메모 상세보기"

    override val viewModel: DetailViewModel
        get() = mViewModel

    override val viewModelId: Int
        get() = BR.viewModel

    override val layoutResId: Int
        get() = R.layout.activity_detailed_memo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        viewDataBinding.rcvImages.adapter = pictureAdapter

        sendId = intent.getLongExtra("id", 0)
        viewModel.getMemo(sendId)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMemo(sendId)
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
                        viewModel.delete()
                        dialog.dismiss()
                    }
                }.show()
            }
        }
        return true
    }
}

