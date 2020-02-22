package com.lineplus.project.ui.view

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.lineplus.project.R
import com.lineplus.project.base.BaseActivity
import com.lineplus.project.base.BaseNavigator
import com.lineplus.project.data.local.db.MemoDatabase
import com.lineplus.project.data.local.entity.MemoEntity
import com.lineplus.project.databinding.ActivityDetailedMemoBinding
import com.lineplus.project.databinding.ActivityEditMemoBinding
import com.lineplus.project.ui.adapter.PictureAdapter
import com.lineplus.project.ui.view.Fragment.PopupDialogFragment
import com.lineplus.project.ui.viewmodel.DetailViewModel
import com.lineplus.project.util.Permission
import com.lineplus.project.util.getRealPathFromUri
import kotlinx.android.synthetic.main.activity_add_memo.*
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class EditMemoActivity : BaseActivity() {

    private val database : MemoDatabase by inject()
    private val pictureAdapter = PictureAdapter(true)

    private var imageFilePath: String? = null
    private var photoUri: Uri? = null

    lateinit var binding : ActivityEditMemoBinding
    lateinit var detailViewModel: DetailViewModel<BaseNavigator>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_memo)
        binding.lifecycleOwner = this

        setTitle("메모 편집하기")

        detailViewModel = DetailViewModel(database, this.application)
        binding.viewModel = detailViewModel.apply {
            setNavigator(this@EditMemoActivity)
        }

        binding.rcvImages.adapter = pictureAdapter
        pictureAdapter.setOnItemSelectedListener { view, item, position ->
            detailViewModel.remove(position)
        }

        val id = getIntent().getLongExtra("id", 0)
        detailViewModel.getMemo(id)

        Permission(this).tedPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.memo_apply, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_apply -> {
                detailViewModel.update()
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
                if(imageFilePath!=null) detailViewModel.add(imageFilePath!!)
            }
        }
    }

    fun takeAlbum(view : View) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = MediaStore.Images.Media.CONTENT_TYPE
        }
        startActivityForResult(Intent.createChooser(intent,"select"), PICK_FROM_ALBUM)
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
                startActivityForResult(intent, PICK_FROM_CAMERA)
            }
        }
    }

    fun getImagewithUrl(view : View) {

        //https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory&fname=https://k.kakaocdn.net/dn/EShJF/btquPLT192D/SRxSvXqcWjHRTju3kHcOQK/img.png
        PopupDialogFragment(this).apply {
            setOnImageSelectedListener { view, imagePath ->
                detailViewModel.add(imagePath)
            }
        }.show()
    }

    private fun getPictureFromAlbum(uri : Uri) {

        val imagePath : String = getRealPathFromUri(this, uri)
        detailViewModel.add(imagePath)
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
