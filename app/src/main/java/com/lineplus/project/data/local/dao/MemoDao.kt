package com.lineplus.project.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lineplus.project.data.local.entity.MemoEntity
import io.reactivex.Single

@Dao
interface MemoDao {

    @Query("SELECT * FROM memoinfo")
    fun getAll() : LiveData<List<MemoEntity>>

    @Query("SELECT * FROM memoinfo where id = :id")
    fun getMemo(id : Long) : Single<MemoEntity>

    @Insert
    suspend fun insert(memo : MemoEntity)

    @Update
    suspend fun update(memo : MemoEntity)

    @Delete
    suspend fun delete(memo : MemoEntity)
}