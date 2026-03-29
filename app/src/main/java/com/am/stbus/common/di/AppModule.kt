/*
 * MIT License
 *
 * Copyright (c) 2013 - 2025 Antonio Marin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.am.stbus.common.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.am.stbus.common.room.AppDatabase
import com.am.stbus.data.services.ApiService
import com.am.stbus.data.services.room.FavouriteItemDao
import com.am.stbus.data.services.room.TimetableDetailDataCachedDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `TimetableDetailDataCached` (" +
                        "`websiteTitle` TEXT NOT NULL, " +
                        "`timetableDetailData` TEXT NOT NULL, " +
                        "`storedAt` TEXT NOT NULL, " +
                        "PRIMARY KEY(`websiteTitle`))"
            )
        }
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "split-bus-db"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single<FavouriteItemDao> {
        get<AppDatabase>().favouriteItemDao()
    }

    single<TimetableDetailDataCachedDao> {
        get<AppDatabase>().timetableDetailDataCachedDao()
    }


}
