package com.memo.project.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.memo.project.data.local.entity.Memo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo")
    fun getAll() : LiveData<List<Memo>>

    @Query("SELECT * FROM memo where id = :id")
    fun getMemo(id : Long) : Single<Memo>

    @Insert
    fun insert(memo : Memo) : Completable

    @Update
    fun update(memo : Memo) : Completable

    @Delete
    fun delete(memo : Memo) : Completable
}