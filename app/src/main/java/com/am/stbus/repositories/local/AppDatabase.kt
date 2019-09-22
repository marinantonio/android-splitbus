package com.am.stbus.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.am.stbus.repositories.models.NewsListItem

@Database(entities = arrayOf(NewsListItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
