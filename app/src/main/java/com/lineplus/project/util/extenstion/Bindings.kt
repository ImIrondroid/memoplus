package com.lineplus.project.util.extenstion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lineplus.project.R
import com.lineplus.project.base.BaseRecyclerViewAdapter
import com.lineplus.project.util.exifOrientationToDegrees
import com.lineplus.project.util.rotate
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.io.IOException

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun<T> RecyclerView.binding(list: List<T>? = null){
    (adapter as? BaseRecyclerViewAdapter<T>)?.run {
        submitList(list)
    }
}

@BindingAdapter("adapter")
fun RecyclerView.binding(adapter: RecyclerView.Adapter<*>? = null) {
    this.adapter = adapter
}

@BindingAdapter("loadImage")
fun AppCompatImageView.load(imageUrl : String?) {

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
