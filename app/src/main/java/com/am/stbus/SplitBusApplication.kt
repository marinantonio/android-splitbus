package com.am.stbus

import android.app.Application
import com.am.stbus.common.di.applicationModule
import com.am.stbus.common.di.useCaseModule
import com.am.stbus.common.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SplitBusApplication : Application() {

    private var listOfModules = listOf(
            applicationModule,
            useCaseModule,
            viewModelModule
    )

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(listOfModules)
        }
    }
}