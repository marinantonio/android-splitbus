package com.am.stbus.presentation.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.screens.stoparrivals.list.BusStopItemView
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
        title = "Favourites",
        titleColour = MaterialTheme.colorScheme.primary
    ) {
        if (emptyScreen) {
            Text("Empty")
        } else {
            if (busStopFavourites.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Bus stops",
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Bus lines Favourites",
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
            //omeScreen()
        }
    }
}