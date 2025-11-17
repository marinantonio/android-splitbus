package com.am.stbus.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BusAlert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.stbus.R
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.screens.stops.list.BusStopItemView
import com.am.stbus.presentation.screens.timetables.list.BusLineItemView
import com.am.stbus.presentation.theme.SplitBusTheme

@Composable
fun HomeScreen(
    busStopFavourites: List<BusStop>,
    busLinesFavourites: List<BusLine>,
    onBusStopClicked: (BusStop) -> Unit,
    onBusLineClicked: (BusLine) -> Unit
) {

    val emptyScreen = busStopFavourites.isEmpty() && busLinesFavourites.isEmpty()

    AppBarScreen(
        title = stringResource(R.string.nav_favourites),
        titleColour = MaterialTheme.colorScheme.primary
    ) {
        if (emptyScreen) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(52.dp),
                    imageVector = Icons.Rounded.BusAlert,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.favourite_empty_title)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.favourite_empty_message)
                )
            }
        } else {
            if (busStopFavourites.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(R.string.nav_bus_stops),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                busStopFavourites.forEach { busStop ->
                    BusStopItemView(
                        busStop = busStop,
                        onBusStopClicked = onBusStopClicked
                    )
                }
            }
            if (busLinesFavourites.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(R.string.nav_timetables),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
                busLinesFavourites.forEach {
                    BusLineItemView(
                        busLine = it,
                        onBusLineClicked = onBusLineClicked
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeScreen(emptyList(), emptyList(), {}) { }
            //omeScreen()
        }
    }
}