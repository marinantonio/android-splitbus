package com.am.stbus.data

import com.am.stbus.data.models.BusStopArrival
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("stop/{busStopId}/arrival-times")
    suspend fun getBusStopArrivals(
        @Path(value = "busStopId") busStopId: Int
    ): Response<List<BusStopArrival>>

}

