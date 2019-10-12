package com.am.stbus.networking.usecases

import com.am.stbus.common.Constants
import com.am.stbus.repositories.models.NewsItem
import com.am.stbus.repositories.models.NewsListItem
import com.am.stbus.repositories.remote.NewsRepository
import io.reactivex.Single
import org.jsoup.Jsoup

class GetNewsUseCase : NewsRepository {

    override fun getNewsList(): Single<List<NewsListItem>> {
        return Single.fromCallable {

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

            return@fromCallable newsList
        }

    }

    override fun getNewsDetail(url: String): Single<NewsItem> {
        return Single.fromCallable {

            val doc = Jsoup.connect(url).timeout(Constants.NETWORK_REQUEST_TIMEOUT).get()
            var newsContent: String
            var newsImgUrl: String

            doc.apply {
                newsContent = if (select("[class=c-article-detail__body c-text-body]").size > 0) {
                    select("[class=c-article-detail__body c-text-body]").html()
                } else {
                    select("[class=EDN_article_content]").html()
                }

                newsImgUrl = "http://www.promet-split.hr${select("[class=c-gallery-fotorama__item c-gallery-fotorama__item--image js-gallery-unwrap]")
                        .attr("data-img")}"
            }

            return@fromCallable NewsItem(newsContent, newsImgUrl)
        }
    }
}
