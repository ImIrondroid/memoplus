package com.memo.project.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memo.project.base.BaseNavigator
import com.memo.project.base.BaseViewModel
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.data.local.entity.MemoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MemoViewModel<N : BaseNavigator>(
    private val dataBase : MemoDatabase,
    application: Application
) : BaseViewModel<N>(application) {

    private var _pictures : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val pictures : LiveData<MutableList<String>> = _pictures

    var id : Long = 0
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    val date : String?
    get() {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val formatdate = simpleDateFormat.format(date)
        return "작성날짜 : $formatdate"
    }

    var memoEntities : LiveData<List<MemoEntity>>

    init {
        memoEntities = select()
    }

    fun add(imagePath : String) {
        _pictures.value = _pictures.value!!.toMutableList().apply {
            this.add(imagePath)
            return@apply
        }
    }

    fun initialize() {
        memoEntities = select()
        _pictures.value = _pictures.value!!.toMutableList()
    }

    fun remove(position : Int) {
        _pictures.value = _pictures.value!!.toMutableList().apply {
            removeAt(position)
            return@apply
        }
    }

    fun select() : LiveData<List<MemoEntity>> {

        return dataBase.memoDao().getAll()
    }

    fun insert() {

        if(title.value.isNullOrBlank()) title.value = "제목 없음"
        if(description.value.isNullOrBlank()) description.value = "내용 없음"

        val now = System.currentTimeMillis()
        val date = Date(now)
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val formatdate = simpleDateFormat.format(date)

        compositeDisposable.add(
            dataBase.memoDao().insert(MemoEntity(
                0L,
                title.value!!,
                description.value!!,
                formatdate,
                formatdate,
                pictures.value?.toList()!!))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("insert : ", "success")
                    getNavigator().backActivity()
                },{
                    Log.e("insert : ", it.message)
                })
        )
    }
}