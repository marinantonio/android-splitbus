package com.am.stbus.common.di

import com.am.stbus.networking.usecases.GetNewsListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetNewsListUseCase()
    }
}