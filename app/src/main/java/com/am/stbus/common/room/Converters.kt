/*
 * MIT License
 *
 * Copyright (c) 2013 - 2026 Antonio Marin
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

package com.am.stbus.common.room

import androidx.room.TypeConverter
import com.am.stbus.data.models.timetables.TimetableDetailData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    private val gson = Gson()
    private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    @TypeConverter
    fun fromTimetableDetailData(value: TimetableDetailData): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTimetableDetailData(value: String): TimetableDetailData {
        val type = object : TypeToken<TimetableDetailData>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime): String {
        return value.format(formatter)
    }

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value, formatter)
    }
}