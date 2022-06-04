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

package com.am.stbus.presentation.screens.information

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.common.Constants.PROMET_URL
import com.am.stbus.common.InformationConstants.GARAZE_URL
import com.am.stbus.common.InformationConstants.ID_CYCLES
import com.am.stbus.common.InformationConstants.ID_GARAGES
import com.am.stbus.common.InformationConstants.ID_GENERAL_CATEGORY
import com.am.stbus.common.InformationConstants.ID_GMAPS
import com.am.stbus.common.InformationConstants.ID_LATEST_NEWS
import com.am.stbus.common.InformationConstants.ID_MAPS_CATEGORY
import com.am.stbus.common.InformationConstants.ID_PARKING
import com.am.stbus.common.InformationConstants.ID_PROMET_WEB
import com.am.stbus.common.InformationConstants.ID_SUBURBAN_MAP
import com.am.stbus.common.InformationConstants.ID_TARIFF_ZONES_MAP
import com.am.stbus.common.InformationConstants.ID_TOURIST_INFO
import com.am.stbus.common.InformationConstants.ID_URBAN_MAP
import com.am.stbus.common.InformationConstants.ID_WEBSITES_CATEGORY
import com.am.stbus.common.InformationConstants.KARTA_GRAD_URL
import com.am.stbus.common.InformationConstants.KARTA_PRIGRAD_URL
import com.am.stbus.common.InformationConstants.NEXT_BIKE_URL
import com.am.stbus.common.InformationConstants.PARKING_URL
import com.am.stbus.common.InformationConstants.TARIFNE_URL
import com.am.stbus.common.InformationConstants.TYPE_HEADER
import com.am.stbus.common.InformationConstants.TYPE_ITEM
import com.am.stbus.domain.models.Information
import kotlinx.android.synthetic.main.fragment_information_list.*
import org.koin.android.ext.android.inject

class InformationListFragment : Fragment() {

    private val preferencesManager: SharedPreferences by inject()

    private val informationListAdapter by lazy {
        InformationListAdapter(requireContext()) { onInformationClicked(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.nav_information)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = informationListAdapter
        }

        if (informationListAdapter.isEmpty()) {
            informationListAdapter.addEntireData(generateInformationList())
        }
    }

    private fun onInformationClicked(information: Information) {
        when (information.informationId) {
            ID_LATEST_NEWS -> navigateToFragment(information)
            ID_TOURIST_INFO -> Toast.makeText(requireContext(), requireContext().getText(R.string.error_still_not_finished), Toast.LENGTH_SHORT).show()
            ID_GMAPS -> navigateToFragment(information)
            ID_URBAN_MAP -> navigateToFragment(information)
            ID_SUBURBAN_MAP -> navigateToFragment(information)
            ID_TARIFF_ZONES_MAP -> navigateToFragment(information)
            ID_CYCLES -> loadUrl(NEXT_BIKE_URL)
            ID_PARKING -> loadUrl(PARKING_URL)
            ID_GARAGES -> loadUrl(GARAZE_URL)
            ID_PROMET_WEB -> loadUrl(PROMET_URL)
            else -> throw IllegalArgumentException("Wrong information destination!")
        }
    }

    private fun navigateToFragment(information: Information) {
        Navigation.findNavController(requireView()).navigate(let {
            when (information.informationId) {
                ID_LATEST_NEWS -> InformationListFragmentDirections.actionInformationListFragmentToInformationNewsListFragment()
                //ID_TOURIST_INFO -> Toast.makeText(context, context!!.getText(R.string.error_still_not_finished), Toast.LENGTH_SHORT).show()
                ID_GMAPS -> InformationListFragmentDirections.actionInformationListFragmentToInformationGmapsFragment(0)
                ID_URBAN_MAP -> InformationListFragmentDirections.actionInformationListFragmentToInformationImageViewFragment(getString(R.string.information_urban_map_title), KARTA_GRAD_URL)
                ID_SUBURBAN_MAP -> InformationListFragmentDirections.actionInformationListFragmentToInformationImageViewFragment(getString(R.string.information_suburban_map_title), KARTA_PRIGRAD_URL)
                ID_TARIFF_ZONES_MAP -> InformationListFragmentDirections.actionInformationListFragmentToInformationImageViewFragment(getString(R.string.information_tariff_zones_title), TARIFNE_URL)
                //ID_CYCLES -> InformationListFragmentDirections.actionInformationListFragmentToInformationWebViewFragment(getString(R.string.information_cycles_title), NEXT_BIKE_IFRAME)
                else -> throw IllegalArgumentException("Wrong information destination!")
            }
        })
    }

    private fun startGmapsActivity() {
        Toast.makeText(requireContext(), R.string.information_gmaps_not_ready, Toast.LENGTH_SHORT).show()
//        val intent = Intent(context, GmapsActivity::class.java)
//        startActivity(intent)
    }

    private fun loadUrl(url: String) {
        if (preferencesManager.getBoolean("open_urls", true)) {
            val customTabsIntent : CustomTabsIntent = buildCustomTabsIntent()
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            requireContext().startActivity(intent)
        }
    }

    private fun buildCustomTabsIntent(): CustomTabsIntent {
        val intentBuilder = CustomTabsIntent.Builder()
        // Show the title
        intentBuilder.setShowTitle(true)
        // Set the color of Toolbar
        intentBuilder.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        return intentBuilder.build()
    }

    private fun generateInformationList(): List<Information> {
        return listOf(
                Information(ID_GENERAL_CATEGORY, TYPE_HEADER, getString(R.string.information_news_general_category), ""),
                Information(ID_LATEST_NEWS, TYPE_ITEM, getString(R.string.information_news_title), getString(R.string.information_news_desc)),
                //Information(ID_TOURIST_INFO, TYPE_ITEM, getString(R.string.information_tourist_title), getString(R.string.information_tourist_desc)),
                // Karte
                Information(ID_MAPS_CATEGORY, TYPE_HEADER, getString(R.string.information_news_maps_category), ""),
                Information(ID_GMAPS, TYPE_ITEM, getString(R.string.information_gmaps_title), getString(R.string.information_gmaps_desc)),
                Information(ID_URBAN_MAP, TYPE_ITEM, getString(R.string.information_urban_map_title), getString(R.string.information_urban_map_desc)),
                Information(ID_SUBURBAN_MAP, TYPE_ITEM, getString(R.string.information_suburban_map_title), getString(R.string.information_suburban_map_desc)),
                Information(ID_TARIFF_ZONES_MAP, TYPE_ITEM, getString(R.string.information_tariff_zones_title), getString(R.string.information_tariff_zones_desc)),
                // Ostali web
                Information(ID_WEBSITES_CATEGORY, TYPE_HEADER, getString(R.string.information_websites_category), ""),
                Information(ID_CYCLES, TYPE_ITEM, getString(R.string.information_cycles_title), getString(R.string.information_cycles_desc)),
                Information(ID_PARKING, TYPE_ITEM, getString(R.string.information_parking_title), getString(R.string.information_parking_desc)),
                Information(ID_GARAGES, TYPE_ITEM, getString(R.string.information_garage_title), getString(R.string.information_garage_desc)),
                Information(ID_PROMET_WEB, TYPE_ITEM, getString(R.string.information_promet_web), getString(R.string.information_promet_desc))
        )
    }

}
