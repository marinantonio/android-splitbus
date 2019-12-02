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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment.timetableDetailFragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.am.stbus.R
import com.am.stbus.common.Constants
import com.am.stbus.common.TimetablesData
import com.am.stbus.common.extensions.loadEmailReport
import com.am.stbus.common.extensions.loadPrometUrl
import com.am.stbus.presentation.screens.timetables.TimetablesSharedViewModel
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment.Companion.FAVOURITE_ADDED
import com.am.stbus.presentation.screens.timetables.timetablesListFragment.TimetablesListFragment.Companion.FAVOURITE_REMOVED
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_timetable.*
import kotlinx.android.synthetic.main.snippet_error.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class TimetableDetailFragment : Fragment() {

    private val timetablesSharedViewModel by sharedViewModel<TimetablesSharedViewModel>()

    private val viewModel: TimetableDetailViewModel by viewModel{ parametersOf(args) }

    private val args: TimetableDetailFragmentArgs by navArgs()

    private var activeViewPagerPage = 0

    private lateinit var addFavorites: MenuItem

    private lateinit var removeFavorites: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            title = "${args.lineNumber} ${getText(TimetablesData().getTimetableTitle(args.lineId))}"
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            inflateMenu(R.menu.menu_timetable)
            onCreateOptionsMenu(menu, MenuInflater(context))
            onPrepareOptionsMenu(menu)
            setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
        }

        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                activeViewPagerPage = position
            }

            override fun onPageSelected(position: Int) {
            }

        })

        viewModel.fullScreenLoading.observe(viewLifecycleOwner, Observer {
            snippet_loading.isVisible = it
            tab_layout.isVisible = !it
        })

        viewModel.timetableContent.observe(viewLifecycleOwner, Observer {
            timetablesSharedViewModel.updateTimetableContent(args.lineId, it.content, it.contentDate)
            setupViewPager(it.content)
        })

        viewModel.updatedFavourite.observe(viewLifecycleOwner, Observer {
            timetablesSharedViewModel.updateFavourite(args.lineId, it)
            when (it) {
                FAVOURITE_ADDED -> {
                    addFavorites.isVisible = false
                    removeFavorites.isVisible = true
                }
                FAVOURITE_REMOVED -> {
                    addFavorites.isVisible = true
                    removeFavorites.isVisible = false
                }
            }
        })

        viewModel.smallLoading.observe(viewLifecycleOwner, Observer {
            timetablesSharedViewModel.setSmallLoading(it)
        })

        viewModel.showSnackBar.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), generateMessage(it.peekContent()), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.timetable_detail_snackbar_refresh)) {
                viewModel.fetchAndPopulateTimetable(args.lineId, args.areaId, args.contentDate, true)
            }.show()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            tab_layout.isVisible = false
            view_pager.isVisible = false
            snippet_error.isVisible = true

            btn_promet.setOnClickListener {
                requireContext().loadPrometUrl()
            }

            btn_error.setOnClickListener {
                requireContext().loadEmailReport(args.lineNumber, errorMessage)
            }
        })

        tab_layout.setupWithViewPager(view_pager)
    }

    private fun generateMessage(date: String): String {
        val formattedDate = LocalDateTime.parse(date).format(DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM ))
        return getString(R.string.timetable_detail_snackbar_message, formattedDate)
    }

    private fun setupViewPager(timetableContent: String) {

        val timetable = timetableContent.split(Constants.EMA_DELIMITER)

        val fragmentsList: List<Fragment> = listOf(
                TimetableDetailDayFragment.newInstance(WORK_DAY, ArrayList(timetable)),
                TimetableDetailDayFragment.newInstance(SATURDAY, ArrayList(timetable)),
                TimetableDetailDayFragment.newInstance(SUNDAY, ArrayList(timetable))
        )

        val titles: List<Int> = listOf(
                R.string.timetable_detail_work_day,
                R.string.timetable_detail_saturday,
                R.string.timetable_detail_sunday
        )

        val adapter = TimetableSliderAdapter(childFragmentManager, fragmentsList, titles)

        view_pager.adapter = adapter
        view_pager.currentItem = activeViewPagerPage
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        addFavorites = menu.findItem(R.id.action_add_favorites)
        removeFavorites = menu.findItem(R.id.action_remove_favorites)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        addFavorites.isVisible = args.favourite == FAVOURITE_REMOVED
        removeFavorites.isVisible = args.favourite == FAVOURITE_ADDED

        super.onPrepareOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_favorites -> {
                viewModel.updateFavouritesStatus(args.lineId, FAVOURITE_ADDED)
            }
            R.id.action_remove_favorites -> {
                viewModel.updateFavouritesStatus(args.lineId, FAVOURITE_REMOVED)
            }
            R.id.action_gmaps -> {
                Toast.makeText(requireContext(), R.string.information_gmaps_not_ready, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun fetchAndPopulateTimetable() {
        viewModel.fetchAndPopulateTimetable(args.lineId, args.areaId, args.contentDate, false)
    }


    companion object {
        const val WORK_DAY = 0
        const val SATURDAY = 1
        const val SUNDAY = 2
    }

    private inner class TimetableSliderAdapter(
            fragmentManager: FragmentManager,
            val fragments: List<Fragment>,
            val fragmentTitles: List<Int>
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = getString(fragmentTitles[position])
    }}
