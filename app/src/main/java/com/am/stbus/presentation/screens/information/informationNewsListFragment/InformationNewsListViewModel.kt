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

package com.am.stbus.presentation.screens.information.informationNewsListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.domain.models.NewsListItem
import com.am.stbus.domain.usecases.GetNewsListUseCase
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class InformationNewsListViewModel(
        private val getNewsListUseCase: GetNewsListUseCase
) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _newsList = MutableLiveData<List<NewsListItem>>()
    val newsList: LiveData<List<NewsListItem>>
        get() = _newsList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        getLocalNewsList()
    }

    private fun getLocalNewsList() {
        getNewsListUseCase.build(false)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object : SingleObserver<List<NewsListItem>> {
                    override fun onSuccess(news: List<NewsListItem>) {
                        getRemoteNewsList()
                        Timber.i("onSuccess")
                        if (news.isNotEmpty()) {
                            _newsList.postValue(news)
                            _loading.postValue(false)
                        }

                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                        _loading.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        Timber.e("onError ${e.localizedMessage}")
                        _error.postValue(e.localizedMessage)
                    }

                })
    }

    private fun getRemoteNewsList() {
        getNewsListUseCase.build(true)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object : SingleObserver<List<NewsListItem>> {
                    override fun onSuccess(news: List<NewsListItem>) {
                        saveRemoteList(news)
                        _newsList.postValue(news)
                        _loading.postValue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.e("On sakskrajb")
                    }

                    override fun onError(e: Throwable) {
                        _error.postValue(e.localizedMessage)
                    }

                })
    }

    private fun saveRemoteList(news: List<NewsListItem>){
        getNewsListUseCase.save(news)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver {
                    override fun onComplete() {
                        Timber.i("OnComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        Timber.i("onError")
                    }
                })
    }
}

