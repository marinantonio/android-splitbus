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

package com.am.stbus.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.data.static.findBusLinePerId
import com.am.stbus.data.static.findBusStopArrivalPerId
import com.am.stbus.domain.usecases.FavouritesRoomDbUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val favouritesRoomDbUseCase: FavouritesRoomDbUseCase
) : ViewModel() {

    val busStopFavourites =
        mutableStateListOf<BusStop>()

    val busLinesFavourites =
        mutableStateListOf<BusLine>()

    init {
        getAllFavourites()
    }

    fun getAllFavourites() {
        viewModelScope.launch {
            val favourites = favouritesRoomDbUseCase.getAllTimetable()

            val busStops =
                favourites.filter { it.type == 0 }.mapNotNull { findBusStopArrivalPerId(it.id) }
            val busLines = favourites.filter { it.type == 1 }.mapNotNull { findBusLinePerId(it.id) }

            busStopFavourites.addAll(busStops)
            busLinesFavourites.addAll(busLines)
        }
    }

    fun addBusStopToFavourites(busStop: BusStop) {
        viewModelScope.launch {
            favouritesRoomDbUseCase.add(busStop.id, 0)
        }

        busStopFavourites.add(busStop)
    }

    fun removeBusStopFromFavourites(busStop: BusStop) {
        viewModelScope.launch {
            favouritesRoomDbUseCase.remove(busStop.id, 0)
        }

        busStopFavourites.remove(busStop)
    }

    fun addBusLineToFavourites(busLine: BusLine) {
        viewModelScope.launch {
            favouritesRoomDbUseCase.add(busLine.id, 1)
        }

        busLinesFavourites.add(busLine)
    }

    fun removeBusLineFromFavourites(busLine: BusLine) {
        viewModelScope.launch {
            favouritesRoomDbUseCase.remove(busLine.id, 1)
        }

        busLinesFavourites.remove(busLine)
    }

}
