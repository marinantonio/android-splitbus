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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.data.models.BusLine
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.screens.common.ErrorScreen
import com.am.stbus.presentation.screens.common.LoadingScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetablesDetailScreen(
    viewModel: TimetablesDetailViewModel = koinViewModel(),
    busLine: BusLine,
    favourite: Boolean,
    onFavouriteClicked: (BusLine) -> Unit,
    onBackClicked: () -> Unit
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { listOfDays.size }
    )

    val currentPage = pagerState.currentPage

    val loading = viewModel.loading

    val timetableData = viewModel.timetableData

    val title = "${busLine.number} ${stringResource(id = busLine.title)}"

    LaunchedEffect(Unit) {
        viewModel.getTimetableData(busLine.websiteTitle)
    }

    AppBarScreen(
        title = title,
        titleColour = MaterialTheme.colorScheme.secondary,
        showBackButton = true,
        onBackClicked = onBackClicked,
        actions = {
            IconButton(onClick = {
                onFavouriteClicked(busLine)
            }) {
                Icon(
                    imageVector = if (favourite) {
                        Icons.Outlined.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    ) {
        if (loading) {
            LoadingScreen()
        } else {
            if (timetableData == null) {
                ErrorScreen(title)
            } else {
                PrimaryTabRow(
                    selectedTabIndex = currentPage,
                    divider = {}
                ) {
                    listOfDays.forEachIndexed { index, day ->
                        Tab(
                            selected = currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = day.toString()
                                )
                            }
                        )
                    }
                }

                HorizontalPager(state = pagerState) { page ->
                    val departureTimes = when (page) {
                        0 -> timetableData.workdayItems
                        1 -> timetableData.saturdayItems
                        2 -> timetableData.sundayList
                        else -> emptyList()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = timetableData.validFrom
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        departureTimes.forEach { timetableRow ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                timetableRow.forEach { time ->
                                    Text(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.secondaryContainer,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 4.dp),
                                        text = time,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(Modifier.width(14.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = timetableData.notes
                        )
                    }
                }
            }
        }
    }
}


sealed class TimetableDetailScreenDay {
    data object Workday : TimetableDetailScreenDay()
    data object Saturday : TimetableDetailScreenDay()
    data object Sunday : TimetableDetailScreenDay()
}

private val listOfDays = listOf(
    TimetableDetailScreenDay.Workday,
    TimetableDetailScreenDay.Saturday,
    TimetableDetailScreenDay.Sunday
)


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