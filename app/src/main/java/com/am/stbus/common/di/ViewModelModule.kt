package com.am.stbus.common.di

import com.am.stbus.screens.information.InformationListViewModel
import com.am.stbus.screens.information.informationNewsListFragment.InformationNewsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel {
        InformationListViewModel(
                androidContext()
        )
    }

    viewModel {
        InformationNewsListViewModel(
                getNewsListUseCase = get()
        )
    }
}