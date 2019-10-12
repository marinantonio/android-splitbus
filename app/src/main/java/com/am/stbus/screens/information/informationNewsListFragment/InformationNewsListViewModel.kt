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

package com.am.stbus.screens.information.informationNewsListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.networking.usecases.GetNewsUseCase
import com.am.stbus.repositories.local.NewsDao
import com.am.stbus.repositories.models.NewsListItem
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class InformationNewsListViewModel(
        private val getNewsUseCase: GetNewsUseCase,
        private val newsDao: NewsDao
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
        newsDao.getAll()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .doAfterSuccess { getNewsFromAPI() }
                .subscribe(object: SingleObserver<List<NewsListItem>>{
                    override fun onSuccess(t: List<NewsListItem>) {
                        handleDbResponse(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        _error.postValue(e.localizedMessage)
                    }

                })
    }

    private fun getNewsFromAPI(){
        getNewsUseCase.getNewsList()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<List<NewsListItem>>{
                    override fun onSuccess(t: List<NewsListItem>) {
                        handleApiResponse(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        handleError(e.localizedMessage)
                    }

                })
    }

    private fun insertNewsInDb(news: List<NewsListItem>){
        newsDao.insertAll(news)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: CompletableObserver{
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


    private fun handleDbResponse(news: List<NewsListItem>) {
        // Ako ima podataka prikazat cemo ih odmah, a ako nema onda loading
        if(news.isNotEmpty()) {
            _newsList.postValue(news)
            _loading.postValue(false)
        } else {
            _loading.postValue(true)
        }
    }

    private fun handleApiResponse(news: List<NewsListItem>) {
        _newsList.postValue(news)
        insertNewsInDb(news)
        if(_loading.value!!)_loading.postValue(false)
    }

    private fun handleError(localizedMessage: String?) {
        if(newsList.value.isNullOrEmpty()) {
            _loading.postValue(false)
            _error.postValue(localizedMessage)
        }
    }

}
