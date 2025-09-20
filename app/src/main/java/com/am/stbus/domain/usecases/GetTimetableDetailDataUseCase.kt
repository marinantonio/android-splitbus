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

import com.am.stbus.common.Constants
import com.am.stbus.domain.TimetablesRepository

class GetTimetableDetailDataUseCase(
    private val timetablesRepository: TimetablesRepository
) {
    suspend fun run(timetableId: String): Result<String> {

        val doc = timetablesRepository.getTimetableDetail(timetableId)

        val workDayList = ArrayList<String>()
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

        if (doc.select("p.c-vozni-red__valid").text()
                .isBlank() && workDayList.isEmpty() && saturdayList.isEmpty() && sundayList.isEmpty()
        ) {
            // Null response
            return Result.failure(Exception("null"))
        } else {
            // Valid response
            return Result.success(timetableItems.joinToString(Constants.EMA_DELIMITER))
        }
    }
}