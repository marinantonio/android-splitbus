package com.am.stbus.screens.timetable.timetableListFragment

import androidx.lifecycle.*
import timber.log.Timber

class TimetableViewModel(private var count: Int = 0) : ViewModel(), LifecycleObserver {

    val changeNotifier = MutableLiveData<Int>()
    fun increment() {
        changeNotifier.value = ++count
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.e("yay onCreate!!")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Timber.e("onResume in")
        changeNotifier.value = count
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Timber.e("onDestroyKicking in")
        changeNotifier.value = count
    }
}