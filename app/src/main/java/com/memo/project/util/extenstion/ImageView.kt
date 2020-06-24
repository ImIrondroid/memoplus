package com.memo.project.util.extenstion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.memo.project.R
import com.memo.project.data.model.MemoImage
import com.memo.project.util.exifOrientationToDegrees
import com.memo.project.util.rotate
import java.io.IOException

@BindingAdapter("loadImage")
fun ImageView.load(image : MemoImage?) {

    val imageUrl = image?.imagePath

    when {
        imageUrl.isNullOrBlank() -> {
            Glide.with(this)
                .load(R.drawable.noimage_bg)
                .thumbnail(0.5f)
                .into(this)
        }
        imageUrl.startsWith("https://") or imageUrl.startsWith("http://") -> {
            Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.page_not_found_bg)
                .thumbnail(0.5f)
                .into(this)
        }
        !imageUrl.isNullOrBlank() -> {
            try {
                val exif = ExifInterface(imageUrl)
                val exifOrientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                when(val exifDegree: Int = exifOrientationToDegrees(exifOrientation)) {
                    0 -> {
                        Glide.with(this)
                            .load(imageUrl)
                            .thumbnail(0.5f)
                            .into(this)
                    }
                    else -> {
                        val bitmap: Bitmap = BitmapFactory.decodeFile(imageUrl)
                        val rotateBitmap = rotate(bitmap, exifDegree.toFloat())
                        Glide.with(this)
                            .load(rotateBitmap)
                            .thumbnail(0.5f)
                            .into(this)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("imageView extension", e.message)
            }
        }
    }
}