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

package com.am.stbus.presentation.screens.stoparrivals.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.stbus.data.models.BusStopArrivals
import com.am.stbus.domain.usecases.GetBusStopArrivalsUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class BusStopArrivalsDetailViewModel(
    private val getDeparturesUseCase: GetBusStopArrivalsUseCase
) : ViewModel() {

    var loading by mutableStateOf(true)

    var busStopArrivals: List<BusStopArrivals>? = null

    fun getBusStopArrivals(busStopId: Int) {
        loading = true
        viewModelScope.launch {
            val result = getDeparturesUseCase.run(busStopId)

            result.onSuccess {
                Timber.d("Debugging - onSuccess $busStopId")
                busStopArrivals = it
                loading = false
            }.onFailure {
                Timber.d("Debugging - onFailure $busStopId")
                busStopArrivals = null
                loading = false
            }
        }
    }

}