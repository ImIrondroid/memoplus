package com.memo.project.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemoImage(
    val imagePath: String
): Parcelable