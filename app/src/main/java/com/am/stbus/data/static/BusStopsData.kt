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

import com.am.stbus.data.models.BusStop

val BUS_ARRIVALS_STOPS = listOf(
    BusStop(
        id = 675258,
        title = "HNK"
    ),
    BusStop(
        id = 676742,
        title = "HNK Izlaz"
    ),
    BusStop(
        id = 675289,
        title = "Pazar - Prema trajektnoj luci"
    ),
    BusStop(
        id = 675290,
        title = "Pazar - Prema općini"
    ),
    BusStop(
        id = 675287,
        title = "Općina - Prema pazaru"
    ),
    BusStop(
        id = 675286,
        title = "Općina - Prema Dom. Rata"
    ),
    BusStop(
        id = 676393,
        title = "Autobusni kolodvor Sukoišan"
    )
)

fun findBusStopArrivalPerId(id: Int): BusStop? {
    return BUS_ARRIVALS_STOPS.firstOrNull { it.id == id }
}