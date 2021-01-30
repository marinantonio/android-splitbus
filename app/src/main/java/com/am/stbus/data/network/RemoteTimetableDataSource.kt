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
import com.am.stbus.common.TimetablesData
import io.reactivex.Single
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList

class RemoteTimetableDataSource {

    fun getTimetableDetail(lineId: Int, url: String): Single<String> {
        return Single.fromCallable {

            val timetableId = Jsoup.connect(url).timeout(Constants.NETWORK_REQUEST_TIMEOUT).get()
                    .select(".c-vozni-red__search-select option:contains("
                            + TimetablesData().getTimetableTitleAsOnPrometWebsite(lineId).toUpperCase(Locale.ROOT)
                            + ")")
                    .attr("value")


            val doc = Jsoup.connect(Constants.PROMET_URL + timetableId).timeout(Constants.NETWORK_REQUEST_TIMEOUT).get()
            val workDayList  = ArrayList<String>()
            val saturdayList = ArrayList<String>()
            val sundayList = ArrayList<String>()

            doc.apply {
                select("table.c-vozni-red__table td:eq(0)").let {
                    for (e in it) {
                        workDayList.add("\n ${e.text()}")
                    }
                    // dodgy fix za zadnji red :)
                    workDayList.add("")
                }

                select("table.c-vozni-red__table td:eq(1)").let {
                    for (e in it) {
                        saturdayList.add("\n ${e.text()}")
                    }
                    saturdayList.add("")
                }

                select("table.c-vozni-red__table td:eq(2)").let {
                    for (e in it) {
                        sundayList.add("\n ${e.text()}")
                    }
                    sundayList.add("")
                }
            }


            val timetableItems = listOf(
                    doc.select("h3.c-vozni-red__line").text(),
                    doc.select("p.c-vozni-red__valid").text(),
                    workDayList,
                    saturdayList,
                    sundayList,
                    doc.select("div.c-vozni-red-note__items").text()
            )

            if (doc.select("p.c-vozni-red__valid").text().isNullOrBlank() && workDayList.isEmpty() && saturdayList.isEmpty() && sundayList.isEmpty()) {
                // Null response
                return@fromCallable ""
            } else {
                // Valid response
                return@fromCallable timetableItems.joinToString(Constants.EMA_DELIMITER)
            }
        }
    }
}