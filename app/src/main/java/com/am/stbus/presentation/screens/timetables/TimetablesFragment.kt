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

package com.am.stbus.presentation.screens.timetables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.am.stbus.R
import com.am.stbus.common.TimetablesData
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment
import kotlinx.android.synthetic.main.fragment_timetables.*
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimetablesFragment : Fragment() {

    private val timetablesSharedViewModel: TimetablesSharedViewModel by koinNavGraphViewModel(R.id.nav_graph)

    private val viewModel: TimetablesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timetables, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.nav_timetables)

        viewModel.timetableList.observe(viewLifecycleOwner) {
            timetablesSharedViewModel.saveTimetables(it)
        }

        setupViewPager()
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun setupViewPager() {

        val fragmentsList: List<Fragment> = listOf(
                TimetablesListFragment.newInstance(TimetablesData.AREA_CITY),
                TimetablesListFragment.newInstance(TimetablesData.AREA_URBAN),
                TimetablesListFragment.newInstance(TimetablesData.AREA_SUBURBAN),
                TimetablesListFragment.newInstance(TimetablesData.AREA_TROGIR),
                TimetablesListFragment.newInstance(TimetablesData.AREA_SOLTA)
        )

        val titles: List<Int> = listOf(
                R.string.timetables_area_city,
                R.string.timetables_area_urban,
                R.string.timetables_area_suburban,
                R.string.timetables_area_trogir,
                R.string.timetables_area_solta
        )

        val adapter = TimetableSliderAdapter(childFragmentManager, fragmentsList, titles)

        view_pager.adapter = adapter

    }

    private inner class TimetableSliderAdapter(
            fragmentManager: FragmentManager,
            val fragments: List<Fragment>,
            val fragmentTitles: List<Int>
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = getString(fragmentTitles[position])
    }

}