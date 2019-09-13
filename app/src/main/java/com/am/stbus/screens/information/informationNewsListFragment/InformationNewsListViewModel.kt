package com.am.stbus.screens.information.informationNewsListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.networking.models.News
import com.am.stbus.networking.usecases.GetNewsListUseCase
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class InformationNewsListViewModel(
        val getNewsListUseCase: GetNewsListUseCase
) : ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()

    val newsList: LiveData<List<News>>
        get() = _newsList

    init {
        getNewsListUseCase.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<List<News>>{
                    override fun onComplete() {
                        Timber.i("OnComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                    }

                    override fun onNext(news: List<News>) {
                        _newsList.postValue(news)
                    }

                    override fun onError(e: Throwable) {
                        Timber.i("error")
                    }

                })

    }
}
