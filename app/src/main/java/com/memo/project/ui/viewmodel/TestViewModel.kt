package com.memo.project.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.memo.project.base.BaseNavigator
import com.memo.project.base.BaseViewModel
import com.memo.project.data.local.entity.Memo
import com.memo.project.data.repository.MemoRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Single

class TestViewModel (
    private val memoRepo: MemoRepositoryImpl
) : BaseViewModel<BaseNavigator>() {

    private var _pictures : MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    val pictures : LiveData<List<String>> = _pictures
    val memoList : LiveData<List<Memo>>

    init {
        memoList = selectAll()
    }

    fun selectAll(): LiveData<List<Memo>> {
        return memoRepo.selectAll()
    }

    fun select(id: Long): Single<Memo> {
        return memoRepo.select(id)
    }

    fun insert(memo: Memo) {
        addDisposable(
            memoRepo.insert(memo)
                .subscribe({getNavigator().backActivity()},{it.printStackTrace()})
        )
    }

    fun update(memo: Memo): Completable {
        return memoRepo.update(memo)
    }

    fun delete(memo: Memo): Completable {
        return memoRepo.delete(memo)
    }
}