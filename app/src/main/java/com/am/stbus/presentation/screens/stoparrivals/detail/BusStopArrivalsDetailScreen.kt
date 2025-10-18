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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.screens.common.ErrorScreen
import com.am.stbus.presentation.screens.common.LoadingScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun BusStopArrivalsDetailScreen(
    viewModel: BusStopArrivalsDetailViewModel = koinViewModel(),
    busStop: BusStop,
    onBackClicked: () -> Unit
) {

    val loading = viewModel.loading

    val busStopArrivals = viewModel.busStopArrivals

    val title = busStop.title

    LaunchedEffect(Unit) {
        viewModel.getBusStopArrivals(busStop.id)
    }

    AppBarScreen(
        title = title,
        showBackButton = true,
        onBackClicked = onBackClicked
    ) {
        if (loading) {
            LoadingScreen()
        } else {
            if (busStopArrivals == null) {
                ErrorScreen(title)
            } else {
                busStopArrivals.forEach {
                    Row {
                        Text("${it.title}")
                        Text("${it.time}")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun TimetablesDetailScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //TimetablesDetailScreen()
        }
    }
}