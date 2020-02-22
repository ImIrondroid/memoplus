package com.lineplus.project.data.local.db

import android.content.Context
import androidx.room.*
import com.lineplus.project.data.local.RoomConverter
import com.lineplus.project.data.local.dao.MemoDao
import com.lineplus.project.data.local.entity.ImagePathEntity
import com.lineplus.project.data.local.entity.MemoEntity

@Database(entities = arrayOf(MemoEntity::class, ImagePathEntity::class), version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

    companion object {
        private var INSTANCE: MemoDatabase? = null

        fun getInstance(context: Context): MemoDatabase? {
            if (INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MemoDatabase::class.java,
                        "your_db.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}