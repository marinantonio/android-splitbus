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
