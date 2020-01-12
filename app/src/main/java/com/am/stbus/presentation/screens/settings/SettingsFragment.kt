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

package com.am.stbus.presentation.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.am.stbus.R
import com.am.stbus.presentation.screens.MainActivity
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

        // About fragment
        findPreference<Preference>("about")?.setOnPreferenceClickListener {
            view?.findNavController()?.navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutFragment())
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.toolbar.title = getString(R.string.nav_settings)
    }

}
