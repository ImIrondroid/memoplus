package com.lineplus.project.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lineplus.project.base.BaseNavigator
import com.lineplus.project.base.BaseViewModel
import com.lineplus.project.data.local.db.MemoDatabase
import com.lineplus.project.data.local.entity.MemoEntity
import com.lineplus.project.util.extenstion.createMutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DetailViewModel<N : BaseNavigator>(
    private val dataBase: MemoDatabase,
    application: Application
) : BaseViewModel<N>(application) {

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

        compositeDisposable.add(
            dataBase.memoDao().getMemo(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    _memoEntity.value = it
                    title.value = it.title
                    description.value = it.descrption
                }
                .subscribe({
                    _pictures.value = it.imagePath.toMutableList()
                    Log.e("getMemo : ", "success")
                }, {
                    Log.e("getMemo : ", it.message)
                })
        )
    }

    fun add(imagePath : String) {

        _pictures.value = _pictures.value!!.toMutableList().apply {
            this.add(imagePath)
            return@apply
        }
    }

    fun remove(position: Int) {
        _pictures.value = _pictures.value!!.toMutableList()?.apply {
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

        compositeDisposable.add(
            dataBase.memoDao().update(updatedMemo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.e("update : ", "success")
                }, {
                    Log.e("update : ", it.message)
                })
        )
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

        compositeDisposable.add(
            dataBase.memoDao().delete(updatedMemo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.e("delete : ", "success")
                    getNavigator().backActivity()
                }, {
                    Log.e("delete : ", it.message)
                })
        )
    }
}