package com.am.stbus.common.di

import androidx.room.Room
import com.am.stbus.repositories.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val applicationModule = module {

    single {
        Room.databaseBuilder(
                    androidApplication(),
                    AppDatabase::class.java,
                    "split-bus-db"
            ).build()
        }

    single {
        get<AppDatabase>().newsDao()
    }
}