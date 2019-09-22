package com.am.stbus.screens.information.informationNewsListFragment.informationNewsDetailFragment

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

    val newsItem = MutableLiveData<NewsItem>()
    val loading = MutableLiveData<Boolean>()

    fun fetchAndPopulateNewsItem(url: String) {
        getNewsUseCase.getNewsDetail(url)
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<NewsItem> {
                    override fun onSuccess(t: NewsItem) {
                        newsItem.postValue(t)
                        loading.postValue(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                        loading.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        loading.postValue(false)
                    }
                })

    }

}
