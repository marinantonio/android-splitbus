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

package com.am.stbus.domain.usecases

import com.am.stbus.domain.repositories.TimetablesRepository

class GetTimetableDetailDataUseCase(
    private val timetablesRepository: TimetablesRepository
) {
    fun run(websiteTitle: String): Result<TimetableDetailData> {
        return try {

            val timetableId = timetablesRepository.getTimetableId(websiteTitle)

            if (timetableId == null) {
                return Result.failure(Exception("Missing timetableId"))
            }

            val doc = timetablesRepository.getTimetableForId(timetableId)

            val workDayList = mutableListOf<List<String>>()
            val saturdayList = mutableListOf<List<String>>()
            val sundayList = mutableListOf<List<String>>()

            val table = doc.select("table.c-vozni-red__table")

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

            Result.success(
                TimetableDetailData(
                    validFrom = doc.select("p.c-vozni-red__valid").text(),
                    workdayItems = workDayList.toList(),
                    saturdayItems = saturdayList.toList(),
                    sundayList = sundayList.toList(),
                    notes = doc.select("div.c-vozni-red-note__items").text()
                )
            )
        } catch (exp: Exception) {
            Result.failure(exp)
        }
    }

    data class TimetableDetailData(
        val validFrom: String,
        val workdayItems: List<List<String>>,
        val saturdayItems: List<List<String>>,
        val sundayList: List<List<String>>,
        val notes: String
    )
}