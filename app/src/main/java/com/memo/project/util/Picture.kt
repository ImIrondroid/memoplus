package com.memo.project.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore

fun exifOrientationToDegrees(exifOrientation: Int): Int {
    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
        return 90
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
        return 180
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
        return 270
    }
    return 0
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
    var column_index=0
    val project : Array<String> = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = activity.contentResolver.query(contentUri, project, null, null, null)
    if(cursor!!.moveToFirst()) {
        column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    }
    return cursor.getString(column_index)
}
