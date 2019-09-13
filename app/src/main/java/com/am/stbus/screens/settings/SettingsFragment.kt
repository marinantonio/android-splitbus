package com.am.stbus.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.am.stbus.R
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class SettingsFragment : PreferenceFragmentCompat() {

    private var listener: SharedPreferences.OnSharedPreferenceChangeListener? = null
    private lateinit var mainActivity: MainActivity

    override fun onResume() {
        super.onResume()
        mainActivity = activity as MainActivity
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        mainActivity = activity as MainActivity
        addPreferencesFromResource(R.xml.preferences)

        // Mijenjanje teme u postavkama
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, string ->
            if (string == "darkMode") {
                mainActivity.setupAppTheme()
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.toolbar.title = getString(R.string.nav_setttings)
    }

}
