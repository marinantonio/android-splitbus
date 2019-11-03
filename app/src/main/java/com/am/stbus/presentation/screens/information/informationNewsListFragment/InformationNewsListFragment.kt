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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.domain.models.NewsListItem
import com.am.stbus.presentation.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_information_news_list.*
import kotlinx.android.synthetic.main.snippet_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class InformationNewsListFragment : Fragment() {

    private val viewModel: InformationNewsListViewModel by viewModel()

    private val informationNewsListAdapter = InformationNewsListAdapter(context) { onNewsItemClicked(it)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.toolbar.title = getString(R.string.information_news_title)

        viewModel.newsList.observe(this, Observer<List<NewsListItem>> {
            onNewsListAdded(it)
        })

        viewModel.loading.observe(this, Observer<Boolean>{
            snippet_loading.isVisible = it
            rv_news_list.isVisible = !it
        })

        viewModel.error.observe(this, Observer<String> {
            if (informationNewsListAdapter.itemCount < 1)
                handleErrorScreen(it)
        })

        rv_news_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = informationNewsListAdapter
        }
    }

    private fun handleErrorScreen(it: String?) {
        rv_news_list.isVisible = false
        snippet_loading.isVisible = false
        snippet_error.isVisible = true

        btn_promet.setOnClickListener {
            Timber.e("Promet!")
            // TODO
        }

        btn_error.setOnClickListener {
            Timber.e("Report error")
            // TODO
        }

    }

    private fun onNewsListAdded(it: List<NewsListItem>) {
        informationNewsListAdapter.clear()
        informationNewsListAdapter.addEntireData(it)
    }

    private fun onNewsItemClicked(it: NewsListItem) {
        view?.findNavController()?.navigate(InformationNewsListFragmentDirections
                .actionInformationNewsListFragmentToInformationNewsDetailFragment(
                        it.title,
                        it.date,
                        it.url
        ))
    }

}
