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

package com.am.stbus.presentation.screens.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.common.TimetablesData
import com.am.stbus.domain.models.Timetable
import com.am.stbus.domain.usecases.timetables.TimetableListUseCase
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment.Companion.FAVOURITE_REMOVED
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavouritesViewModel(private val timetableListUseCase: TimetableListUseCase) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _timetableList = MutableLiveData<List<Timetable>>()
    val timetableList: LiveData<List<Timetable>>
        get() = _timetableList

    private val _removedFavourite = MutableLiveData<TimetablesListFragment.UpdatedFavourite>()
    val removedFavourite: LiveData<TimetablesListFragment.UpdatedFavourite>
        get() = _removedFavourite

    init {
        getTimetables()
    }

    private fun getTimetables() {
        timetableListUseCase.getTimetables()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object : SingleObserver<List<Timetable>> {
                    override fun onSuccess(timetables: List<Timetable>) {
                        if (timetables.isEmpty()) {
                            saveTimetables()
                        } else {
                            _timetableList.postValue(timetables)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        // TODO
                    }

                    override fun onError(e: Throwable) {
                        // TODO
                    }

                })
    }

    private fun saveTimetables() {
        timetableListUseCase.saveTimetables(TimetablesData.list)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        _timetableList.postValue(TimetablesData.list)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    fun removeFavouritesStatus(position: Int, timetable: Timetable) {

        timetableListUseCase.updateFavourites(timetable.lineId, FAVOURITE_REMOVED)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        _removedFavourite.postValue(
                                TimetablesListFragment.UpdatedFavourite(
                                        position,
                                        timetable.lineId,
                                        FAVOURITE_REMOVED
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