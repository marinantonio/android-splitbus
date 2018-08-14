package com.am.stbus.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.am.stbus.R
import com.am.stbus.adapters.VozniRedAdapter
import com.am.stbus.helpers.DatabaseHandler
import com.am.stbus.models.VozniRed
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class MainFragment : Fragment() {

    private val PREFS_FIRST_RUN = "PREFS_FIRST_RUN"
    var db: DatabaseHandler? = null
    private val TAG = MainFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Ovo ćemo drukčije napravit
        //Shipat ću app s db-om i onda na prvom runu samo kopira iz assetsa u ovu od aplikacije
        // https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
        // https://github.com/jgilfelt/android-sqlite-asset-helper


        //TODO: Prvi run dialog
/*        val settings = activity!!.getSharedPreferences(PREFS_NAME, 0)
        val database = settings.getBoolean("database", false)
        if (!database) {
            Log.v("!splitBusDb:", "izvrsio se")
            SetupDatabase().execute()
            val editor = settings.edit()
            editor.putBoolean("database", true)
            editor.apply()
            //db!!.close()
        }*/



        //return rootView

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclerview,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated")

        if (DatabaseHandler(activity).getProfilesCount() < 100) {
            setupDatabase()
        }
        //val profile_counts = db!!.getProfilesCount()
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
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        db = DatabaseHandler(activity)
        val dataAdapter = VozniRedAdapter(context, db!!.nedavnoList, 1)
        if (dataAdapter.itemCount > 0) {
            recycler_view.visibility = View.VISIBLE
            text_empty.visibility = View.GONE
        } else {
            recycler_view.visibility = View.GONE
            text_empty.visibility = View.VISIBLE
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
            db!!.addVozniRed(VozniRed(getString(R.string.bus32),2360, 31, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus31),2361, 32,  1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus6),2364, 6,  1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus7),2365, 7, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus8),2366, 8, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus9),2367, 9, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus11),2369, 11, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus121),2370, 12, 1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus122),2371, 12, 1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus14),2372, 14, 1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus15),2373, 15, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus17),2375, 17, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus18),2376, 18, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus20),2377, 20, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus21),2378, 21, 1, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus241),2381, 24,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus242),2382, 24,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus251),2383, 25,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus252),2384, 25,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus261),2385, 26,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus262),2386, 26,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus271),2387, 27,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus272),2388, 27,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus39),2411, 39,1,0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus40),2412, 40,1,0))


            //2 Urbano podrucje
            db!!.addVozniRed(VozniRed(getString(R.string.bus1),2358, 1, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus2),2359, 2, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus5),2362, 5,  2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus51),2363, 51,  2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus10),2368, 10, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus16),2374, 16, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus22),2379, 22, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus23),2380, 23, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus281),2389, 28, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus282),2390, 28, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus291),2391, 29, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus292),2392, 29, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus301),2393, 30, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus302),2394, 30, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus311),2395, 31, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus312),2396, 31, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus321),2397, 32, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus322),2398, 32, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus331),2399, 33, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus332),2400, 33, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus341),2401, 34, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus342),2402, 34, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus351),2403, 35, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus352),2404, 35, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus361),2405, 36, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus362),2406, 36, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus371),2407, 37, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus372),2408, 37, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus381),2409, 38, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus382),2410, 38, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus601),2413, 60, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus602),2414, 60, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.zeljkstari),2442, 113, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.kstarizelj),2443, 113, 2, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.trostdirekt1),2444, 111, 2, 0))
            //db!!.addPrigrad(VozniRed(getString(R.string.trostdirekt2),1432, 111))

            //3 Linije prigradskog podrucja
            db!!.addVozniRed(VozniRed(getString(R.string.bus671),2415, 67, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus672),2416, 67, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus681),2417, 68, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus682),2418, 68, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus691), 2419, 69, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus692),2420, 69, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus711),2421, 71, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus712),2422, 71, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus731),2423, 73, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus732),2424, 73, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus761),2425, 76, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus762),2426, 76, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus771),2427, 77, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus772),2428, 77, 3, 0))
            //79 ne vozi vise
            //db!!.addPrigrad(new VozniRed(getString(R.string.bus791), "73", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_76.png", "79"));
            //db!!.addPrigrad(new VozniRed(getString(R.string.bus792), "74", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_77.png", "79"));
            db!!.addVozniRed(VozniRed(getString(R.string.bus801),2429, 80, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus802),2430, 80, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus811),2431, 81, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus812),2432, 81, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus861),2433, 86, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus862),2434, 86, 3, 0))
            //88 ne vozi vise
            //db!!.addPrigrad(new VozniRed(getString(R.string.bus881), "81", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_84.png", "88"));
            //db!!.addPrigrad(new VozniRed(getString(R.string.bus882), "82", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_85.png", "88"));
            //nema vise 901, i 902, samo 900
            //db!!.addPrigrad(new VozniRed(getString(R.string.bus901), "83", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_86.png", "90"));
            db!!.addVozniRed(VozniRed(getString(R.string.bus900),2435, 90, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus911),2436, 91, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus912),2437, 91, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus931),2438, 93, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus932),2439, 93, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.kstarirudine),2440, 112, 3, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.rudinekstari),2441, 112, 3, 0))


            //4 Trogir
            db!!.addVozniRed(VozniRed(getString(R.string.bus411),2030, 91, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus412),2031, 93, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus421),2032, 93, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus422),2033, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus441),2034, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus442),2035, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus451),2036, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus452),2037, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus47),2038, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus48),2039, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus491),2040, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus492),2041, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus501),2042, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus502),2043, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus511),2044, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus512),2045, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus521),2046, 112, 4, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.bus522),2047, 112, 4, 0))
            //5 solta
            db!!.addVozniRed(VozniRed(getString(R.string.buss1),1903, 112, 5, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.buss2),1904, 112, 5, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.buss3),1905, 112, 5, 0))
            db!!.addVozniRed(VozniRed(getString(R.string.buss4),1906, 112, 5, 0))
        } finally {
            db!!.close()
        }

    }


    /*
    //TODO: Zamjenit progressDialog s necim drugim
    //Postavljanje kompletne baze podataka na prvom paljenju
    private inner class SetupDatabase: AsyncTask<Void, Void, Void>() {

        private var progressDialog: ProgressDialog? = null
        private var uspjelo = false

        override fun onPreExecute() {
            super.onPreExecute()
//            progress_bar.visibility = View.VISIBLE

        /*    progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage(getString(R.string.db_setup))
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()*/
        }

        override fun doInBackground(vararg params: Void): Void? {
            try {
                // 0 peglamo za recentse, pa da se ne gubim u adapteru
                // 1 Gradske linije
                // 2 Urbano podrucje
                // 3 Prigradsko podrucje
                // 4 Trogir i Solta
                db = DatabaseHandler(activity)

                //1 Gradske linije
                db!!.addVozniRed(VozniRed(getString(R.string.bus32),1382, 31, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus31),1383, 32,  1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus6),1386, 6,  1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus7),1387, 7, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus8),1388, 8, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus9),1389, 9, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus11),1391, 11, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus121),1392, 12, 1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus122),1393, 12, 1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus14),1394, 14, 1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus15),1395, 15, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus17),1397, 17, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus18),1398, 18, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus20),1399, 20, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus21),1400, 21, 1, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus241),1403, 24,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus242),1404, 24,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus251),1405, 25,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus252),1406, 25,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus261),1407, 26,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus262),1408, 26,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus271),1409, 27,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus272),1410, 27,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus39),1433, 39,1,0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus40),1434, 40,1,0))


                //2 Urbano podrucje
                db!!.addVozniRed(VozniRed(getString(R.string.bus1),1380, 1, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus2),1381, 2, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus5),1384, 5,  2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus51),1385, 51,  2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus10),1390, 10, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus16),1396, 16, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus22),1401, 22, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus23),1402, 23, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus281),1411, 28, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus282),1412, 28, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus291),1413, 29, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus292),1414, 29, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus301),1415, 30, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus302),1416, 30, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus311),1417, 31, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus312),1418, 31, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus321),1419, 32, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus322),1420, 32, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus331),1421, 33, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus332),1422, 33, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus341),1423, 34, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus342),1424, 34, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus351),1425, 35, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus352),1426, 35, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus361),1427, 36, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus362),1428, 36, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus371),1429, 37, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus372),1430, 37, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus381),1431, 38, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus382),1432, 38, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus601),1435, 60, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus602),1436, 60, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.zeljkstari),1464, 113, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.kstarizelj),1465, 113, 2, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.trostdirekt1),1466, 111, 2, 0))
                //db!!.addPrigrad(VozniRed(getString(R.string.trostdirekt2),1432, 111))

                //3 Linije prigradskog podrucja
                db!!.addVozniRed(VozniRed(getString(R.string.bus671),1437, 67, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus672),1438, 67, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus681),1439, 68, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus682),1440, 68, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus691), 1441, 69, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus692),1442, 69, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus711),1443, 71, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus712),1444, 71, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus731),1445, 73, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus732),1446, 73, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus761),1447, 76, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus762),1448, 76, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus771),1449, 77, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus772),1450, 77, 3, 0))
                //79 ne vozi vise
                //db!!.addPrigrad(new VozniRed(getString(R.string.bus791), "73", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_76.png", "79"));
                //db!!.addPrigrad(new VozniRed(getString(R.string.bus792), "74", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_77.png", "79"));
                db!!.addVozniRed(VozniRed(getString(R.string.bus801),1451, 80, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus802),1452, 80, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus811),1453, 81, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus812),1454, 81, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus861),1455, 86, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus862),1456, 86, 3, 0))
                //88 ne vozi vise
                //db!!.addPrigrad(new VozniRed(getString(R.string.bus881), "81", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_84.png", "88"));
                //db!!.addPrigrad(new VozniRed(getString(R.string.bus882), "82", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_85.png", "88"));
                //nema vise 901, i 902, samo 900
                //db!!.addPrigrad(new VozniRed(getString(R.string.bus901), "83", "http://www.promet-split.hr/BUS_LINIJE/voznired_Page_86.png", "90"));
                db!!.addVozniRed(VozniRed(getString(R.string.bus900),1457, 90, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus911),1458, 91, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus912),1459, 91, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus931),1460, 93, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus932),1461, 93, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.kstarirudine),1462, 112, 3, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.rudinekstari),1463, 112, 3, 0))


                //4 Trogir
                db!!.addVozniRed(VozniRed(getString(R.string.bus411),253, 91, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus412),254, 93, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus421),255, 93, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus422),256, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus441),257, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus442),258, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus47),259, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus48),260, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus491),261, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus492),262, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus501),263, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus502),264, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus511),265, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus512),266, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus521),267, 112, 4, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.bus522),268, 112, 4, 0))
                //5 solta
                db!!.addVozniRed(VozniRed(getString(R.string.buss1),1347, 112, 5, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.buss2),1348, 112, 5, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.buss3),1349, 112, 5, 0))
                db!!.addVozniRed(VozniRed(getString(R.string.buss4),1350, 112, 5, 0))

                uspjelo = true
            } catch (e: Exception) {
                e.stackTrace
            }

            return null
        }


        override fun onPostExecute(result: Void?) {
            if (uspjelo) {
                // progress_bar.visibility = View.GONE

                progressDialog!!.dismiss()
                //db!!.close()
                Toast.makeText(activity, getString(R.string.db_setup_done), Toast.LENGTH_SHORT).show()
                setupRecyclerView()
            } else {
                //progress_bar.visibility = View.GONE

                Toast.makeText(activity, getString(R.string.db_setup_failed), Toast.LENGTH_SHORT).show()
                progressDialog!!.dismiss()
                //db!!.close()
            }
        }

    } */

}