package com.am.stbus.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.am.stbus.R
import com.am.stbus.fragments.TimetableFragment
import com.am.stbus.helpers.DatabaseHandler
import kotlinx.android.synthetic.main.activity_display_bus.*

/**
 * Created by marin on 26.2.2018..
 */

class DisplayBusActivity : AppCompatActivity() {

    var naziv: String = ""
    var web: Int = 0
    var gmaps: Int = 0
    var podrucje: Int = 0
    var nedavno: Int = 0
    // ss

    private lateinit var addFavorites: MenuItem
    private lateinit var removeFavorites: MenuItem

    var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_bus)
        setSupportActionBar(toolbar)

        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { finish() }
        }

        //Kupimo iz RecycleraView-a
        naziv = intent.getStringExtra("naziv")
        web = intent.getIntExtra("web", 0)
        gmaps = intent.getIntExtra("gmaps", 0)
        podrucje = intent.getIntExtra("podrucje", 0)
        //nedavno = intent.getIntExtra("nedavno", 0)
        title = naziv

        db = DatabaseHandler(this)
        nedavno = db!!.getVozniRed(web)[0].nedavno
        Log.e("nedavno", nedavno.toString())




        //Log.e("nedavno", nedavno.toString())

        setupViewPager(view_pager)
        tabs.setupWithViewPager(view_pager)

        //postavljamo ViewPager kakav takav


        //TODO:FetchDataFromUrl(this, image_view, loading_progress, web).execute()

    }

    private fun setupViewPager(pager: ViewPager?) {
        val adapter = Adapter(supportFragmentManager)

        val f1 = TimetableFragment.newInstance(naziv, web, gmaps, podrucje, 0)
        adapter.addFragment(f1, getString(R.string.radni_dan))

        val f2 = TimetableFragment.newInstance(naziv, web, gmaps, podrucje, 1)
        adapter.addFragment(f2, getString(R.string.subota))

        val f3 = TimetableFragment.newInstance(naziv, web, gmaps, podrucje, 2)
        adapter.addFragment(f3, getString(R.string.nedjelja))

        pager?.adapter = adapter
    }

    private class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        override fun getItem(position: Int): Fragment = fragments.get(position)

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = titles.get(position)

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
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
            addFavorites.isVisible = false // hide play button
            removeFavorites.isVisible = true // show the pause button
        } else {
            addFavorites.isVisible = true // hide play button
            removeFavorites.isVisible = false // show the pause button
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
        }
        return super.onOptionsItemSelected(item)
    }



}