package com.am.stbus.common.di

import com.am.stbus.networking.usecases.GetNewsUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single {
        GetNewsUseCase()
    }

}