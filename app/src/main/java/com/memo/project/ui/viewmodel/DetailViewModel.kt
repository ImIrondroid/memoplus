package com.memo.project.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memo.project.base.BaseNavigator
import com.memo.project.base.BaseViewModel
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.data.local.entity.MemoEntity
import com.memo.project.util.extenstion.createMutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DetailViewModel(
    private val dataBase: MemoDatabase
) : BaseViewModel<BaseNavigator>() {

    private val _memoEntity: MutableLiveData<MemoEntity> by createMutableLiveData()
    var memoEntity: LiveData<MemoEntity> = _memoEntity

    private val _pictures: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    var pictures: LiveData<MutableList<String>> = _pictures

    var title = MutableLiveData<String>("")
    var description = MutableLiveData<String>("")
    val date : String?
        get() {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
            val formatdate = simpleDateFormat.format(date)
            return "작성날짜 : $formatdate"
        }

    fun getMemo(id: Long) {

        dataBase.memoDao().getMemo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _memoEntity.value = it
                title.value = it.title
                description.value = it.descrption
            }
            .subscribe({
                _pictures.value = it.imagePath.toMutableList()
            }, {
                it.printStackTrace()
            })
            .let{ addDisposable(it) }
    }

    fun add(imagePath : String) {

        _pictures.value = _pictures.value!!.toMutableList().apply {
            this.add(imagePath)
            return@apply
        }
    }

    fun remove(position: Int) {
        _pictures.value = _pictures.value!!.toMutableList().apply {
            removeAt(position)
            return@apply
        }
    }

    fun update() {

        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val formatdate = simpleDateFormat.format(date)

        if(title.value.isNullOrBlank()) title.value = "제목 없음"
        if(description.value.isNullOrBlank()) description.value = "내용 없음"

        val updatedMemo = MemoEntity(
            _memoEntity.value!!.id,
            title.value!!,
            description.value!!,
            _memoEntity.value!!.createdAt,
            formatdate,
            _pictures.value?.toList()!!
        )

        dataBase.memoDao().update(updatedMemo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("update : ", "success")
            }, {
                Log.e("update : ", it.message)
            })
            .let { addDisposable(it) }
    }

    fun delete() {

        if(title.value.isNullOrBlank()) title.value = "제목 없음"
        if(description.value.isNullOrBlank()) description.value = "내용 없음"

        val updatedMemo = MemoEntity(
            _memoEntity.value?.id!!,
            title.value ?: "제목 없음",
            description.value ?: "내용 없음",
            _memoEntity.value!!.createdAt,
            _memoEntity.value!!.editedAt,
            _pictures.value?.toList()!!
        )

        dataBase.memoDao().delete(updatedMemo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("delete : ", "success")
                getNavigator().backActivity()
            }, {
                Log.e("delete : ", it.message)
            })
            .let { addDisposable(it) }
    }
}