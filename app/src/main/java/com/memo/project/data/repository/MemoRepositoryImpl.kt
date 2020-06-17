package com.memo.project.data.repository

import androidx.lifecycle.LiveData
import com.memo.project.data.local.db.MemoDatabase
import com.memo.project.data.local.entity.Memo
import com.memo.project.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

class MemoRepositoryImpl (
    private val database: MemoDatabase,
    private val scheduler: SchedulerProvider
): MemoRepository {

    override fun selectAll(): LiveData<List<Memo>> {
        return database.memoDao().getAll()
    }

    override fun select(id: Long): Single<Memo> {
        return database.memoDao().getMemo(id)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
    }

    override fun insert(memo: Memo): Completable {
        return database.memoDao().insert(memo)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
    }

    override fun update(memo: Memo): Completable {
        return database.memoDao().update(memo)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
    }

    override fun delete(memo: Memo): Completable {
        return database.memoDao().delete(memo)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.mainThread())
    }
}