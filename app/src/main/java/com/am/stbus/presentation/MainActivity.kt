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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.am.stbus.data.models.BusLine
import com.am.stbus.data.models.BusStop
import com.am.stbus.presentation.screens.home.HomeScreen
import com.am.stbus.presentation.screens.stoparrivals.detail.BusStopArrivalsDetailScreen
import com.am.stbus.presentation.screens.stoparrivals.list.BusStopArrivalsListScreen
import com.am.stbus.presentation.screens.timetables.detail.TimetablesDetailScreen
import com.am.stbus.presentation.screens.timetables.list.TimetablesScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SplitBusTheme {

                val backStack = rememberNavBackStack<NavKey>(HomeScreenKey)

                val barsVisible = TOP_LEVEL_ROUTES.map { it.navKey }.contains(backStack.last())

                Scaffold(
                    bottomBar = {
                        if (barsVisible) {
                            NavigationBar {
                                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                                    val isSelected = topLevelRoute.navKey == backStack.last()

                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                            if (!backStack.contains(topLevelRoute.navKey)) {
                                                backStack.add(topLevelRoute.navKey)
                                                if (backStack.size > 2) {
                                                    backStack.removeAt(1)
                                                }
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
                        modifier = Modifier.padding(innerPadding),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            entry<HomeScreenKey> {
                                HomeScreen()
                            }
                            entry<BusStopsScreenKey> {
                                BusStopArrivalsListScreen {
                                    backStack.add(BusStopArrivalsDetailScreenKey(it))
                                }
                            }
                            entry<BusStopArrivalsDetailScreenKey> {
                                BusStopArrivalsDetailScreen(
                                    busStop = it.busStop,
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
                                TimetablesDetailScreen(
                                    busLine = it.busLine,
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

private fun getTitleForActiveScreen(selectedNavKey: NavKey): String {
    return when (selectedNavKey) {
        is HomeScreenKey -> "Favourites"
        is BusStopsScreenKey -> "Bus stops"
        is TimetablesListScreenKey -> "Vozni redovi"
        else -> "Detalj"
    }
}

/*
fun getIconForNavKey(selectedKey: NavKey): ImageVector {
    return when (selectedKey) {
        is HomeScreen -> Icons.Default.Home
        is UserProfile -> Icons.Default.DirectionsBus
        else -> Icons.Default.DirectionsBus
    }
}

private val BOTTOM_BAR_KEYS: List<NavKey> =
    listOf(Home, UserProfile)

*/


/*
private sealed interface TopLevelRoute {
    val icon: ImageVector
}

private data object Home : TopLevelRoute {
    override val icon = Icons.Default.Home
}

private data object TimetablesList : TopLevelRoute {
    override val icon = Icons.Default.DirectionsBus
}

data class TimetableDetail(val timetable: Timetable)

private data object DeparturesScreenWithToolbar : TopLevelRoute {
    override val icon = Icons.Default.AccessTime
}


private val TOP_LEVEL_ROUTES: List<TopLevelRoute> =
    listOf(Home, TimetablesList, DeparturesScreenWithToolbar)

class TopLevelBackStack<T : Any>(startKey: T) {

    // Maintain a stack for each top level route
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {

        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}
*/
