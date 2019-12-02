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

package com.am.stbus.presentation.screens.information.informationNewsListFragment.informationNewsDetailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.domain.models.NewsItem
import com.am.stbus.domain.usecases.news.NewsDetailUseCase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InformationNewsDetailViewModel(
        private val url: String,
        private val getNewsDetailUseCase: NewsDetailUseCase
) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _newsItem = MutableLiveData<NewsItem>()
    val newsItem: LiveData<NewsItem>
        get() = _newsItem

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        fetchAndPopulateNewsItem(url)
    }

    private fun fetchAndPopulateNewsItem(url: String) {
        getNewsDetailUseCase.get(true, url)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<NewsItem> {
                    override fun onSuccess(t: NewsItem) {
                        _newsItem.postValue(t)
                        _loading.postValue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        _loading.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        _loading.postValue(false)
                        _error.postValue(e.localizedMessage)
                    }
                })

    }

}
