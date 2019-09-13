package com.am.stbus.networking.usecases

import com.am.stbus.networking.models.News
import com.am.stbus.networking.repositories.NewsRepository
import com.am.stbus.staro.helpers.PROMET_NOVOSTI_URL
import io.reactivex.Observable
import org.jsoup.Jsoup
import java.io.IOException

class GetNewsListUseCase : NewsRepository {
    override fun getArticles(): Observable<List<News>> {
        return Observable.create { observableEmitter ->
            val newsList = mutableListOf<News>()
            try {
                val doc = Jsoup.connect(PROMET_NOVOSTI_URL).get()
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

                    newsList.add(News(title, summary, datum, url))
                    count += 1
                }
                observableEmitter.onNext(newsList)
            } catch (e: IOException) {
                observableEmitter.onError(e)
            } finally {
                observableEmitter.onComplete()
            }
        }
    }
}
