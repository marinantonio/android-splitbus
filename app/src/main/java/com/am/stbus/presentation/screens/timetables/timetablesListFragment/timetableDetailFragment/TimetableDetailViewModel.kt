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
import com.am.stbus.common.Constants.DOWNLOADED_RECENTLY
import com.am.stbus.common.TimetablesData
import com.am.stbus.common.helpers.Event
import com.am.stbus.domain.usecases.timetables.TimetableDetailUseCase
import com.am.stbus.domain.usecases.timetables.TimetableListUseCase
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.net.UnknownHostException

class TimetableDetailViewModel(
        private val args: TimetableDetailFragmentArgs,
        private val timetableListUseCase: TimetableListUseCase,
        private val timetableDetailUseCase: TimetableDetailUseCase
) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _timetableContent = MutableLiveData<UpdatedTimetable>()
    val timetableContent: LiveData<UpdatedTimetable>
        get() = _timetableContent

    private val _fullScreenLoading = MutableLiveData<Boolean>()
    val fullScreenLoading: LiveData<Boolean>
        get() = _fullScreenLoading

    private val _smallLoading = MutableLiveData<Boolean>()
    val smallLoading: LiveData<Boolean>
        get() = _smallLoading

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>>
        get() = _showSnackBar

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _updatedFavourite = MutableLiveData<Int>()
    val updatedFavourite: LiveData<Int>
        get() = _updatedFavourite

    init {
        populateTimetable(args.lineId, args.areaId, args.content, args.contentDate)
    }

    private fun populateTimetable(lineId: Int, areaId: Int, timetableContent: String, date: String) {
        if (timetableContent.isNotEmpty()) {
            _timetableContent.postValue(UpdatedTimetable(timetableContent, date))
            if (isntDownloadedRecently(date)) {
                // Download only if old data is older than 2 hours
                fetchAndPopulateTimetable(lineId, areaId, date, true)
            }
        } else {
            _fullScreenLoading.postValue(true)
            fetchAndPopulateTimetable(lineId, areaId, date, false)
        }
    }

    fun fetchAndPopulateTimetable(lineId: Int, areaId: Int, date: String?, showSmallLoading: Boolean) {
        timetableDetailUseCase.getTimetableDetail(lineId, getTimetableBaseUrl(areaId))
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<String> {
                    override fun onSuccess(content: String) {
                        if (content.isNotBlank()) {
                            _timetableContent.postValue(UpdatedTimetable(content, LocalDateTime.now().toString()))
                            _fullScreenLoading.postValue(false)
                            _smallLoading.postValue(false)
                            saveTimetableContent(lineId, content)
                        } else {
                            onError(IllegalStateException("Empty timetable, possibly wrong url!"))
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                        _smallLoading.postValue(showSmallLoading)
                    }

                    override fun onError(e: Throwable) {
                        when (e) {
                            is UnknownHostException -> {
                                // No internet exception
                                if (date.isNullOrEmpty()) {
                                    _fullScreenLoading.postValue(false)
                                } else {
                                    _smallLoading.postValue(false)
                                    _showSnackBar.postValue(Event(date))
                                }
                            }
                            else -> {
                                // All other errors, including Empty timetable
                                _fullScreenLoading.postValue(false)
                                _smallLoading.postValue(false)
                                _error.postValue(e.localizedMessage)
                                saveTimetableContent(args.lineId, "")
                            }
                        }
                    }
                })
    }

    fun updateFavouritesStatus(lineId: Int, favouriteStatus: Int) {
        timetableListUseCase.updateFavourites(lineId, favouriteStatus)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        _updatedFavourite.postValue(favouriteStatus)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }


    fun saveTimetableContent(lineId: Int, timetableContent: String) {
        timetableDetailUseCase.saveTimetableDetail(lineId, timetableContent, LocalDateTime.now().toString())
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver{
                    override fun onComplete() {
                        _smallLoading.postValue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    private fun getTimetableBaseUrl(areaId: Int): String {
        return when (areaId) {
            TimetablesData.AREA_CITY -> Constants.AREA_CITY_URL
            TimetablesData.AREA_URBAN -> Constants.AREA_URBAN_URL
            TimetablesData.AREA_SUBURBAN -> Constants.AREA_SUBURBAN_URL
            TimetablesData.AREA_TROGIR -> Constants.AREA_TROGIR_URL
            TimetablesData.AREA_SOLTA -> Constants.AREA_SOLTA_URL
            else -> throw IllegalArgumentException("Illegal areaId $areaId")
        }
    }

    private fun isntDownloadedRecently(date: String): Boolean {
        return LocalDateTime.parse(date).isBefore(LocalDateTime.now().minusHours(DOWNLOADED_RECENTLY))
    }

    data class UpdatedTimetable(val content: String, val contentDate: String)
}
