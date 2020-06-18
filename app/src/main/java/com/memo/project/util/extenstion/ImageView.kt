package com.memo.project.util.extenstion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.memo.project.R
import com.memo.project.data.model.MemoImage
import com.memo.project.util.exifOrientationToDegrees
import com.memo.project.util.rotate
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.io.IOException

@BindingAdapter("loadImage")
fun ImageView.load(image : MemoImage?) {

    val imageUrl = image?.imagePath
    val view = this
    val context = this.context

    when {
        imageUrl.isNullOrBlank() -> {
            context.runOnUiThread {
                Glide.with(this)
                    .load(R.drawable.noimage_bg)
                    .thumbnail(0.5f)
                    .into(view)
            }
            return
        }
        imageUrl.startsWith("https://") or imageUrl.startsWith("http://") -> {
            context.runOnUiThread {
                Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.page_not_found_bg)
                    .thumbnail(0.5f)
                    .into(view)
            }
        }
        !imageUrl.isNullOrBlank() -> {
            doAsync {
                var exif: ExifInterface? = null
                try {
                    exif = ExifInterface(imageUrl)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val exifOrientation: Int =
                    exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)!!
                val exifDegree: Int = exifOrientationToDegrees(exifOrientation)
                val bitmap: Bitmap = BitmapFactory.decodeFile(imageUrl)
                val rotateBitmap = rotate(bitmap, exifDegree.toFloat())
                context.runOnUiThread {
                    Glide.with(this)
                        .load(rotateBitmap!!)
                        .thumbnail(0.5f)
                        .into(view)
                }
            }
        }
    }
}