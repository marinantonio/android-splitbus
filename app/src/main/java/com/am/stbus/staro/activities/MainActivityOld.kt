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
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.am.stbus.R
import com.am.stbus.staro.fragments.InformacijeFragment
import com.am.stbus.staro.fragments.MainFragment
import com.am.stbus.staro.fragments.NovostiListFragment
import com.am.stbus.staro.fragments.VozniRedoviListFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_old.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivityOld: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var currentSelectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_old)
        setSupportActionBar(toolbar)
        displayView(R.id.nav_recent)
        updatePosition(0)

        val toggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, 0f) // disabled arrow animation
                updatePosition(currentSelectedPosition)
            }
        }

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun updatePosition(position: Int) {
        val menu = nav_view.menu
        menu.getItem(position).isChecked = true
    }

    private fun displayView(viewId: Int) {
        var fragment: androidx.fragment.app.Fragment? = null
        val args = Bundle()

        when (viewId) {

            R.id.nav_recent -> {
                currentSelectedPosition = 0
                fragment = MainFragment()
                title = getString(R.string.nav_favoriti)
            }

            R.id.nav_urban -> {
                currentSelectedPosition = 1
                fragment = VozniRedoviListFragment()
                title = getString(R.string.nav_grad_split)
                args.putInt("ARGUMENT_PODRUCJE", 1)
                fragment.setArguments(args)
            }

            R.id.nav_urban_area -> {
                currentSelectedPosition = 2
                fragment = VozniRedoviListFragment()
                title = getString(R.string.nav_urbano_podrucje)
                args.putInt("ARGUMENT_PODRUCJE", 2)
                fragment.setArguments(args)
            }

            R.id.nav_suburban -> {
                currentSelectedPosition = 3
                fragment = VozniRedoviListFragment()
                title = getString(R.string.nav_prigradske)
                args.putInt("ARGUMENT_PODRUCJE", 3)
                fragment.setArguments(args)
            }

            R.id.nav_trogir_solta -> {
                currentSelectedPosition = 4
                fragment = VozniRedoviListFragment()
                title = getString(R.string.nav_trogir_solta)
                args.putInt("ARGUMENT_PODRUCJE", 4)
                fragment.setArguments(args)
            }

            R.id.nav_novosti -> {
                currentSelectedPosition = 5
                fragment = NovostiListFragment()
                title = getString(R.string.title_novosti)
            }

            R.id.nav_informacije -> {
                currentSelectedPosition = 6
                fragment = InformacijeFragment()
                title = getString(R.string.title_informacije)
            }

            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)

                ///currentSelectedPosition = 7
                //fragment = ObavijestFragment()

                //title = getString(R.string.action_settings)
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            //ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
            ft.commit()
        }

        // set the toolbar title
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayView(item.itemId)
        return true
    }

    override fun onStop() {
        super.onStop()
        drawer_layout.closeDrawer(GravityCompat.START)
    }

}