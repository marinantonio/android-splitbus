package com.am.stbus.screens.information.informationNewsListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.networking.usecases.GetNewsListUseCase
import com.am.stbus.repositories.local.NewsDao
import com.am.stbus.repositories.models.News
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class InformationNewsListViewModel(
        val getNewsListUseCase: GetNewsListUseCase,
        val newsDao: NewsDao
) : ViewModel() {

    private val schedulers = Schedulers.io()
    private val thread = AndroidSchedulers.mainThread()

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>>
        get() = _newsList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    //private val _error


    init {
        newsDao.getAll()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: SingleObserver<List<News>>{
                    override fun onSuccess(t: List<News>) {
                        handleDbResponse(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Timber.i("onError")
                    }

                })
    }


    private fun getNewsFromAPI(){
        getNewsListUseCase.getArticles()
                .subscribeOn(schedulers)
                .observeOn(thread)
                .subscribe(object: Observer<List<News>>{
                    override fun onComplete() {
                        Timber.i("OnComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.i("onSubscribe")
                    }

                    override fun onNext(news: List<News>) {
                        Timber.i("onNext")
                        handleApiResponse(news)
                    }

                    override fun onError(e: Throwable) {
                        handleError(e.localizedMessage)
                        Timber.i("onError")
                    }

                })
    }

    private fun insertNewsInDb(news: List<News>){
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

    private fun handleDbResponse(news: List<News>) {
        // Ako ima podataka pokazat cemo ih odmah, a ako nema onda loading
        if(news.isNotEmpty()) {
            _newsList.postValue(news)
            _loading.postValue(false)
        } else {
            _loading.postValue(true)
        }

        // I uvijek osvjezavamo podatke s novima
        // TODO: Error handling
        getNewsFromAPI()
    }

    private fun handleApiResponse(news: List<News>) {
        _newsList.postValue(news)
        insertNewsInDb(news)
        if(_loading.value!!)_loading.postValue(false)
    }

    private fun handleError(localizedMessage: String?) {
        if(_newsList.value.isNullOrEmpty()) {
            _loading.postValue(false)
            // todo show error
        }

    }

}
