package com.memo.project.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.memo.project.data.model.MemoImage

class RoomConverter {

    @TypeConverter
    fun fromString(value: String) : List<MemoImage> {
        val type = object : TypeToken<List<MemoImage>>(){}.type
        return Gson().fromJson(value, type)
    }
    @TypeConverter
    fun fromList(list: List<MemoImage>) : String {
        return Gson().toJson(list)
    }
}