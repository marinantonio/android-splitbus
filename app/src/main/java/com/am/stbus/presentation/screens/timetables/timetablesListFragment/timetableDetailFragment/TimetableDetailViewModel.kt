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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.common.Constants
import com.am.stbus.common.TimetablesData
import com.am.stbus.domain.usecases.timetables.TimetableDetailUseCase
import com.am.stbus.domain.usecases.timetables.TimetableListUseCase
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TimetableDetailViewModel(
        private val timetableListUseCase: TimetableListUseCase,
        private val timetableDetailUseCase: TimetableDetailUseCase
) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _timetableContent = MutableLiveData<String>()
    val timetableContent: LiveData<String>
        get() = _timetableContent

    private val _fullScreenLoading = MutableLiveData<Boolean>()
    val fullScreenLoading: LiveData<Boolean>
        get() = _fullScreenLoading

    private val _smallLoading = MutableLiveData<Boolean>()
    val smallLoading: LiveData<Boolean>
        get() = _smallLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _updatedFavourite = MutableLiveData<Int>()
    val updatedFavourite: LiveData<Int>
        get() = _updatedFavourite



    fun populateTimetable(lineId: Int, areaId: Int, timetableContent: String) {
        if (timetableContent.isNotEmpty()) {
            _timetableContent.postValue(timetableContent)
            _smallLoading.postValue(true)
        } else {
            _fullScreenLoading.postValue(true)
        }

        // Update endpoint
        fetchAndPopulateTimetable(lineId, areaId)
    }

    private fun fetchAndPopulateTimetable(lineId: Int, areaId: Int) {

        val timetableBaseUrl = when(areaId) {
            TimetablesData.AREA_CITY -> Constants.AREA_CITY_URL
            TimetablesData.AREA_URBAN -> Constants.AREA_URBAN_URL
            TimetablesData.AREA_SUBURBAN -> Constants.AREA_SUBURBAN_URL
            TimetablesData.AREA_TROGIR -> Constants.AREA_TROGIR_URL
            TimetablesData.AREA_SOLTA -> Constants.AREA_SOLTA_URL
            else -> throw IllegalArgumentException("Illegal areaId $areaId")
        }

        timetableDetailUseCase.getTimetableDetail(timetableBaseUrl, lineId)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<String> {
                    override fun onSuccess(t: String) {
                        _timetableContent.postValue(t)
                        _fullScreenLoading.postValue(false)
                        _smallLoading.postValue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        //_loading.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        //_loading.postValue(false)
                        //_error.postValue(e.localizedMessage)
                    }
                })


    }

    fun updateFavouritesStatus(lineId: Int, favouriteStatus: Int) {

        timetableListUseCase.updateFavourites(lineId, favouriteStatus)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        Timber.e("onComplete updateFavourite")
                        _updatedFavourite.postValue(favouriteStatus)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    // TODO: Implement the ViewModel
    // MAIN LOADING SCREEN SHOULD BE PRESENT


    fun saveTimetableContent(timetableContent: String) {
        // Store this in the db
    }
}
