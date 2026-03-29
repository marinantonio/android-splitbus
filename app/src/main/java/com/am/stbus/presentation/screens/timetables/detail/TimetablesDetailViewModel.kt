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

package com.am.stbus.presentation.screens.timetables.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.stbus.data.models.timetables.TimetableDetailData
import com.am.stbus.domain.usecases.timetables.GetLocalTimetableDetailDataUseCase
import com.am.stbus.domain.usecases.timetables.GetRemoteTimetableDetailDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class TimetablesDetailViewModel(
    private val getLocalTimetableDetailDataUseCase: GetLocalTimetableDetailDataUseCase,
    private val getRemoteTimetableDetailDataUseCase: GetRemoteTimetableDetailDataUseCase
) : ViewModel() {

    var fullScreenLoading by mutableStateOf(true)

    var pullToRefreshLoading by mutableStateOf(false)

    var timetableData: TimetableDetailData? = null

    fun getLocalTimetableData(websiteTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getLocalTimetableDetailDataUseCase.run(websiteTitle)

            result.onSuccess {
                timetableData = it.timetableDetailData
                fullScreenLoading = false
                if (it.needsRefresh) {
                    geRemoteTimetableData(
                        websiteTitle, true
                    )
                }
            }.onFailure {
                geRemoteTimetableData(websiteTitle, false)
            }
        }
    }

    fun geRemoteTimetableData(
        websiteTitle: String,
        pullToRefresh: Boolean
    ) {
        if (pullToRefresh) {
            pullToRefreshLoading = true
        } else {
            fullScreenLoading = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = getRemoteTimetableDetailDataUseCase.run(websiteTitle)

            result.onSuccess {
                timetableData = it
                fullScreenLoading = false
                pullToRefreshLoading = false
            }.onFailure {
                Timber.wtf("onFailure $websiteTitle $it")
                Timber.wtf(it)
                fullScreenLoading = false
                pullToRefreshLoading = false
            }
        }
    }
}