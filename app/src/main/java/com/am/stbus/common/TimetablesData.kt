/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
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

package com.am.stbus.common

import com.am.stbus.R
import com.am.stbus.domain.models.Timetable

class TimetablesData {

    fun getTimetableTitle(lineId: Int): Int =
            when(lineId) {
                // Gradski
                1 -> R.string.bus32
                2 -> R.string.bus32
                3 -> R.string.bus6

                // Urbano
                101 -> R.string.bus1
                102 -> R.string.bus021
                103 -> R.string.bus022
                104 -> R.string.bus023

                // Prigradsko
                201 -> R.string.bus671
                202 -> R.string.bus672
                203 -> R.string.bus681
                204 -> R.string.bus682

                // Trogir i Solta
                301 -> R.string.bus411
                302 -> R.string.bus412
                303 -> R.string.bus421
                304 -> R.string.bus422
                401 -> R.string.buss1
                402 -> R.string.buss2
                403 -> R.string.buss3
                404 -> R.string.buss4
                else -> throw IllegalArgumentException("Illegal lineId $lineId")
            }

    fun getTimetableTitleAsOnPrometWebsite(lineId: Int): String =
            when(lineId) {
                // Gradski
                1 -> "3 BRDA - LOVRINAC"
                2 -> "3 LOVRINAC - BRDA"
                3 -> "6 KILA - VUKOVARSKA - HNK - KILA"

                // Urbano
                101 -> "1 STARINE - HNK - STARINE"
                102 -> "2 ZRAČNA L. - K. SUĆURAC (STRINJE) - POLJIČKA - SPLIT"
                103 -> "2 SPLIT- POLJIČKA - K. SUĆURAC (STRINJE) - ZRAČNA L."
                104 -> "2A K. SUĆURAC(STRINJE) - TR. LUKA - K. SUĆURAC(STRINJE)"

                // Prigradsko
                201 -> "67 SPLIT - KOTLENICE - DOLAC D. - DOLAC G."
                202 -> "67 DOLAC G. - DOLAC D. - KOTLENICE - SPLIT"
                203 -> "68 SPLIT-TUGARE-BLATO-ŠESTANOVAC"
                204 -> "68 ŠESTANOVAC-BLATO-TUGARE-SPLIT"

                // Trogir i Solta
                301 -> "41 TROGIR - PLANO - MALJKOVIĆI"
                302 -> "41 MALJKOVIĆI - PLANO - TROGIR"
                303 -> "42 TROGIR - SLATINE"
                304 -> "42 SLATINE - TROGIR"
                401 -> "MASLINICA - DONJE SELO - SREDNJE SELO - GROHOTE - ROGAČ"
                402 -> "ROGAČ - GROHOTE - SREDNJE SELO - DONJE SELO - MASLINICA"
                403 -> "STOMORSKA - GORNJE SELO - NEČUJAM - GROHOTE - ROGAČ"
                404 -> "ROGAČ - GROHOTE - NEČUJAM - GORNJE SELO - STOMORSKA"
                else -> throw IllegalArgumentException("Illegal lineId $lineId")
            }

    companion object {

        const val AREA_CITY = 0
        const val AREA_URBAN = 1
        const val AREA_SUBURBAN = 2
        const val AREA_TROGIR_SOLTA = 3

        val list: List<Timetable> = listOf(
                // Gradski
                Timetable(1, "3", 31, AREA_CITY, 0, "", ""),
                Timetable(2, "3", 32, AREA_CITY, 0, "", ""),
                Timetable(3, "6", 60, AREA_CITY, 0, "", ""),

                // Urbano
                Timetable(101, "1", 31, AREA_URBAN, 0, "", ""),
                Timetable(102, "2", 31, AREA_URBAN, 0, "", ""),
                Timetable(103, "2", 31, AREA_URBAN, 0, "", ""),
                Timetable(103, "2A", 31, AREA_URBAN, 0, "", ""),

                // Prigradsko
                Timetable(201, "67", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(202, "67", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(203, "68", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(203, "68", 31, AREA_SUBURBAN, 0, "", ""),

                // Trogir i Solta
                Timetable(301, "41", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(302, "41", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(303, "42", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(304, "42", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(401, "", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(402, "", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(403, "", 31, AREA_TROGIR_SOLTA, 0, "", ""),
                Timetable(403, "", 31, AREA_TROGIR_SOLTA, 0, "", "")
        )

    }
}