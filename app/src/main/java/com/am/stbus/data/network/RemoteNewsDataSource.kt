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

package com.am.stbus.data.network

import com.am.stbus.common.Constants
import com.am.stbus.domain.models.NewsItem
import com.am.stbus.domain.models.NewsListItem
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber

class RemoteNewsDataSource {

    suspend fun getNewsList(dispatcher: CoroutineDispatcher): List<NewsListItem> {
        return withContext(dispatcher) {
            try {
                val doc = Jsoup.connect(Constants.PROMET_NOVOSTI_URL).timeout(Constants.NETWORK_REQUEST_TIMEOUT).get()
                val newsList = mutableListOf<NewsListItem>()
                var count = 0
                val elements = doc.select("h3.c-article-card__title > a")

                for (e in elements) {

                    val title = e.text()
                    val url = e.attr("href")

                    var summary = ""
                    var datum: String

                    doc.apply {
                        datum = select("div.c-article-card__date")[count].text()
                        val summaryElement = select("div.c-article-card__content")[count]
                        val summaryElementSize = summaryElement.select("div.c-article-card__summary").size
                        if (summaryElementSize > 0)
                            summary = summaryElement.child(2).text()

                    }

                    newsList.add(NewsListItem(count, title, summary, datum, url))
                    count += 1
                }

                newsList
            } catch (e: Exception) {
                Timber.e("Error fetching content")
                emptyList<NewsListItem>()
            }
        }
    }

    fun getNewsDetail(url: String): Single<NewsItem> {
        return Single.fromCallable {

            val doc = Jsoup.connect(url).timeout(Constants.NETWORK_REQUEST_TIMEOUT).get()
            var newsImgUrl: String
            val newsContentWithAttachments: String

            doc.apply {
                val newsContent = if (select("[class=c-article-detail__body c-text-body]").size > 0) {
                    select("[class=c-article-detail__body c-text-body]").html()
                } else {
                    select("[class=EDN_article_content]").html()
                }

                val ul = doc.select("[class=o-list-bare c-article-document-list]").select("li")
                val attachments = ul.map {
                    val attachmentUrl = it.select("[class=c-article-document o-media]").first()?.attr("href")
                    val attachmentTitle = it.select("[class=c-article-document__title c-text-lead]").text()
                    val attachmentProperties = it.select("[class=c-article-document__meta c-text-smallprint]").text()
                    return@map "<a href=\"$attachmentUrl\">$attachmentTitle $attachmentProperties</a>"
                }.joinToString(separator = "<br><br>")

                newsContentWithAttachments = newsContent + attachments

                newsImgUrl = "http://www.promet-split.hr${select("[class=c-gallery-fotorama__item c-gallery-fotorama__item--image js-gallery-unwrap]")
                        .attr("data-img")}"
            }

            return@fromCallable NewsItem(newsContentWithAttachments, newsImgUrl)
        }
    }

}