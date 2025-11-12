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

package com.am.stbus.presentation.screens.timetables.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusLineArea
import com.am.stbus.data.models.BusLineArea.Companion.getPagerTitle
import com.am.stbus.data.static.CITY_BUS_LINES
import com.am.stbus.data.static.CITY_URBAN_LINES
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import kotlinx.coroutines.launch

@Composable
fun TimetablesScreen(
    onBusLineClicked: (BusLine) -> Unit
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { timetableAreas.size }
    )

    val currentPage = pagerState.currentPage

    AppBarScreen(
        title = "Vozni redovi",
        titleColour = MaterialTheme.colorScheme.secondary
    ) {
        PrimaryTabRow(
            selectedTabIndex = currentPage,
            divider = {}
        ) {
            timetableAreas.forEachIndexed { index, timetableArea ->
                Tab(
                    selected = currentPage == index,
                    selectedContentColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = stringResource(timetableArea.busLineArea.getPagerTitle())
                        )
                    }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->

            val busLinesForArea = timetableAreas[page].busLines

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                busLinesForArea.forEach {
                    BusLineItemView(
                        busLine = it,
                        onBusLineClicked = onBusLineClicked
                    )
                }
            }
        }
    }
}

@Composable
fun BusLineItemView(
    busLine: BusLine,
    onBusLineClicked: (BusLine) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBusLineClicked(busLine)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .width(48.dp)
                .padding(start = 0.dp, top = 12.dp, end = 8.dp, bottom = 12.dp),
            text = busLine.number,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier.padding(end = 16.dp, top = 12.dp, bottom = 12.dp),
            text = stringResource(busLine.title)
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.4.dp
    )
}

private val timetableAreas = listOf(
    TimetableArea(busLineArea = BusLineArea.City, busLines = CITY_BUS_LINES),
    TimetableArea(busLineArea = BusLineArea.Urban, busLines = CITY_URBAN_LINES)
)

data class TimetableArea(
    val busLineArea: BusLineArea,
    val busLines: List<BusLine>
)



@Preview
@Composable
fun TimetablesScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TimetablesScreen {}
        }
    }
}