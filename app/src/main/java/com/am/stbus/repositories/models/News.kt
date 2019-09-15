package com.am.stbus.repositories.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
        @PrimaryKey val newsId: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "desc") val desc: String,
        @ColumnInfo(name = "date") val date: String,
        @ColumnInfo(name = "url") val url: String)