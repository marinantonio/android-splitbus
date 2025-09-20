package com.am.stbus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.am.stbus.presentation.screens.home.HomeScreenWithToolbar
import com.am.stbus.presentation.screens.timetables.Timetable
import com.am.stbus.presentation.screens.timetables.TimetablesDetailWithToolbar
import com.am.stbus.presentation.screens.timetables.TimetablesWithToolbar
import com.am.stbus.presentation.theme.SplitBusTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SplitBusTheme {

                val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                                val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = {
                                        topLevelBackStack.addTopLevel(topLevelRoute)
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
                ) { _ ->
                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreenWithToolbar()
                            }
                            entry<TimetablesList> {
                                TimetablesWithToolbar {
                                    topLevelBackStack.add(TimetableDetail(it))
                                }
                            }
                            entry<TimetableDetail> {
                                TimetablesDetailWithToolbar(timetable = it.timetable)
                            }

                            /*              entry<ChatList> {
                                              BreweriesScreenWithToolbar {
                                                  topLevelBackStack.add(BreweryDetail(it))
                                              }
                                          }
                                          entry<BreweryDetail> { key ->
                                              BreweryDetailScreen(key.brewery)
                                          }
                                          entry<ChatDetail> {
                                              AccountScreenWithToolbar()
                                          }*/
                        }
                    )
                }
            }
        }
    }
}

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



private data object ChatList : TopLevelRoute {
    override val icon = Icons.Default.Face
}


/*
private data object ChatDetail
data class BreweryDetail(val brewery: Brewery)
*/

private val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Home, TimetablesList)

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
