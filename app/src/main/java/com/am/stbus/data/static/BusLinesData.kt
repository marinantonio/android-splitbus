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

package com.am.stbus.data.static

import com.am.stbus.R
import com.am.stbus.data.models.BusLine

val CITY_BUS_LINES = listOf(
    BusLine(id = 1, title = R.string.bus32, number = "3", websiteTitle = "3 BRNIK"),
    BusLine(id = 2, title = R.string.bus31, number = "3A", websiteTitle = "3A BRNIK"),
    BusLine(id = 3, title = R.string.bus6, number = "6", websiteTitle = "6 KILA"),
    BusLine(id = 4, title = R.string.bus761, number = "76", websiteTitle = "7226 KLJACI -"),
)

val CITY_URBAN_LINES = listOf(
    BusLine(id = 100, title = R.string.bus1, number = "1", websiteTitle = "1 BUNJE"),
    BusLine(id = 101, title = R.string.bus022, number = "2", websiteTitle = "2 SPLIT"),
    BusLine(id = 102, title = R.string.bus021, number = "2", websiteTitle = "2 ZRAČNA")
)

val COMBINED_BUS_LINES = CITY_BUS_LINES + CITY_URBAN_LINES

fun findBusLinePerId(id: Int): BusLine? {
    return COMBINED_BUS_LINES.firstOrNull { it.id == id }
}