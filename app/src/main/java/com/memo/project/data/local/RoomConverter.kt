package com.memo.project.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {
    @TypeConverter
    fun fromString(value :String) : List<String> {
        val listType = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list : List<String>) : String{
        val listType = object: TypeToken<List<String>>() {}.type
        return Gson().toJson(list, listType)
    }
}