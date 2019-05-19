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

package com.am.stbus.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.adapters.VozniRedAdapter
import com.am.stbus.helpers.DatabaseHandler
import com.am.stbus.models.VozniRed
import kotlinx.android.synthetic.main.fragment_vozni_redovi_list.*

class MainFragment : Fragment() {

    private val PREFS_FIRST_RUN = "PREFS_FIRST_RUN"
    private lateinit var db: DatabaseHandler
    private val TAG = MainFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Ovo ćemo drukčije napravit
        //Shipat ću app s db-om i onda na prvom runu samo kopira iz assetsa u ovu od aplikacije
        // https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
        // https://github.com/jgilfelt/android-sqlite-asset-helper


        //TODO: Prvi run dialog
/*        val settings = activity.getSharedPreferences(PREFS_NAME, 0)
        val database = settings.getBoolean("database", false)
        if (!database) {
            Log.v("!splitBusDb:", "izvrsio se")
            SetupDatabase().execute()
            val editor = settings.edit()
            editor.putBoolean("database", true)
            editor.apply()
            //db.close()
        }*/



        //return rootView
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vozni_redovi_list,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated")

        if (DatabaseHandler(activity).getProfilesCount() < 100) {
            setupDatabase()
        }
        //val profile_counts = db.getProfilesCount()
        //Log.e(TAG, profile_counts.toString())
/*        if (DatabaseHandler(activity).isTableEmpty()) {
            //SetupDatabase().execute()
            setupDatabase()
        }*/

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        db = DatabaseHandler(activity)
        val dataAdapter = VozniRedAdapter(context, db.nedavnoList, 1)
        if (dataAdapter.itemCount > 0) {
            recycler_view.visibility = View.VISIBLE
            tv_error_naslov.visibility = View.GONE
            tv_error_opis.visibility = View.GONE

        } else {
            recycler_view.visibility = View.GONE
            tv_error_naslov.visibility = View.VISIBLE
            tv_error_opis.visibility = View.VISIBLE
        }
        recycler_view.adapter = dataAdapter
    }

    private fun setupDatabase() {
        db = DatabaseHandler(activity)
        try {
            // 0 peglamo za recentse, pa da se ne gubim u adapteru
            // 1 Gradske linije
            // 2 Urbano podrucje
            // 3 Prigradsko podrucje
            // 4 Trogir i Solta

            //1 Gradske linije
            db.addVozniRed(VozniRed(getString(R.string.bus32),1, "3", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus31),2, "3",  1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus6),3, "6",  1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus7),4, "7", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus8),5, "8", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus9),6, "9", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus11),7, "11", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus121),8, "12", 1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus122),9, "12", 1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus14),10, "14", 1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus15),11, "15", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus17),12, "17", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus18),13, "18", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus20),14, "20", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus21),15, "21", 1, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus241),16, "24",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus242),17, "24",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus251),18, "25",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus252),19, "25",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus261),20, "26",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus262),21, "26",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus271),22, "27",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus272),23, "27",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus39),24, "39",1,0))
            db.addVozniRed(VozniRed(getString(R.string.bus40),25, "40",1,0))


            //2 Urbano podrucje
            db.addVozniRed(VozniRed(getString(R.string.bus1),26, "1", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus021),27, "2", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus022),28, "2", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus5),29, "5",  2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus51),30, "5A",  2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus10),31, "10", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus16),32, "16", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus22),33, "22", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus23),34, "23", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus281),35, "28", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus282),36, "28", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus291),37, "29", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus292),38, "29", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus301),39, "30", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus302),40, "30", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus311),41, "31", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus312),42, "31", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus321),43, "32", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus322),44, "32", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus331),45, "33", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus332),46, "33", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus341),47, "34", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus342),48, "34", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus351),49, "35", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus352),50, "35", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus361),51, "36", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus362),52, "36", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus371),53, "37", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus372),54, "37", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus381),55, "38", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus382),56, "38", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus601),57, "60", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus602),58, "60", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.zeljkstari),59, "", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.kstarizelj),60, "", 2, 0))
            db.addVozniRed(VozniRed(getString(R.string.trostdirekt2),61, "", 2, 0))
            //db.addPrigrad(VozniRed(getString(R.string.trostdirekt2),61, 111))

            //3 Linije prigradskog podrucja
            db.addVozniRed(VozniRed(getString(R.string.bus671),62, "67", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus672),63, "67", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus681),64, "68", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus682),65, "68", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus691), 66, "69", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus692),67, "69", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus711),68, "71", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus712),69, "71", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus731),70, "73", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus732),71, "73", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus761),72, "76", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus762),73, "76", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus771),74, "77", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus772),75, "77", 3, 0))
            //79 ne vozi vise
            //db.addPrigrad(new VozniRed(getString(R.string.bus791), "76", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_76.png", "79"));
            //db.addPrigrad(new VozniRed(getString(R.string.bus792), "77", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_77.png", "79"));
            db.addVozniRed(VozniRed(getString(R.string.bus801),78, "80", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus802),79, "80", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus811),80, "81", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus812),81, "81", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus861),82, "86", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus862),83, "86", 3, 0))
            //88 ne vozi vise
            //db.addPrigrad(new VozniRed(getString(R.string.bus881), "84", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_84.png", "88"));
            //db.addPrigrad(new VozniRed(getString(R.string.bus882), "85", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_85.png", "88"));
            //nema vise 901, i 902, samo 900
            //db.addPrigrad(new VozniRed(getString(R.string.bus901), "86", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_86.png", "90"));
            db.addVozniRed(VozniRed(getString(R.string.bus900),87, "90", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus911),88, "91", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus912),89, "91", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus931),90, "93", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus932),91, "93", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.kstarirudine),92, "", 3, 0))
            db.addVozniRed(VozniRed(getString(R.string.rudinekstari),93, "", 3, 0))

            //4 Trogir
            db.addVozniRed(VozniRed(getString(R.string.bus411),94, "41", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus412),95, "41", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus421),96, "42", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus422),97, "42", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus441),98, "44", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus442),99, "44", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus451),100, "45", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus452),101, "45", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus47),102, "47", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus48),103, "48", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus491),104, "49", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus492),105, "49", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus501),106, "50", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus502),107, "50", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus511),108, "51", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus512),109, "51", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus521),110, "52", 4, 0))
            db.addVozniRed(VozniRed(getString(R.string.bus522),111, "52", 4, 0))

            //5 solta
            db.addVozniRed(VozniRed(getString(R.string.buss1),112, "", 5, 0))
            db.addVozniRed(VozniRed(getString(R.string.buss2),113, "", 5, 0))
            db.addVozniRed(VozniRed(getString(R.string.buss3),114, "", 5, 0))
            db.addVozniRed(VozniRed(getString(R.string.buss4),115, "", 5, 0))
        } finally {
            db.close()
        }
    }
}