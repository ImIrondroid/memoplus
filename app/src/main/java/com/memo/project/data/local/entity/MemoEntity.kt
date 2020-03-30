package com.memo.project.data.local.entity

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "memoinfo")
data class MemoEntity(
    //아이디 값 자동 증가시키며 생성
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var title : String,
    var descrption : String,
    val createdAt : String,
    var editedAt : String,
    var imagePath : List<String>
) : BaseObservable(), Serializable

@Entity(tableName = "memoImagePath")
data class ImagePathEntity(
    @PrimaryKey
    val id : Long,
    var imagePath : String
)