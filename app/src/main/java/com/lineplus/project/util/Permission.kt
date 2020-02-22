package com.lineplus.project.util

import android.Manifest
import android.app.Activity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.lineplus.project.R

class Permission(
    val activity : Activity
) {
    fun tedPermission() {
        TedPermission.with(activity.applicationContext)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {}
                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {}
            })
            .setRationaleMessage(activity.resources.getString(R.string.permission_2))
            .setDeniedMessage(activity.resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()
    }
}