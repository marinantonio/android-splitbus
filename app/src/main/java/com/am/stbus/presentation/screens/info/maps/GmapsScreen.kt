/*
 * MIT License
 *
 * Copyright (c) 2013 - 2026 Antonio Marin
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

package com.am.stbus.presentation.screens.info.maps

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.am.stbus.R
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GmapsScreen(
    viewModel: GmapsScreenViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    // Helper to check if any location permission is granted
    fun hasAnyLocationPermission() = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    var hasLocationPermission by remember { mutableStateOf(hasAnyLocationPermission()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasLocationPermission = permissions.values.any { it }
            Timber.d("GmapsScreen: Permission result: $hasLocationPermission")
        }
    )

    // Sync permission state when returning to the app (e.g., from settings)
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val current = hasAnyLocationPermission()
                if (current != hasLocationPermission) {
                    hasLocationPermission = current
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Auto-request if missing
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val pullToRefreshLoading = viewModel.vehiclesLoading

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopReceivingData()
        }
    }

    val vehicles = viewModel.vehicleData

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(43.50891, 16.43915),
            12f
        )
    }

    val uiSettings = remember(hasLocationPermission) {
        MapUiSettings(myLocationButtonEnabled = hasLocationPermission)
    }

    val properties = remember(hasLocationPermission) {
        MapProperties(isMyLocationEnabled = hasLocationPermission)
    }

    AppBarScreen(
        title = stringResource(R.string.information_gmaps_title),
        titleColour = MaterialTheme.colorScheme.onBackground,
        onBackClicked = onBackClicked,
        actions = {
            if (!hasLocationPermission) {
                IconButton(onClick = {
                    launcher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "Enable Location",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                properties = properties
            ) {
                vehicles.forEach { vehicle ->
                    key(vehicle.id) {
                        val markerState = rememberUpdatedMarkerState(
                            position = LatLng(vehicle.latitude, vehicle.longitude)
                        )

                        BusMarker(
                            state = markerState,
                            busNumber = vehicle.busLine
                        )
                    }
                }
            }

            if (pullToRefreshLoading) {
                ContainedLoadingIndicator(
                    modifier =
                        Modifier
                            .padding(top = 16.dp)
                            .requiredSize(
                                width = 48.dp,
                                height = 48.dp,
                            )
                            .align(Alignment.TopCenter),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}
