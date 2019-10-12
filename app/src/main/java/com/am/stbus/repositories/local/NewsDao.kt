/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
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

package com.am.stbus.repositories.local

import androidx.room.*
import com.am.stbus.repositories.models.NewsListItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsListItem")
    fun getAll(): Single<List<NewsListItem>>

    @Query("SELECT * FROM newsListItem WHERE newsId IN (:newsIds)")
    fun loadAllByIds(newsIds: IntArray): List<NewsListItem>

    @Query("SELECT * FROM newsListItem WHERE url LIKE :url LIMIT 1")
    fun findByUrl(url: String): NewsListItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<NewsListItem>): Completable

    @Delete
    fun delete(news: NewsListItem)
}
