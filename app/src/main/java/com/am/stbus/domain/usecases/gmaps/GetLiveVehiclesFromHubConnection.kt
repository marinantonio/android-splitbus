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

package com.am.stbus.domain.usecases.gmaps

import com.microsoft.signalr.HubConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GetLiveVehiclesFromHubConnection(
    private val hubConnection: HubConnection
) {
    suspend fun start(onData: (VehicleData) -> Unit) {

        hubConnection.on("ReceiveVehicleData", { vehicle ->
            onData(vehicle)
        }, VehicleData::class.java)

        withContext(Dispatchers.IO) {
            try {
                hubConnection.start().blockingAwait()
            } catch (e: Exception) {
                Timber.wtf("SignalR: Failed to start $e")
            }
        }
    }

    suspend fun stop() {
        withContext(Dispatchers.IO) {
            try {
                hubConnection.stop().blockingAwait()
            } catch (e: Exception) {
                Timber.wtf("SignalR: Failed to stop $e")
            }
        }
    }
}

data class VehicleData(
    val id: Long,
    val timestamp: String,
    val name: String,
    val garageNumber: String,
    val vehicleStatus: Int,
    val latitude: Double,
    val longitude: Double
)
