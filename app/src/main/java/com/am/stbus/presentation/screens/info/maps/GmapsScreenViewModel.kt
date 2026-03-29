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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.am.stbus.data.models.LiveVehicle
import com.am.stbus.domain.usecases.gmaps.GetLiveVehiclesFromHubConnection
import com.am.stbus.domain.usecases.gmaps.GetLiveVehiclesUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class GmapsScreenViewModel(
    private val getLiveVehiclesUseCase: GetLiveVehiclesUseCase,
    private val getLiveVehiclesFromHubConnection: GetLiveVehiclesFromHubConnection
) : ViewModel() {

    var vehicleData = mutableStateListOf<GmapsLiveBus>()

    var vehiclesLoading by mutableStateOf(true)

    override fun onCleared() {
        viewModelScope.launch {
            getLiveVehiclesFromHubConnection.stop()
        }
        super.onCleared()
    }

    init {
        getLiveVehiclesAndStartSocketHub()
    }

    private fun getLiveVehiclesAndStartSocketHub() {
        viewModelScope.launch {
            getLiveVehiclesUseCase.run()
                .onSuccess {
                    startSocketHub(it)
                }
                .onFailure {
                    vehiclesLoading = false
                    // todo: error
                    Timber.wtf("Error - $it")
                }
        }
    }

    private fun startSocketHub(listOfLiveVehicles: List<LiveVehicle>) {
        viewModelScope.launch {
            getLiveVehiclesFromHubConnection.start { socketVehicle ->

                val garageNumber = socketVehicle.garageNumber

                val busLine =
                    listOfLiveVehicles.firstOrNull { it.garageNumber == garageNumber }?.name ?: ""

                if (busLine.isNotBlank()) {
                    if (vehiclesLoading) {
                        vehiclesLoading = false
                    }

                    val gmapsLiveBus = GmapsLiveBus(
                        id = socketVehicle.id,
                        busLine = busLine,
                        latitude = socketVehicle.latitude,
                        longitude = socketVehicle.longitude
                    )

                    updateVehicle(gmapsLiveBus)
                }
            }
        }
    }


    private fun updateVehicle(gmapsLiveBus: GmapsLiveBus) {
        val index = vehicleData.indexOfFirst { it.id == gmapsLiveBus.id }

        if (index >= 0) {
            // Update existing item — triggers recomposition
            vehicleData[index] = gmapsLiveBus
        } else {
            // Add new vehicle
            vehicleData.add(gmapsLiveBus)
        }
    }

    fun stopReceivingData() {
        viewModelScope.launch {
            getLiveVehiclesFromHubConnection.stop()
        }
    }

    data class GmapsLiveBus(
        val id: Long,
        val busLine: String,
        val latitude: Double,
        val longitude: Double
    )
}