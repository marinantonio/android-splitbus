package com.am.stbus.common.di

import com.am.stbus.common.Constants.PROMET_API_URL
import com.am.stbus.common.PROMET_KEY
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    // Provide OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                //return response
                chain.proceed(
                    //create request
                    chain.request()
                        .newBuilder()
                        .also {
                            it.addHeader("accept", "application/json, text/plain, */*")
                            it.addHeader("appuid", "PrometSplit.Mobile")
                            it.addHeader("x-auth-key", PROMET_KEY)
                            it.addHeader("x-tenant", "KingICT.PS.Public")
                        }.build()
                )
            }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(PROMET_API_URL)
            .client(get())
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
    }

}