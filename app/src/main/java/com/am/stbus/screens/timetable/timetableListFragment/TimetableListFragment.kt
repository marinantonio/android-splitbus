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

package com.am.stbus.screens.timetable.timetableListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.am.stbus.R
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class TimetableListFragment : Fragment() {

    private val viewModel : TimetableViewModel by lazy {
        ViewModelProviders.of(this).get(TimetableViewModel::class.java)
    }
    private val changeObserver = Observer<Int> { value -> value?.let { incrementClickCount(value) }}
    private var nonLiveCount: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_dashboard, container, false)
        lifecycle.addObserver(viewModel)
        viewModel.changeNotifier.observe(this, changeObserver)
        view.btn_increase_count.setOnClickListener { viewModel.increment() }
        view.btn_open_detail.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.timetableDetailFragment, null))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolbar.title = getString(R.string.nav_timetables)
    }

    private fun incrementClickCount(value: Int) {
        // https://medium.com/@elye.project/android-architecture-components-for-dummies-in-kotlin-50-lines-of-code-29b29d3a381
        view?.tv_count_value?.text = (value).toString()
        view?.tv_non_live_count_value?.text = (nonLiveCount++).toString()
    }


}