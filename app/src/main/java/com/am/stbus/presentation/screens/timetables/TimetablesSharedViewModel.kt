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

package com.am.stbus.presentation.screens.timetables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.domain.models.Timetable

class TimetablesSharedViewModel : ViewModel() {

    private val _timetables = MutableLiveData<List<Timetable>>()
    val timetables: LiveData<List<Timetable>>
        get() = _timetables

    private val _smallLoading = MutableLiveData<Boolean>()
    val smallLoading: LiveData<Boolean>
        get() = _smallLoading

    fun saveTimetables(timetables: List<Timetable>) {
        _timetables.value = timetables
    }

    fun setSmallLoading(loading: Boolean) {
        _smallLoading.postValue(loading)
    }

    fun updateFavourite(lineId: Int, favourite: Int) {
        //val newTimetables = _timetables.value
        _timetables.value?.find { timetable -> timetable.lineId == lineId}?.favourite = favourite
        _timetables.postValue(_timetables.value)
    }

    fun updateTimetableContent(lineId: Int, content: String, contentDate: String) {
        _timetables.value?.find { timetable -> timetable.lineId == lineId }?.let {
            it.content = content
            it.contentDate = contentDate
        }
        _timetables.postValue(_timetables.value)
    }

}
