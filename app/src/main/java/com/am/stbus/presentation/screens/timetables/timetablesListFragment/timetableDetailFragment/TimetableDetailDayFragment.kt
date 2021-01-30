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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.am.stbus.R
import com.am.stbus.common.extensions.sharedGraphViewModel
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment.TimetableDetailFragment.Companion.SATURDAY
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment.TimetableDetailFragment.Companion.SUNDAY
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment.TimetableDetailFragment.Companion.WORK_DAY
import kotlinx.android.synthetic.main.fragment_timetable_day.*

class TimetableDetailDayFragment : Fragment() {

    private val timetablesSharedViewModel by this.sharedGraphViewModel<TimetablesSharedViewModel>(R.id.nav_graph)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timetable_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val day = arguments?.get("day")
        val timetableContent = arguments?.getStringArrayList("timetable_content")
        val timetable = when(day){
            WORK_DAY -> cleanTimetable(timetableContent?.get(2))
            SATURDAY -> cleanTimetable(timetableContent?.get(3))
            SUNDAY -> cleanTimetable(timetableContent?.get(4))
            else -> throw IllegalArgumentException("Wrong day $day")
        }

        // "${timetableContent?.get(0)}
        tv_valid_from.text = timetableContent?.get(1)
        tv_timetable.text = timetable
        tv_note.text = timetableContent?.get(5)

        swipe_to_refresh.setOnRefreshListener {
            (parentFragment as TimetableDetailFragment).fetchAndPopulateTimetable()
        }

        timetablesSharedViewModel.smallLoading.observe(viewLifecycleOwner, Observer {
            swipe_to_refresh.isRefreshing = it
        })
    }

    private fun cleanTimetable(timetable: String?): String {
        return if (!timetable.isNullOrEmpty()) {
            val cleanedString = timetable
                    .replace("[\\[\\],]".toRegex(), "")
                    .replace(" ", " \t\t\t ")
            return if (cleanedString.isBlank()) {
                getString(R.string.timetable_detail_no_buses)
            } else {
                cleanedString
            }
        } else {
            getString(R.string.timetable_detail_no_buses)
        }
    }

    companion object {
        fun newInstance(day: Int, timetableContent: ArrayList<String>): TimetableDetailDayFragment {
            val fragment = TimetableDetailDayFragment()
            fragment.arguments = Bundle().apply {
                putInt("day", day)
                putStringArrayList("timetable_content", timetableContent)
            }
            return fragment
        }
    }
}