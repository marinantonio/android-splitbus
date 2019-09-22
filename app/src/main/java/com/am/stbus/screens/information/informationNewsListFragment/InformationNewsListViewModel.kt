package com.am.stbus.screens.information.informationNewsListFragment

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

    val newsList = MutableLiveData<List<NewsListItem>>()
    val loading = MutableLiveData<Boolean>()

    init {
        newsDao.getAll()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<List<NewsListItem>>{
                    override fun onSuccess(t: List<NewsListItem>) {
                        handleDbResponse(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Timber.i("onError news dao")
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
                        Timber.e(e.localizedMessage)
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
            newsList.postValue(news)
            loading.postValue(false)
        } else {
            loading.postValue(true)
        }

        // I uvijek osvjezavamo podatke s novima
        // TODO: Error handling
        getNewsFromAPI()
    }

    private fun handleApiResponse(news: List<NewsListItem>) {
        newsList.postValue(news)
        insertNewsInDb(news)
        if(loading.value!!)loading.postValue(false)
    }

    private fun handleError(localizedMessage: String?) {
        if(newsList.value.isNullOrEmpty()) {
            loading.postValue(false)
            // todo show error
        }

    }

}
