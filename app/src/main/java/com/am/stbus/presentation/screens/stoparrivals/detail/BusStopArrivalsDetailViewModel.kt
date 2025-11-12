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
import com.am.stbus.domain.usecases.GetBusStopArrivalsUseCase
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import timber.log.Timber

class BusStopArrivalsDetailViewModel(
    private val getDeparturesUseCase: GetBusStopArrivalsUseCase
) : ViewModel() {

    var loading by mutableStateOf(true)

    var pullToRefreshLoading by mutableStateOf(false)

    var busStopTimes: List<BusStopTimes>? = null

    init {
        Timber.d("Debugging - BusStopArrivalsDetailViewModel init")
    }

    fun getBusStopArrivals(onPullToRefresh: Boolean, busStopId: Int) {
        if (!onPullToRefresh) {
            busStopTimes = emptyList()
        }
        Timber.d("Debugging - get bus stop arrival $busStopId")
        pullToRefreshLoading = onPullToRefresh
        viewModelScope.launch {
            val result = getDeparturesUseCase.run(busStopId)

            result.onSuccess { busStopArrivals ->
                Timber.d("onSuccess $busStopId ${busStopArrivals}")
                busStopTimes = busStopArrivals.map {
                    BusStopTimes(
                        lineNumber = it.lineNumber ?: "",
                        title = it.title ?: "",
                        time = convertStringToZonedDateTime(it.time)
                    )
                }
                loading = false
                pullToRefreshLoading = false
            }.onFailure {
                Timber.d("onFailure $busStopId $it")
                busStopTimes = null
                pullToRefreshLoading = false
                loading = false
            }
        }
    }

    fun convertStringToZonedDateTime(timeString: String?): ZonedDateTime? {
        return try {
            val localDateTime = LocalDateTime.parse(timeString)
            localDateTime.atZone(ZoneId.of("Europe/Zagreb"))
        } catch (exp: Exception) {
            Timber.d("exp $exp")
            null
        }
    }

    data class BusStopTimes(
        val lineNumber: String,
        val title: String,
        val time: ZonedDateTime?
    )

}


/*
@Serializable
data class BusStopArrival(
    @SerialName("lineNumber")
    val lineNumber: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("time")
    val time: String?,
)*/
