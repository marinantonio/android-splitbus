package com.am.stbus.repositories.remote

import com.am.stbus.repositories.models.News
import io.reactivex.Observable

interface NewsRepository {

    fun getArticles(): Observable<List<News>>
}