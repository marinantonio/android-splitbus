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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.domain.models.Timetable
import com.am.stbus.presentation.screens.timetables.TimetablesFragmentDirections
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import kotlinx.android.synthetic.main.fragment_timetables_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimetablesListFragment : Fragment() {

    private val timetablesSharedViewModel by sharedViewModel<TimetablesSharedViewModel>(from = { findNavController().getViewModelStoreOwner(R.id.nav_graph)})

    private val viewModel: TimetablesListViewModel by viewModel()

    private val timetableListAdapter = TimetablesListAdapter(
            context,
            { onTimetableClicked(it) },
            { position, timetable -> onTimetableFavouritesClicked(position, timetable)},
            { onTimetableGmapsClicked(it) }
    )

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

        timetablesSharedViewModel.timetables.observe(viewLifecycleOwner, Observer<List<Timetable>>{
            timetableListAdapter.clear()

            // Ne najljepsi fix, ali ovo se dogada zbog sharedViewModela!
            // Ukratko, ako parentActivity bude unisten sharedViewModel nece imat odakle dobit podatke
            // pa ce se app rusiti. Ako napravim da se rekreira onda korisnik nece dobit updejtani recyclerView
            // (Ako se skine timetable ili promjeni fav status) to se nece odraziti u listi ovdje, stoga sharedViewModel
            // Mora pratiti nav_graph.
            if (!it.isNullOrEmpty()) {
                timetableListAdapter.addEntireData(it.filter { timetable -> timetable.areaId == areaId })
            }
        })

        viewModel.updatedFavourite.observe(viewLifecycleOwner, Observer<UpdatedFavourite>{
            timetablesSharedViewModel.updateFavourite(it.lineId, it.favourite)
        })
    }

    private fun onTimetableClicked(timetable: Timetable) {
        parentFragment?.findNavController()?.navigate(TimetablesFragmentDirections
                .actionTimetablesFragmentToTimetablesDetailFragment(
                        timetable.lineId,
                        timetable.lineNumber,
                        timetable.gmapsId,
                        timetable.areaId,
                        timetable.favourite,
                        timetable.content,
                        timetable.contentDate
                ))
    }

    private fun onTimetableFavouritesClicked(position: Int, timetable: Timetable) {
        viewModel.updateFavouritesStatus(position, timetable)
    }

    private fun onTimetableGmapsClicked(timetable: Timetable) {
        Toast.makeText(requireContext(), R.string.information_gmaps_not_ready, Toast.LENGTH_SHORT).show()
//        val intent = Intent(requireActivity(), GmapsActivity::class.java)
//        intent.putExtra("gmaps", timetable.gmapsId)
//        startActivity(intent)
    }

    companion object {
        fun newInstance(areaId: Int): TimetablesListFragment {
            val fragment = TimetablesListFragment()
            fragment.arguments = Bundle().apply {
                putInt("areaId", areaId)
            }
            return fragment

        }

        const val FAVOURITE_ADDED = 1
        const val FAVOURITE_REMOVED = 0
    }

    data class UpdatedFavourite(val position: Int, val lineId: Int, val favourite: Int)
}