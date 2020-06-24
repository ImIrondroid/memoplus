package com.memo.project.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore

fun exifOrientationToDegrees(exifOrientation: Int): Int {
    return when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> {
            90
        }
        ExifInterface.ORIENTATION_ROTATE_180 -> {
            180
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> {
            270
        }
        else -> 0
    }
}

fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree)
    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        matrix,
        true
    )
}

@Suppress("DEPRECATION")
fun getRealPathFromUri(activity : Activity, contentUri : Uri) : String {
    var result: String
    val cursor = activity.contentResolver.query(contentUri, null, null, null, null)
    if(cursor==null) { result = contentUri.path!! } 
    else {
        cursor.moveToFirst()
        val index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(index)
        cursor.close()
    }
    return result
}
