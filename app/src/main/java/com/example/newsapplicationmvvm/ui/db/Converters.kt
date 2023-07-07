package com.example.newsapplicationmvvm.ui.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromSource(source: com.example.newsapplicationmvvm.ui.models.Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): com.example.newsapplicationmvvm.ui.models.Source {
        return com.example.newsapplicationmvvm.ui.models.Source(name, name)
    }
}