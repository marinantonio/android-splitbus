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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.screens.common.ErrorScreen
import com.am.stbus.presentation.screens.common.LoadingScreen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BusStopArrivalsDetailScreen(
    viewModel: BusStopArrivalsDetailViewModel = koinViewModel(),
    busStop: BusStop,
    favourite: Boolean,
    onFavouriteClicked: (BusStop) -> Unit,
    onBackClicked: () -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        viewModel.getBusStopArrivals(onPullToRefresh = false, busStopId = busStop.id)
    }

    var running by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            while (running) {
                delay(30_000)
                viewModel.getBusStopArrivals(onPullToRefresh = true, busStopId = busStop.id)
            }
        }
    }

    val loading = viewModel.loading

    val pullToRefreshLoading = viewModel.pullToRefreshLoading

    val pullToRefreshState = rememberPullToRefreshState()

    val busStopTimes = viewModel.busStopTimes

    val title = busStop.title

    AppBarScreen(
        title = title,
        titleColour = MaterialTheme.colorScheme.tertiary,
        showBackButton = true,
        onBackClicked = onBackClicked,
        actions = {
            IconButton(onClick = {
                onFavouriteClicked(busStop)
            }) {
                Icon(
                    imageVector = if (favourite) {
                        Icons.Outlined.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    ) {
        if (loading) {
            LoadingScreen()
        } else {
            PullToRefreshBox(
                isRefreshing = pullToRefreshLoading,
                state = pullToRefreshState,
                onRefresh = {
                    viewModel.getBusStopArrivals(onPullToRefresh = true, busStopId = busStop.id)
                },
                indicator = {
                    PullToRefreshDefaults.LoadingIndicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        state = pullToRefreshState,
                        isRefreshing = pullToRefreshLoading
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    if (busStopTimes == null) {
                        ErrorScreen(title)
                    } else {
                        val timeNow = ZonedDateTime.now()

                        busStopTimes.forEach {

                            val minutesUntil = if (it.time != null) {
                                Duration.between(timeNow, it.time).toMinutes()
                            } else {
                                ""
                            }

                            Row(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it.lineNumber,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(it.title)
                                Spacer(Modifier.weight(1f))
                                Spacer(Modifier.width(8.dp))
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$minutesUntil",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                    )
                                    Text(
                                        text = "min",
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.4.dp
                            )
                        }

                        if (busStopTimes.isNotEmpty()) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 32.dp),
                                text = "NAPOMENA: Podatci su generirani prema podatcima s Prometove internet stranice. Ne odgovaram za njihovu točnost, a više stanica dolazi u sljedećim nadogradnjama",
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 32.dp),
                                text = "NEMA POLAZAKA",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
