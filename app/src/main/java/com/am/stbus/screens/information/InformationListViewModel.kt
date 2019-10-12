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

package com.am.stbus.screens.information

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.R
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
import com.am.stbus.common.InformationConstants.ID_URBAN_MAP
import com.am.stbus.common.InformationConstants.ID_WEBSITES_CATEGORY
import com.am.stbus.common.InformationConstants.TYPE_HEADER
import com.am.stbus.common.InformationConstants.TYPE_ITEM
import com.am.stbus.repositories.models.Information

class InformationListViewModel(private val context: Context) : ViewModel() {

    private val _informationList = MutableLiveData<List<Information>>()

    val informationList: LiveData<List<Information>>
        get() = _informationList

    init {
        _informationList.postValue(generateInformationList())
    }

    private fun generateInformationList(): List<Information> {
        return listOf(
                Information(ID_GENERAL_CATEGORY, TYPE_HEADER, context.getString(R.string.information_news_general_category), ""),
                Information(ID_LATEST_NEWS, TYPE_ITEM, context.getString(R.string.information_news_title), context.getString(R.string.information_news_desc)),
                //Information(ID_TOURIST_INFO, TYPE_ITEM, context.getString(R.string.information_tourist_title), context.getString(R.string.information_tourist_desc)),
                // Karte
                Information(ID_MAPS_CATEGORY, TYPE_HEADER, context.getString(R.string.information_news_maps_category), ""),
                Information(ID_GMAPS, TYPE_ITEM, context.getString(R.string.information_gmaps_title), context.getString(R.string.information_gmaps_desc)),
                Information(ID_URBAN_MAP, TYPE_ITEM, context.getString(R.string.information_urban_map_title), context.getString(R.string.information_urban_map_desc)),
                Information(ID_SUBURBAN_MAP, TYPE_ITEM, context.getString(R.string.information_suburban_map_title), context.getString(R.string.information_suburban_map_desc)),
                Information(ID_TARIFF_ZONES_MAP, TYPE_ITEM, context.getString(R.string.information_tariff_zones_title), context.getString(R.string.information_tariff_zones_desc)),
                // Ostali web
                Information(ID_WEBSITES_CATEGORY, TYPE_HEADER, context.getString(R.string.information_websites_category), ""),
                Information(ID_CYCLES, TYPE_ITEM, context.getString(R.string.information_cycles_title), context.getString(R.string.information_cycles_desc)),
                Information(ID_PARKING, TYPE_ITEM, context.getString(R.string.information_parking_title), context.getString(R.string.information_parking_desc)),
                Information(ID_GARAGES, TYPE_ITEM, context.getString(R.string.information_garage_title), context.getString(R.string.information_garage_desc)),
                Information(ID_PROMET_WEB, TYPE_ITEM, context.getString(R.string.information_promet_web), context.getString(R.string.information_promet_desc))
                )
    }

}