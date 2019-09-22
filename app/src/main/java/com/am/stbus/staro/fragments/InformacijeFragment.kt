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

package com.am.stbus.staro.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.screens.gmaps.GmapsActivity
import com.am.stbus.staro.activities.SlikaActivity
import com.am.stbus.staro.adapters.InformacijeAdapter
import com.am.stbus.staro.helpers.*
import com.am.stbus.staro.models.Informacija
import kotlinx.android.synthetic.main.fragment_informacije.view.*

class InformacijeFragment : Fragment() {
    private val TAG = InformacijeAdapter::class.java.simpleName

    private lateinit var rootView: View
    private lateinit var informacijeAdapter: InformacijeAdapter
    private var data = ArrayList<Informacija>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /**
         * RECYCLER VIEW
         * https://github.com/andijakl/PartsList/blob/master/app/src/main/java/com/andresjakl/partslist/MainActivity.kt
         * */

        rootView = inflater.inflate(R.layout.fragment_informacije, container, false)
        rootView.rv_informacije.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rootView.rv_informacije.hasFixedSize()

        data.add(Informacija(getString(R.string.informacije_gmaps_naslov), getString(R.string.informacije_gmaps_opis)))
        data.add(Informacija(getString(R.string.informacije_karta_grad_naslov), getString(R.string.informacije_karta_grad_opis)))
        data.add(Informacija(getString(R.string.informacije_karta_prigrad_naslov), getString(R.string.informacije_karta_prigrad_opis)))
        data.add(Informacija(getString(R.string.informacije_tarifne_zone_naslov), getString(R.string.informacije_tarifne_zone_opis)))
        data.add(Informacija(getString(R.string.informacije_prometsplit_naslov), getString(R.string.informacije_prometsplit_opis)))
        data.add(Informacija(getString(R.string.informacije_parking_naslov), getString(R.string.informacije_parking_opis)))
        data.add(Informacija(getString(R.string.informacije_garaze_naslov), getString(R.string.informacije_garaze_opis)))

        informacijeAdapter = InformacijeAdapter(data) { informacija: Informacija -> onItemClicked(informacija)}
        rootView.rv_informacije.adapter = informacijeAdapter

        return rootView
    }

    private fun onItemClicked(informacija: Informacija) {
        when {
            informacija.naslov == getString(R.string.informacije_gmaps_naslov) -> startGmapsActivity()
            informacija.naslov == getString(R.string.informacije_karta_grad_naslov) -> startSlikeActivity(informacija.naslov, KARTA_GRAD_URL)
            informacija.naslov == getString(R.string.informacije_karta_prigrad_naslov) -> startSlikeActivity(informacija.naslov, KARTA_PRIGRAD_URL)
            informacija.naslov == getString(R.string.informacije_tarifne_zone_naslov) -> startSlikeActivity(informacija.naslov, TARIFNE_URL)
            informacija.naslov == getString(R.string.informacije_prometsplit_naslov) -> Utils().openUrl(context!!, PROMET_URL)
            informacija.naslov == getString(R.string.informacije_parking_naslov) -> Utils().openUrl(context!!, PARKING_URL)
            informacija.naslov == getString(R.string.informacije_garaze_naslov) -> Utils().openUrl(context!!, GARAZE_URL)
        }
    }

    private fun startSlikeActivity(naslov: String, url: String) {
        val intent = Intent(context, SlikaActivity::class.java)
        intent.putExtra("title", naslov)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun startGmapsActivity() {
        val intent = Intent(context, GmapsActivity::class.java)
        startActivity(intent)
    }

}
