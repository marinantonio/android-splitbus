package com.am.stbus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.DepartureBoard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.am.stbus.R
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.home.HomeScreen
import com.am.stbus.presentation.screens.info.InfoScreen
import com.am.stbus.presentation.screens.info.maps.GmapsScreen
import com.am.stbus.presentation.screens.info.prometweb.PrometWebScreen
import com.am.stbus.presentation.screens.stops.BusStopArrivalsListScreen
import com.am.stbus.presentation.screens.stops.detail.BusStopArrivalsDetailScreen
import com.am.stbus.presentation.screens.timetables.TimetablesScreen
import com.am.stbus.presentation.screens.timetables.detail.TimetablesDetailScreen
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

                // Recommended: use rememberNavBackStack for persistent state
                val backStack = rememberNavBackStack(HomeScreenKey)

                val bottomBarVisible = TOP_LEVEL_ROUTES.map { it.navKey }.contains(backStack.last())

                val busStopFavourites = viewModel.busStopFavourites

                val busLinesFavourites = viewModel.busLinesFavourites

                Scaffold(
                    bottomBar = {
                        if (bottomBarVisible) {
                            Column {
                                HorizontalDivider(
                                    thickness = 0.8.dp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
                                )
                                NavigationBar(
                                    containerColor = Color.Transparent
                                ) {
                                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                                        val isSelected = topLevelRoute.navKey == backStack.last()

                                        NavigationBarItem(
                                            selected = isSelected,
                                            onClick = {
                                                backStack.add(topLevelRoute.navKey)
                                                // Keep only the home screen and the current selection
                                                if (backStack.size > 2) {
                                                    backStack.removeAt(1)
                                                }
                                            },
                                            icon = {
                                                Icon(
                                                    imageVector = topLevelRoute.icon,
                                                    contentDescription = null
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(topLevelRoute.title),
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->

                    val bottomPadding = innerPadding.calculateBottomPadding()

                    NavDisplay(
                        modifier = Modifier.padding(
                            bottom =
                                if (bottomBarVisible) {
                                    bottomPadding
                                } else {
                                    0.dp
                                }
                        ),
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
                                        backStack.removeLastOrNull()
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
                                    navBarPadding = bottomPadding,
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
                                        backStack.removeLastOrNull()
                                    }
                                )
                            }
                            entry<InfoScreenKey> {
                                InfoScreen(
                                    onGmapsClicked = {
                                        backStack.add(InfoGmapsScreenKey)
                                    },
                                    onPrometWebClicked = {
                                        backStack.add(InfoPrometWebScreenKey)
                                    }
                                )
                            }
                            entry<InfoGmapsScreenKey> {
                                GmapsScreen {
                                    backStack.removeLastOrNull()
                                }
                            }
                            entry<InfoPrometWebScreenKey> {
                                PrometWebScreen {
                                    backStack.removeLastOrNull()
                                }
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
    val title: Int
}

private data object Home : BottomBarRoutes {
    override val icon = Icons.Default.Home
    override val navKey: NavKey = HomeScreenKey
    override val title: Int = R.string.nav_favourites
}

private data object BusStops : BottomBarRoutes {
    override val icon = Icons.Rounded.DepartureBoard
    override val navKey: NavKey = BusStopsScreenKey
    override val title: Int = R.string.nav_bus_stops
}

private data object TimetablesList : BottomBarRoutes {
    override val icon = Icons.Default.DirectionsBus
    override val navKey: NavKey = TimetablesListScreenKey
    override val title: Int = R.string.nav_timetables
}

private data object Info : BottomBarRoutes {
    override val icon = Icons.Default.Info
    override val navKey: NavKey = InfoScreenKey
    override val title: Int = R.string.nav_information
}

private val TOP_LEVEL_ROUTES: List<BottomBarRoutes> =
    listOf(Home, BusStops, TimetablesList, Info)


@Serializable
data object HomeScreenKey : NavKey

@Serializable
data object BusStopsScreenKey : NavKey

@Serializable
data object TimetablesListScreenKey : NavKey

@Serializable
data object InfoScreenKey : NavKey

@Serializable
data object InfoGmapsScreenKey : NavKey

@Serializable
data object InfoPrometWebScreenKey : NavKey


@Serializable
data class TimetableDetailScreenKey(val busLine: BusLine) : NavKey

@Serializable
data class BusStopArrivalsDetailScreenKey(val busStop: BusStop) : NavKey
