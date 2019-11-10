/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.domain.models.Timetable
import com.am.stbus.domain.usecases.timetables.TimetableListUseCase
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment.Companion.FAVOURITE_ADDED
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment.Companion.FAVOURITE_REMOVED
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TimetablesListViewModel(private val timetableListUseCase: TimetableListUseCase) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _updatedFavourite = MutableLiveData<TimetablesListFragment.UpdatedFavourite>()
    val updatedFavourite: LiveData<TimetablesListFragment.UpdatedFavourite>
        get() = _updatedFavourite

    fun updateFavouritesStatus(position: Int, timetable: Timetable) {

        val favouritesToUpdate = if (timetable.favourite == FAVOURITE_REMOVED) { FAVOURITE_ADDED } else { FAVOURITE_REMOVED }

        timetableListUseCase.updateFavourites(timetable.lineId, favouritesToUpdate)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        Timber.e("onComplete updateFavourite")
                        _updatedFavourite.postValue(
                                TimetablesListFragment.UpdatedFavourite(
                                        position,
                                        timetable.lineId,
                                        favouritesToUpdate
                                )
                        )
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

}