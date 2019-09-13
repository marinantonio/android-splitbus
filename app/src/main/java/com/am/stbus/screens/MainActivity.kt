package com.am.stbus.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.am.stbus.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val rootFragments: IntArray =
            intArrayOf(R.id.favouriteFragment, R.id.timetableListFragment, R.id.informationListFragment, R.id.settingsFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        setupAppTheme()

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
        } else {
            nav_view.visibility = View.GONE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
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
