package com.am.stbus.data

import com.am.stbus.data.models.Model
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

// https://api.promet-split.hr/Fleet/api/v1/timetable/stop/675293/departure-times?dateTime=2025-10-04T23%3A30%3A44.548Z

        @GET("timetable/stop/675293/departure-times")
        suspend fun getSomething(
                @Query(value = "dateTime", encoded = true) date: String = "2025-10-04T23:30:44.548Z"
        ): Response<List<Model>>

        /*        @GET("timetable/trip/departure-times")
                suspend fun getSomething(
                        @Query(value = "date", encoded = true) date: String = "2025-10-04T23:27:59.694Z",
                        @Query("pathwayId") pathwayId: Int = 1110618,
                        @Query("departureCode") departureCode: String = "01001000001",
                        @Query("transportServiceRouteId") transportServiceRouteId: Int = 83746
                ): Response<List<Model>>*/

}

