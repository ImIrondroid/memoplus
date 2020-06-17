package com.memo.project.ui.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.FileProvider
import com.memo.project.BR
import com.memo.project.R
import com.memo.project.base.BaseActivity
import com.memo.project.databinding.ActivityEditMemoBinding
import com.memo.project.ui.adapter.PictureAdapter
import com.memo.project.ui.view.Fragment.PopupDialogFragment
import com.memo.project.ui.viewmodel.DetailViewModel
import com.memo.project.util.Permission
import com.memo.project.util.getRealPathFromUri
import kotlinx.android.synthetic.main.activity_add_memo.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditMemoActivity : BaseActivity<ActivityEditMemoBinding, DetailViewModel>() {

    private val mViewModel : DetailViewModel by viewModel()
    private val pictureAdapter : PictureAdapter by inject()
    private var imageFilePath: String? = null
    private var photoUri: Uri? = null

    override val screenTitle: String
        get() = "메모 편집하기"

    override val viewModel: DetailViewModel
        get() = mViewModel

    override val viewModelId: Int
        get() = BR.viewModel

    override val layoutResId: Int
        get() = R.layout.activity_edit_memo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        viewDataBinding.rcvImages.adapter = pictureAdapter
        pictureAdapter.setOnItemSelectedListener { view, item, position ->
            viewModel.remove(position)
        }

        val id = getIntent().getLongExtra("id", 0)
        viewModel.getMemo(id)

        Permission(this).tedPermission()
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this@EditMemoActivity).apply {
            setTitle("메모편집을 취소하시겠습니까?")
            setPositiveButton("취소") { dialog, which ->
                dialog.dismiss()
            }
            setNegativeButton("확인") { dialog, which ->
                super.onBackPressed()
                dialog.dismiss()
            }
        }.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.memo_apply, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_apply -> {
                viewModel.update()
                val dialog = AlertDialog.Builder(this@EditMemoActivity).apply {
                     setTitle("편집이 완료되었습니다")
                     setCancelable(false)
                     setPositiveButton("확인") { dialog, which ->
                        dialog.dismiss()
                        finish()
                     }
                }.show()
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == PICK_FROM_ALBUM) {
                //선택된 여러개 이미지 넣기
                val clipData : ClipData = data?.clipData!!
                var i = 0
                while(i<clipData.itemCount) {
                    val uri : Uri = clipData.getItemAt(i).uri
                    getPictureFromAlbum(uri)
                    i++
                }
            } else if(requestCode == PICK_FROM_CAMERA) {
                //카메라 이미지 넣기
                if(imageFilePath!=null) viewModel.add(imageFilePath!!)
            }
        }
    }

    fun takeAlbum(view : View) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = MediaStore.Images.Media.CONTENT_TYPE
        }
        startActivityForResult(Intent.createChooser(intent,"select"),
            PICK_FROM_ALBUM
        )
    }

    fun takePhoto(view : View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) { // Error occurred while creating the File
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, packageName, photoFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent,
                    PICK_FROM_CAMERA
                )
            }
        }
    }

    fun getImagewithUrl(view : View) {

        //https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory&fname=https://k.kakaocdn.net/dn/EShJF/btquPLT192D/SRxSvXqcWjHRTju3kHcOQK/img.png
        PopupDialogFragment(this).apply {
            setOnImageSelectedListener { view, imagePath ->
                viewModel.add(imagePath)
            }
        }.show()
    }

    private fun getPictureFromAlbum(uri : Uri) {

        val imagePath : String = getRealPathFromUri(this, uri)
        viewModel.add(imagePath)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "LINE_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    fun statusChanged(view : View) {

        view.visibility = View.INVISIBLE
        button_group.visibility = View.VISIBLE
    }

    companion object {

        const val PICK_FROM_ALBUM = 1;
        const val PICK_FROM_CAMERA = 2;
    }
}
