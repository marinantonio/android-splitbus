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

package com.am.stbus.presentation.screens.stops.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.R
import com.am.stbus.data.models.BusStop
import com.am.stbus.data.static.BUS_ARRIVALS_STOPS
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.theme.SplitBusTheme

@Composable
fun BusStopArrivalsListScreen(
    onBusStopClicked: (BusStop) -> Unit
) {

    val busStops = BUS_ARRIVALS_STOPS

    AppBarScreen(
        title = stringResource(R.string.nav_bus_stops),
        titleColour = MaterialTheme.colorScheme.tertiary
    ) {
        busStops.forEach {
            BusStopItemView(
                busStop = it,
                onBusStopClicked = onBusStopClicked
            )
        }
    }
}

@Composable
fun BusStopItemView(
    busStop: BusStop,
    onBusStopClicked: (BusStop) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBusStopClicked(busStop)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = stringResource(busStop.title))
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.4.dp
    )
}

@Preview
@Composable
fun BusStopArrivalsListScreenPreview() {
    SplitBusTheme {
        BusStopArrivalsListScreen {}
    }
}