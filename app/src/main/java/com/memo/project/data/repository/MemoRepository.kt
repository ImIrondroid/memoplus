package com.memo.project.data.repository

import androidx.lifecycle.LiveData
import com.memo.project.data.local.entity.Memo
import io.reactivex.Completable
import io.reactivex.Single

interface MemoRepository {

    fun selectAll(): LiveData<List<Memo>>
    fun select(id: Long): Single<Memo>
    fun insert(memo: Memo): Completable
    fun update(memo: Memo): Completable
    fun delete(memo: Memo): Completable
}