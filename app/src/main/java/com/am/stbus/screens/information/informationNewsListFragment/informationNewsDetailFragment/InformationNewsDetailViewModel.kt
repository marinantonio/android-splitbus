package com.am.stbus.screens.information.informationNewsListFragment.informationNewsDetailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.networking.usecases.GetNewsUseCase
import com.am.stbus.repositories.models.NewsItem
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InformationNewsDetailViewModel(
        private val getNewsUseCase: GetNewsUseCase
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

    fun fetchAndPopulateNewsItem(url: String) {
        getNewsUseCase.getNewsDetail(url)
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
