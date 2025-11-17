package com.am.stbus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.DepartureBoard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.home.HomeScreen
import com.am.stbus.presentation.screens.stops.detail.BusStopArrivalsDetailScreen
import com.am.stbus.presentation.screens.stops.list.BusStopArrivalsListScreen
import com.am.stbus.presentation.screens.timetables.detail.TimetablesDetailScreen
import com.am.stbus.presentation.screens.timetables.list.TimetablesScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SplitBusTheme {

                val backStack = remember { mutableStateListOf<Any>(HomeScreenKey) }

                val barsVisible = TOP_LEVEL_ROUTES.map { it.navKey }.contains(backStack.last())

                val busStopFavourites = viewModel.busStopFavourites

                val busLinesFavourites = viewModel.busLinesFavourites

                Scaffold(
                    bottomBar = {
                        if (barsVisible) {
                            NavigationBar {
                                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                                    val isSelected = topLevelRoute.navKey == backStack.last()

                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                            backStack.add(topLevelRoute.navKey)
                                            if (backStack.size > 2) {
                                                backStack.removeAt(1)
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = topLevelRoute.icon,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavDisplay(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            entry<HomeScreenKey> {
                                HomeScreen(
                                    busStopFavourites = busStopFavourites,
                                    busLinesFavourites = busLinesFavourites,
                                    onBusStopClicked = { busStop ->
                                        backStack.add(BusStopArrivalsDetailScreenKey(busStop))
                                    },
                                    onBusLineClicked = { busLine ->
                                        backStack.add(TimetableDetailScreenKey(busLine))
                                    }
                                )
                            }
                            entry<BusStopsScreenKey> {
                                BusStopArrivalsListScreen {
                                    backStack.add(BusStopArrivalsDetailScreenKey(it))
                                }
                            }
                            entry<BusStopArrivalsDetailScreenKey> {
                                val favourite = busStopFavourites.contains(it.busStop)
                                BusStopArrivalsDetailScreen(
                                    busStop = it.busStop,
                                    favourite = favourite,
                                    onFavouriteClicked = { busStop ->
                                        if (favourite) {
                                            viewModel.removeBusStopFromFavourites(busStop)
                                        } else {
                                            viewModel.addBusStopToFavourites(busStop)
                                        }
                                    },
                                    onBackClicked = {
                                        backStack.removeAt(backStack.lastIndex)
                                    }
                                )
                            }
                            entry<TimetablesListScreenKey> {
                                TimetablesScreen {
                                    backStack.add(TimetableDetailScreenKey(it))
                                }
                            }
                            entry<TimetableDetailScreenKey> {
                                val favourite = busLinesFavourites.contains(it.busLine)
                                TimetablesDetailScreen(
                                    busLine = it.busLine,
                                    favourite = favourite,
                                    onFavouriteClicked = { busLine ->
                                        if (favourite) {
                                            viewModel.removeBusLineFromFavourites(busLine)
                                        } else {
                                            viewModel.addBusLineToFavourites(busLine)
                                        }
                                    },
                                    onBackClicked = {
                                        backStack.removeAt(backStack.lastIndex)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

// Bottom Bar
private sealed interface BottomBarRoutes {
    val icon: ImageVector
    val navKey: NavKey
}

private data object Home : BottomBarRoutes {
    override val icon = Icons.Default.Home
    override val navKey: NavKey = HomeScreenKey
}

private data object BusStops : BottomBarRoutes {
    override val icon = Icons.Rounded.DepartureBoard
    override val navKey: NavKey = BusStopsScreenKey
}

private data object TimetablesList : BottomBarRoutes {
    override val icon = Icons.Default.DirectionsBus
    override val navKey: NavKey = TimetablesListScreenKey
}

private val TOP_LEVEL_ROUTES: List<BottomBarRoutes> =
    listOf(Home, BusStops, TimetablesList)


@Serializable
data object HomeScreenKey : NavKey

@Serializable
data object BusStopsScreenKey : NavKey

@Serializable
data object TimetablesListScreenKey : NavKey

@Serializable
data class TimetableDetailScreenKey(val busLine: BusLine) : NavKey

@Serializable
data class BusStopArrivalsDetailScreenKey(val busStop: BusStop) : NavKey
