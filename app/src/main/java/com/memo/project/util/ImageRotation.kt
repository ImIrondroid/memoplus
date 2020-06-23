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

fun rotate(bitmap: Bitmap, degree: Float): Bitmap? {
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

fun getRealPathFromUri(activity : Activity, contentUri : Uri) : String {
    var index = 0
    val project : Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = activity.contentResolver.query(contentUri, project, null, null, null)
    if(cursor!!.moveToFirst()) {
        index  = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    }
    return cursor.getString(index)
}
