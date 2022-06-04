/*
 * MIT License
 *
 * Copyright (c) 2013 - 2021 Antonio Marin
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
import androidx.lifecycle.viewModelScope
import com.am.stbus.domain.models.NewsListItem
import com.am.stbus.domain.usecases.news.NewsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InformationNewsListViewModel(
        private val getNewsListUseCase: NewsListUseCase
) : ViewModel() {

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
        viewModelScope.launch {
            try {
                val newsList = getNewsListUseCase.getLocalNewsList(Dispatchers.IO)
                if (newsList.isNotEmpty()) {
                    _newsList.postValue(newsList)
                    _loading.postValue(false)
                } else {
                    _loading.postValue(true)
                }
                getRemoteNewsList()
            } catch (e: Exception) {
                _loading.postValue(false)
                _error.postValue(e.localizedMessage)
            }
        }
    }

    private fun getRemoteNewsList() {
        viewModelScope.launch {
            try {
                val newsList = getNewsListUseCase.getRemoteNewsList( Dispatchers.IO)
                if (newsList.isNotEmpty()) {
                    _newsList.postValue(newsList)
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                _loading.postValue(false)
                _error.postValue(e.localizedMessage)
            }
        }
    }
}

