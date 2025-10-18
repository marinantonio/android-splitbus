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

package com.am.stbus.data.models

import com.am.stbus.R
import com.am.stbus.common.Constants.AREA_CITY_URL
import com.am.stbus.common.Constants.AREA_SOLTA_URL
import com.am.stbus.common.Constants.AREA_SUBURBAN_URL
import com.am.stbus.common.Constants.AREA_TROGIR_URL
import com.am.stbus.common.Constants.AREA_URBAN_URL
import kotlinx.serialization.Serializable

@Serializable
sealed class BusLineArea {
    @Serializable
    object City : BusLineArea()

    @Serializable
    object Urban : BusLineArea()

    @Serializable
    object Suburban : BusLineArea()

    @Serializable
    object Trogir : BusLineArea()

    @Serializable
    object Solta : BusLineArea()

    companion object {
        fun BusLineArea.getPagerTitle(): Int {
            return when (this) {
                City -> R.string.timetables_area_city
                Urban -> R.string.timetables_area_urban
                Suburban -> R.string.timetables_area_suburban
                Trogir -> R.string.timetables_area_trogir
                Solta -> R.string.timetables_area_solta
            }
        }

        fun BusLineArea.getBaseUrl(): String {
            return when (this) {
                City -> AREA_CITY_URL
                Urban -> AREA_URBAN_URL
                Suburban -> AREA_SUBURBAN_URL
                Trogir -> AREA_TROGIR_URL
                Solta -> AREA_SOLTA_URL
            }
        }
    }
}



