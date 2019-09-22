package com.am.stbus.repositories.remote

import com.am.stbus.repositories.models.NewsItem
import com.am.stbus.repositories.models.NewsListItem
import io.reactivex.Single

interface NewsRepository {

    fun getNewsList(): Single<List<NewsListItem>>
    fun getNewsDetail(url: String): Single<NewsItem>
}