package com.memo.project.data.local.entity

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.memo.project.data.model.MemoImage
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "memo")
@Parcelize
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,
    var title : String,
    var descrption : String,
    val createdAt : String,
    var editedAt : String,
    var imageList : List<MemoImage>
) : BaseObservable(), Parcelable
