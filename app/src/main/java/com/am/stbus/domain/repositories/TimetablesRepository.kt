/*
 * MIT License
 *
 * Copyright (c) 2013 - 2025 Antonio Marin
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

package com.am.stbus.domain.repositories

import com.am.stbus.common.Constants.NETWORK_REQUEST_TIMEOUT
import com.am.stbus.common.Constants.PROMET_ALL_LINES_URL
import com.am.stbus.common.Constants.PROMET_ALL_LINE_ID_URL
import com.am.stbus.data.models.roomdb.TimetableDetailDataCached
import com.am.stbus.data.models.timetables.TimetableDetailData
import com.am.stbus.data.services.room.TimetableDetailDataCachedDao
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.threeten.bp.ZonedDateTime

class TimetablesRepository(
    private val timetableDetailDataCachedDao: TimetableDetailDataCachedDao
) {
    suspend fun getLocalTimetableDataForId(websiteTitle: String): TimetableDetailDataCached? {
        return timetableDetailDataCachedDao.getTimetableByWebsiteTitle(websiteTitle)
    }

    suspend fun getRemoteTimetableDataForId(websiteTitle: String): TimetableDetailData {

        val timetablePath =
            Jsoup.connect(PROMET_ALL_LINES_URL).timeout(NETWORK_REQUEST_TIMEOUT).get()
                .select(
                    ".c-vozni-red__search-select option:contains("
                            + websiteTitle
                            + ")"
                )
                .attr("value")

        val timetableId = timetablePath.split("/").lastOrNull()?.toIntOrNull()
            ?: throw Exception("Missing timetableId $websiteTitle")

        val timetableDocument = Jsoup.connect(PROMET_ALL_LINE_ID_URL + timetableId)
            .timeout(NETWORK_REQUEST_TIMEOUT).get()

        val timetableData = generateTimetableDetailDataFromDocument(timetableDocument)

        timetableDetailDataCachedDao.insertTimetable(
            TimetableDetailDataCached(
                websiteTitle = websiteTitle,
                timetableDetailData = timetableData,
                storedAt = ZonedDateTime.now()
            )
        )

        return timetableData
    }

    private fun generateTimetableDetailDataFromDocument(timetableDocument: Document): TimetableDetailData {
        val workDayList = mutableListOf<List<String>>()
        val saturdayList = mutableListOf<List<String>>()
        val sundayList = mutableListOf<List<String>>()

        val table = timetableDocument.select("table.c-vozni-red__table")

        table.select("tbody tr").forEach { row ->
            val cells = row.select("td")

            // Column 0: Weekday
            workDayList.add(
                cells.getOrNull(0)
                    ?.select("span.c-vozni-red__box--new")?.map {
                        it.text()
                    } ?: emptyList()
            )

            // Column 1: Saturday
            saturdayList.add(
                cells.getOrNull(1)
                    ?.select("span.c-vozni-red__box--new")?.map {
                        it.text()
                    } ?: emptyList()
            )

            // Column 2: Sunday/Holiday
            sundayList.add(
                cells.getOrNull(2)
                    ?.select("span.c-vozni-red__box--new")?.map {
                        it.text()
                    } ?: emptyList()
            )
        }

        return TimetableDetailData(
            validFrom = timetableDocument.select("p.c-vozni-red__valid").text(),
            workdayItems = workDayList.toList(),
            saturdayItems = saturdayList.toList(),
            sundayList = sundayList.toList(),
            notes = timetableDocument.select("div.c-vozni-red-note__items").text()
        )
    }


    /*    fun getTimetableForWebsiteTitle(timetableId: Int): TimetableDetailData {
            // Check if there's locally stored TimetableDetailData
            val cached = runBlocking { timetableDetailDataCachedDao.getTimetableById(timetableId) }
            if (cached != null) {
                // Check if it's still valid (e.g., within 24 hours)
                if (cached.storedAt.plusHours(24).isAfter(ZonedDateTime.now())) {
                    return cached.timetableDetailData
                }
            }

            val timetableDocument = Jsoup.connect(PROMET_ALL_LINE_ID_URL + timetableId)
                .timeout(NETWORK_REQUEST_TIMEOUT).get()


            // Store to database
            runBlocking {
                timetableDetailDataCachedDao.insertTimetable(
                    TimetableDetailDataCached(
                        id = timetableId,
                        timetableDetailData = timetableDetailData,
                        storedAt = ZonedDateTime.now()
                    )
                )
            }

            return timetableDetailData

        }*/


}
