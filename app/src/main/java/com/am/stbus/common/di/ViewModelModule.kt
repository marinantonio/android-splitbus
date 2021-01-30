/*
 * MIT License
 *
 * Copyright (c) 2013 - 2021 Antonio Marin
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

package com.am.stbus.common.di

import com.am.stbus.presentation.screens.favourites.FavouritesViewModel
import com.am.stbus.presentation.screens.information.InformationListViewModel
import com.am.stbus.presentation.screens.information.informationNewsListFragment.InformationNewsListViewModel
import com.am.stbus.presentation.screens.information.informationNewsListFragment.informationNewsDetailFragment.InformationNewsDetailViewModel
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import com.am.stbus.presentation.screens.timetables.TimetablesViewModel
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListViewModel
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment.TimetableDetailFragmentArgs
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment.TimetableDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel {
        InformationListViewModel(
        )
    }

    viewModel {
        FavouritesViewModel(
                timetableListUseCase = get()
        )
    }

    viewModel {
        TimetablesViewModel(
                timetableListUseCase = get()
        )
    }

    viewModel {
        TimetablesListViewModel(
                timetableListUseCase = get()
        )
    }

    viewModel {(args: TimetableDetailFragmentArgs) ->
        TimetableDetailViewModel(
                args = args,
                timetableListUseCase = get(),
                timetableDetailUseCase = get()
        )
    }

    viewModel {
        InformationNewsListViewModel(
                getNewsListUseCase = get()
        )
    }

    viewModel {(url: String) ->
        InformationNewsDetailViewModel(
                url = url,
                getNewsDetailUseCase = get()
        )
    }

    viewModel {
        TimetablesSharedViewModel()
    }

}