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

package com.am.stbus.staro.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.am.stbus.R
import com.am.stbus.screens.gmaps.GmapsActivity
import com.am.stbus.staro.fragments.VozniRedFragment
import com.am.stbus.staro.helpers.DatabaseHandler
import com.am.stbus.staro.helpers.PROMET_URL
import com.am.stbus.staro.helpers.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_vozni_red.*
import kotlinx.android.synthetic.main.snippet_error.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException
import java.lang.ref.WeakReference

/**
 * Created by Antonio Marin on 26.2.2018.
 */
private val TAG = VozniRedActivity::class.java.simpleName

class VozniRedActivity : AppCompatActivity() {

    private lateinit var addFavorites: MenuItem
    private lateinit var removeFavorites: MenuItem
    private lateinit var db: DatabaseHandler

    private lateinit var naziv: String
    private lateinit var gmaps: String
    var web: Int = 0
    var podrucje: Int = 0
    var nedavno: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vozni_red)
        setSupportActionBar(toolbar)

        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { finish() }
        }

        //Kupimo iz RecycleraView-a
        naziv = intent.getStringExtra("naziv")
        web = intent.getIntExtra("web", 0)
        gmaps = intent.getStringExtra("gmaps")
        podrucje = intent.getIntExtra("podrucje", 0)

        title = "$gmaps $naziv"

        db = DatabaseHandler(this)
        nedavno = db.getVozniRed(web)[0].nedavno

        val fetchUrl = when (podrucje) {
            1 -> "$PROMET_URL/vozni-red/split/"
            2 -> "$PROMET_URL/vozni-red/urbano-podrucje"
            3 -> "$PROMET_URL/vozni-red/prigradsko-podrucje"
            4 -> "$PROMET_URL/vozni-red/trogir/"
            5 -> "$PROMET_URL/vozni-red/otok-solta/"
            else -> "$PROMET_URL/vozni-red/split/"
        }

        btn_error.setOnClickListener {  Utils().reportIssue(this, naziv) }
        btn_promet.setOnClickListener { Utils().openUrl(this, PROMET_URL) }

        val webLineName = Utils().getWebName(web) // web je zapravo vozni red id
        GetVozniRed(this).execute(fetchUrl, webLineName)
    }

    private fun setupViewPager(pager: ViewPager?, vrijedi: String, napomena: String, radniDani: String, subota: String, nedjelja: String) {
        val adapter = Adapter(supportFragmentManager)

        val f1 = VozniRedFragment.newInstance(vrijedi, radniDani, napomena)
        adapter.addFragment(f1, getString(R.string.radni_dan))

        val f2 = VozniRedFragment.newInstance(vrijedi, subota, napomena)
        adapter.addFragment(f2, getString(R.string.subota))

        val f3 = VozniRedFragment.newInstance(vrijedi, nedjelja, napomena)
        adapter.addFragment(f3, getString(R.string.nedjelja))

        pager?.adapter = adapter
    }

    private class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }
    }

    private class GetVozniRed internal constructor(context: VozniRedActivity) : AsyncTask<String, Void, GetVozniRed.Wrapper>() {

        private val activityReference: WeakReference<VozniRedActivity> = WeakReference(context)

        inner class Wrapper {
            var vrijedi: String = ""
            var napomena: String = ""
            var radniDani: String = ""
            var subota: String = ""
            var nedjelja: String = ""
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
            activity.showLoading(1)

        }

        override fun doInBackground(vararg params: String?): Wrapper {

            val wrapper = Wrapper()
            val arrayListRadni = ArrayList<String>()
            val arrayListSubota = ArrayList<String>()
            val arrayListNedjelja = ArrayList<String>()

            try {
                // Za pocetak aplikacija dohvaca citavu tablicu gdje su svi vozni redovi za taj tip
                val docSve = Jsoup.connect(params[0]).timeout(15000).get()

                // Nakon toga prema nazivu voznog reda pronalazi se njegov id
                val elementsVozniRed = docSve.select(".c-vozni-red__search-select option:contains(" + params[1]!!.toUpperCase() + ")")
                val vozniRedId = elementsVozniRed.attr("value")

                // Tek onda formiramo puni URL i trazimo vozni red
                val doc = Jsoup.connect(PROMET_URL + vozniRedId).timeout(15000).get()

/*            val elementsLinija = doc.select("h3.c-vozni-red__line")
            for (e in elementsLinija) {
                wrapper.naziv = e.text()
            }*/

                val elementsVrijedi = doc.select("p.c-vozni-red__valid")
                for (e in elementsVrijedi) {
                    wrapper.vrijedi = e.text()
                }

                val elementsNapomena = doc.select("div.c-vozni-red-note__items")
                for (e in elementsNapomena) {
                    wrapper.napomena = e.text()
                }

                val elementsRadni = doc.select("table.c-vozni-red__table td:eq(0)")
                for (e in elementsRadni) {
                    arrayListRadni.add("\n" + e.text())
                }

                val elementsSubota = doc.select("table.c-vozni-red__table td:eq(1)")
                for (e in elementsSubota) {
                    arrayListSubota.add("\n" + e.text())
                }

                val elementsNedjelja = doc.select("table.c-vozni-red__table td:eq(2)")
                for (e in elementsNedjelja) {
                    arrayListNedjelja.add("\n" + e.text())
                }

                wrapper.radniDani = arrayListRadni.toString()
                wrapper.subota = arrayListSubota.toString()
                wrapper.nedjelja = arrayListNedjelja.toString()

            }catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "IOException" + e.localizedMessage)
            } catch (e: NullPointerException) {
                e.printStackTrace()
                Log.e(TAG, "NullPointerException" + e.localizedMessage)
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                Log.e(TAG, "HttpStatusException" + e.localizedMessage)
            }
            return wrapper
        }

        override fun onPostExecute(wrapper: Wrapper) {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
                activity.updateUI(wrapper.vrijedi, wrapper.napomena, wrapper.radniDani, wrapper.subota, wrapper.nedjelja)
        }
    }

    private fun updateUI(vrijedi: String, napomena: String, radniDani: String, subota: String, nedjelja: String) {
        // Ako je polje "vrijedi" prazno to znaci da aplikacija ne hvata podatke
        // I onda je treba prikazat korisniku prihvatljiv ekran s upozorenjem
        if (vrijedi.isEmpty()) {
            showLoading(2)
        } else {
            setupViewPager(view_pager, vrijedi, napomena, Utils().cleanVozniRed(this, radniDani),
                    Utils().cleanVozniRed(this,subota), Utils().cleanVozniRed(this, nedjelja))
            tabs.setupWithViewPager(view_pager)
            showLoading(0)
        }
    }

    private fun showLoading(viewState: Int) {
        // 0 je prikaz voznog reda, 1 je loading, 2 je error
        when (viewState) {
            0 -> {
                tabs.visibility = View.VISIBLE
                snippet_loading.visibility = View.GONE
                snippet_error.visibility = View.GONE
            }
            1 -> {
                tabs.visibility = View.GONE
                snippet_loading.visibility = View.VISIBLE
                snippet_error.visibility = View.GONE
            }
            2 -> {
                tabs.visibility = View.GONE
                snippet_loading.visibility = View.GONE
                snippet_error.visibility = View.VISIBLE
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_displaybus, menu)

        addFavorites = menu.findItem(R.id.action_add_favorites)
        removeFavorites = menu.findItem(R.id.action_remove_favorites)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)

        if (nedavno == 1) {
            addFavorites.isVisible = false
            removeFavorites.isVisible = true
        } else {
            addFavorites.isVisible = true
            removeFavorites.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_add_favorites -> {
                nedavno = 1
                val db = DatabaseHandler(this)
                db.addFavorites(web)
                this.invalidateOptionsMenu()
                Snackbar.make(view_pager, getString(R.string.snackbar_favorites_add),
                        Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.action_remove_favorites -> {
                nedavno = 0
                val db = DatabaseHandler(this)
                db.removeFavorites(web)
                this.invalidateOptionsMenu()
                Snackbar.make(view_pager, getString(R.string.snackbar_favorites_remove),
                        Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.action_gmaps -> {
                val intent = Intent(baseContext, GmapsActivity::class.java)
                intent.putExtra("gmaps", gmaps)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}