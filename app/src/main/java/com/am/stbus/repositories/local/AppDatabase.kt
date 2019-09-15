package com.am.stbus.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.am.stbus.repositories.models.News

@Database(entities = arrayOf(News::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
