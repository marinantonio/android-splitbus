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

package com.am.stbus.presentation.screens

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.am.stbus.R
import kotlinx.android.synthetic.main.activity_main.*
import android.view.WindowManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.am.stbus.common.extensions.systemUiVisibilityFullScreen


class MainActivity : AppCompatActivity() {

    //private val sharedViewModel by viewModel<TimetablesSharedViewModel>()

    private val rootFragments: IntArray =
            intArrayOf(R.id.favouriteFragment, R.id.timetablesFragment, R.id.informationListFragment, R.id.settingsFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAppTheme()

        container.systemUiVisibilityFullScreen()

        val navigationController: NavController = findNavController(R.id.nav_host_fragment)

        // Za skrivanje bottomNavBara
        navigationController.addOnDestinationChangedListener(onDestinationChangedListener)

        // This is extremely cool, link bottomNavigationBar with Navigation Controller
        // Because we link them there's no need for BottomNavigationView.onNavigationItemSelectedListener
        // NOTE: id's must same inside nav_graph.xml and bottom_nav_menu.xml
        NavigationUI.setupWithNavController(nav_view, navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val onDestinationChangedListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        if (rootFragments.contains(destination.id)) {
            nav_view.visibility = View.VISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
            setupWhiteNavigationNavigationBarColor(true)
        } else {
            nav_view.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            setupWhiteNavigationNavigationBarColor(false)
        }

        app_bar.isVisible = destination.id == R.id.settingsFragment
    }

    private fun setupWhiteNavigationNavigationBarColor(colored: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (colored) {
                window.navigationBarColor = ContextCompat.getColor(this, R.color.colorSystemNavigationBackground)
            } else {
                window.navigationBarColor = ContextCompat.getColor(this, R.color.prozirnaAndroid)
            }
        }

    }

    fun setupAppTheme() {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        when (preference.getString("darkMode", "1")) {
            "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "3" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}