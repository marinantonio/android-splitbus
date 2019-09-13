package com.am.stbus.networking.repositories

import com.am.stbus.networking.models.News
import io.reactivex.Observable

interface NewsRepository {

    fun getArticles(): Observable<List<News>>

}