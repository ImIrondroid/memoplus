package com.memo.project.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.memo.project.data.local.entity.MemoEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MemoDao {

    @Query("SELECT * FROM memoinfo")
    fun getAll() : LiveData<List<MemoEntity>>

    @Query("SELECT * FROM memoinfo where id = :id")
    fun getMemo(id : Long) : Single<MemoEntity>

    @Insert
    fun insert(memo : MemoEntity) : Completable

    @Update
    fun update(memo : MemoEntity) : Completable

    @Delete
    fun delete(memo : MemoEntity) : Completable
}