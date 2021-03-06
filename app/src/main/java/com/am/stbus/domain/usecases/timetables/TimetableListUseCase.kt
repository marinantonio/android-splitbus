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

package com.am.stbus.domain.usecases.timetables

import com.am.stbus.domain.models.Timetable
import com.am.stbus.domain.repositories.TimetableRepository
import io.reactivex.Completable
import io.reactivex.Single

class TimetableListUseCase(private val timetableRepository: TimetableRepository) {

    fun saveTimetables(list: List<Timetable>): Completable {
        return timetableRepository.saveTimetables(list)
    }

    fun getTimetables(): Single<List<Timetable>> {
        return timetableRepository.getTimetables()
    }

    fun updateFavourites(lineId: Int, favourite: Int): Completable {
        return timetableRepository.updateFavourites(lineId, favourite)
    }
}