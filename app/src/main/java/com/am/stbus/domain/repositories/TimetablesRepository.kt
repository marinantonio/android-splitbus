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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TimetablesRepository {

    fun getTimetableId(websiteTitle: String): Int? {

        val timetablePath =
            Jsoup.connect(PROMET_ALL_LINES_URL).timeout(NETWORK_REQUEST_TIMEOUT).get()
            .select(
                ".c-vozni-red__search-select option:contains("
                        + websiteTitle
                        + ")"
            )
            .attr("value")

        val timetableId = timetablePath.split("/").lastOrNull()?.toIntOrNull()

        return timetableId
    }

    fun getTimetableForId(timetableId: Int): Document {
        return Jsoup.connect(PROMET_ALL_LINE_ID_URL + timetableId)
            .timeout(NETWORK_REQUEST_TIMEOUT).get()
    }


}