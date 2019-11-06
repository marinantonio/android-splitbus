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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.common.TimetablesData
import com.am.stbus.domain.models.Timetable
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import kotlinx.android.synthetic.main.fragment_timetables_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimetablesListFragment : Fragment() {

    private lateinit var timetablesSharedViewModel: TimetablesSharedViewModel

    private lateinit var timetablesList: List<Timetable>

    private val viewModel: TimetablesListViewModel by viewModel()

    private val timetableListAdapter = TimetablesListAdapter(
            context,
            { onTimetableClicked(it) },
            { position, timetable -> onTimetableFavouritesClicked(position, timetable)},
            { onTimetableGmapsClicked(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timetablesSharedViewModel = activity?.run {
            ViewModelProviders.of(this)[TimetablesSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timetables_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_timetables.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = timetableListAdapter
        }

        val areaId = arguments?.get("areaId")


        timetablesSharedViewModel.timetables.observe(this, Observer<List<Timetable>>{
            if (timetableListAdapter.itemCount == 0) {
                timetablesList = it
                timetableListAdapter.addEntireData(it.filter { timetable -> timetable.areaId == areaId })
            }
        })

        viewModel.updatedFavourite.observe(this, Observer<UpdatedFavourite>{
            timetableListAdapter.updateFavourite(it.position, it.favourite)

            // Iako je pohranjeno u bazi nakon sto iduci put loadamo TimetablesFragment
            // SharedViewFragement ne stigne se bas osvjezit pa ovako to napravimo umjesto njega
            timetablesList.find { timetable ->  timetable.lineId == it.lineId}?.favourite = it.favourite
            timetablesSharedViewModel.saveTimetables(timetablesList)
        })
    }

    companion object {
        fun newInstance(areaId: Int): TimetablesListFragment {
            val fragment = TimetablesListFragment()
            fragment.arguments = Bundle().apply {
                putInt("areaId", areaId)
            }
            return fragment

        }
    }

    private fun onTimetableClicked(timetable: Timetable) {
        Toast.makeText(context, TimetablesData().getTimetableTitleAsOnPrometWebsite(timetable.lineId), Toast.LENGTH_SHORT).show()
    }

    private fun onTimetableFavouritesClicked(position: Int, timetable: Timetable) {
        viewModel.updateFavouritesStatus(position, timetable)
    }

    private fun onTimetableGmapsClicked(timetable: Timetable) {

    }

    data class UpdatedFavourite(val position: Int, val lineId: Int, val favourite: Int)
}