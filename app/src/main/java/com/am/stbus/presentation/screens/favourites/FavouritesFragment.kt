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

package com.am.stbus.presentation.screens.favourites

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
import com.am.stbus.common.extensions.systemUiVisibilityFullScreen
import com.am.stbus.domain.models.Timetable
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_favourites.toolbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {

    private val timetablesSharedViewModel by sharedViewModel<TimetablesSharedViewModel>()

    private val viewModel: FavouritesViewModel by viewModel()

    private val favouriteAdapter = FavouritesAdapter(
            context,
            { onTimetableClicked(it) },
            { position, timetable -> onTimetableFavouritesClicked(position, timetable)},
            { onTimetableGmapsClicked(it) }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.nav_favourites)

        rv_timetables.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = favouriteAdapter
        }

        timetablesSharedViewModel.timetables.observe(viewLifecycleOwner, Observer<List<Timetable>> { timetables ->
            val favouriteTimetables = timetables.filter { it.favourite == 1 }
            if (favouriteTimetables.isNotEmpty()) {
                favouriteAdapter.clear()
                favouriteAdapter.addEntireData(timetables.filter { it.favourite == 1 })
            }

            rv_timetables.isVisible = favouriteTimetables.isNotEmpty()
            tv_error_naslov.isVisible = favouriteTimetables.isEmpty()
            tv_error_opis.isVisible = favouriteTimetables.isEmpty()

        })

        viewModel.timetableList.observe(viewLifecycleOwner, Observer<List<Timetable>> {
            timetablesSharedViewModel.saveTimetables(it)
        })

        viewModel.removedFavourite.observe(viewLifecycleOwner, Observer<TimetablesListFragment.UpdatedFavourite> {
            timetablesSharedViewModel.updateFavourite(it.lineId, it.favourite)
        })
    }


    private fun onTimetableClicked(timetable: Timetable) {
        view?.findNavController()?.navigate(FavouritesFragmentDirections
                .actionFavouriteFragmentToTimetableDetailFragment(
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
        viewModel.removeFavouritesStatus(position, timetable)
    }

    private fun onTimetableGmapsClicked(timetable: Timetable) {

    }

}
