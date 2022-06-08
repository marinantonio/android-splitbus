/*
 * MIT License
 *
 * Copyright (c) 2013 - 2021 Antonio Marin
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

package com.am.stbus.data.repositoriesImpl

import com.am.stbus.data.local.NewsDao
import com.am.stbus.data.network.RemoteNewsDataSource
import com.am.stbus.domain.models.NewsItem
import com.am.stbus.domain.models.NewsListItem
import com.am.stbus.domain.repositories.NewsRepository
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher

class NewsRepositoryImpl(
        private val remoteNewsDataSource: RemoteNewsDataSource,
        private val localNewsDataSource: NewsDao
): NewsRepository {
    override suspend fun getNewsList(remote: Boolean, dispatcher: CoroutineDispatcher): List<NewsListItem> {
        return when(remote) {
            true -> remoteNewsDataSource.getNewsList(dispatcher)
            false -> localNewsDataSource.getAll()
        }
    }

    override suspend fun saveNewsList(list: List<NewsListItem>) {
        localNewsDataSource.insertAll(list)
    }

    override suspend fun deleteNewsList() {
        localNewsDataSource.nukeTable()
    }

    override fun getNewsDetail(remote: Boolean, url: String): Single<NewsItem> {
        return when (remote) {
            true -> remoteNewsDataSource.getNewsDetail(url)
            false -> throw IllegalArgumentException("Shouldn't be here!")
        }

    }
}