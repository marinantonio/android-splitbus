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

package com.am.stbus.domain.usecases.news

import com.am.stbus.domain.models.NewsListItem
import com.am.stbus.domain.repositories.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher

class NewsListUseCase(private val newsRepository: NewsRepository) {

    suspend fun getLocalNewsList(
        dispatcher: CoroutineDispatcher
    ): List<NewsListItem> {
        return newsRepository.getNewsList(false, dispatcher)
    }

    suspend fun getRemoteNewsList(
        dispatcher: CoroutineDispatcher
    ): List<NewsListItem> {
        newsRepository.deleteNewsList()
        val list = newsRepository.getNewsList(true, dispatcher)
        newsRepository.saveNewsList(list)
        return list
    }

    suspend fun deleteNewsList() {
        newsRepository.deleteNewsList()
    }

    suspend fun save(list: List<NewsListItem>) {
        newsRepository.saveNewsList(list)
    }

}