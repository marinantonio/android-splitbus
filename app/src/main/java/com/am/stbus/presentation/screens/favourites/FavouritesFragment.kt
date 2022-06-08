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

package com.am.stbus.presentation.screens.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.domain.models.Timetable
import com.am.stbus.presentation.screens.settings.ContentFragment.Companion.FIRST_RUN_CONTENT
import com.am.stbus.presentation.screens.settings.ContentFragment.Companion.UPDATE_APP_CONTENT
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavouritesFragment : Fragment() {

    private val timetablesSharedViewModel: TimetablesSharedViewModel by koinNavGraphViewModel(R.id.nav_graph)

    private val viewModel: FavouritesViewModel by viewModel()

    private val favouriteAdapter by lazy {
        FavouritesAdapter(
            requireContext(),
            { onTimetableClicked(it) },
            { position, timetable -> onTimetableFavouritesClicked(position, timetable) },
            { onTimetableGmapsClicked(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        timetablesSharedViewModel.timetables.observe(viewLifecycleOwner) { timetables ->
            val favouriteTimetables = timetables?.filter { it.favourite == 1 } ?: emptyList()
            populateFavouriteTimetables(favouriteTimetables)
        }

        viewModel.timetableList.observe(viewLifecycleOwner) { timetablesEvent ->
            timetablesEvent.getContentIfNotHandled {
                populateFavouriteTimetables(it)
            }
        }

        viewModel.removedFavourite.observe(viewLifecycleOwner) {
            timetablesSharedViewModel.updateFavourite(it.lineId, it.favourite)
        }

        checkShouldWelcomeBeShown()
    }

    private fun populateFavouriteTimetables(timetables: List<Timetable>) {

        favouriteAdapter.clear()
        favouriteAdapter.addEntireData(timetables)

        rv_timetables.isVisible = timetables.isNotEmpty()
        tv_empty_title.isVisible = timetables.isEmpty()
        tv_empty_message.isVisible = timetables.isEmpty()


    }

    private fun checkShouldWelcomeBeShown() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        when (sharedPref.getInt(
            SHARED_PREFS_BUILD_VERSION_KEY,
            SHARED_PREFS_BUILD_VERSION_DEFAULT_VALUE
        )) {
            BuildConfig.VERSION_CODE -> Timber.i("All good! :)")
            SHARED_PREFS_BUILD_VERSION_DEFAULT_VALUE -> showWelcomeFragment(FIRST_RUN_CONTENT)
            else -> showWelcomeFragment(UPDATE_APP_CONTENT)
        }
    }

    private fun showWelcomeFragment(updateAppContent: Int) {

        // Pokreni Welcome fragment
        requireView().findNavController().navigate(
            FavouritesFragmentDirections.actionFavouriteFragmentToContentFragment(
                when (updateAppContent) {
                    FIRST_RUN_CONTENT -> FIRST_RUN_CONTENT
                    UPDATE_APP_CONTENT -> UPDATE_APP_CONTENT
                    else -> throw IllegalArgumentException("Wrong first run argument")
                }
            )
        )

        // Update value in shared prefs
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt(SHARED_PREFS_BUILD_VERSION_KEY, BuildConfig.VERSION_CODE)
            commit()
        }

    }

    private fun onTimetableClicked(timetable: Timetable) {
        requireView().findNavController().navigate(
            FavouritesFragmentDirections
                .actionFavouriteFragmentToTimetableDetailFragment(
                    timetable.lineId,
                    timetable.lineNumber,
                    timetable.gmapsId,
                    timetable.areaId,
                    timetable.favourite,
                    timetable.content,
                    timetable.contentDate
                )
        )
    }

    private fun onTimetableFavouritesClicked(position: Int, timetable: Timetable) {
        viewModel.removeFavouritesStatus(position, timetable)
    }

    private fun onTimetableGmapsClicked(timetable: Timetable) {
        requireView().findNavController().navigate(
            FavouritesFragmentDirections
                .actionFavouriteFragmentToInformationGmapsFragment(timetable.gmapsId)
        )
    }

    companion object {
        const val SHARED_PREFS_SPLIT_BUS = "SB_PREFERENCES"
        const val SHARED_PREFS_BUILD_VERSION_KEY = "BUILD_VERSION"
        const val SHARED_PREFS_BUILD_VERSION_DEFAULT_VALUE = 0
    }

}
