package com.am.stbus.repositories.local

import androidx.room.*
import com.am.stbus.repositories.models.News
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): Single<List<News>>

    @Query("SELECT * FROM news WHERE newsId IN (:newsIds)")
    fun loadAllByIds(newsIds: IntArray): List<News>

    @Query("SELECT * FROM news WHERE url LIKE :url LIMIT 1")
    fun findByUrl(url: String): News

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>): Completable

    @Delete
    fun delete(news: News)
}
