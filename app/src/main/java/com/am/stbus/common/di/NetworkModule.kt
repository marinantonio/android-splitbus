package com.am.stbus.common.di

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
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(get())
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
    }

    /*    // Provide Retrofit
        single {
            Retrofit.Builder()
                .baseUrl("https://api.openbrewerydb.org/v1/")
                .client(get())
                .addConverterFactory(
                    Json.asConverterFactory(
                        "application/json; charset=UTF8".toMediaType()))
                .build()
        }*/
}