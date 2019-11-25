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

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.common.Constants
import com.am.stbus.common.extensions.loadEmailReport
import com.am.stbus.common.extensions.toSpanned
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.fragment_favourites.toolbar
import org.koin.android.ext.android.inject

class AboutFragment : Fragment() {

    private val preferencesManager: SharedPreferences by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        tv_app_version.text = BuildConfig.VERSION_NAME

        btn_changelog.setOnClickListener { openDialog(R.string.about_changelog_title, R.string.about_changelog_content) }
        btn_github.setOnClickListener { loadUrl(Constants.GITHUB_URL) }
        btn_facebook.setOnClickListener { loadUrl(Constants.FACEBOOK_URL) }
        btn_faq.setOnClickListener { openDialog(R.string.about_faq_title, R.string.about_faq_content) }
        btn_licence.setOnClickListener { openLicensesDialog() }
        btn_contact.setOnClickListener { requireContext().loadEmailReport("", "Kontakt") }
        btn_linkedin.setOnClickListener { loadUrl(Constants.LINKEDIN_URL) }
        btn_website.setOnClickListener { loadUrl(Constants.WEBSITE_URL) }
    }

    private fun loadUrl(url: String) {
        if (preferencesManager.getBoolean("open_urls", true)) {
            val customTabsIntent : CustomTabsIntent = buildCustomTabsIntent()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(intent)
        }
    }

    private fun buildCustomTabsIntent(): CustomTabsIntent {
        val intentBuilder = CustomTabsIntent.Builder()
        // Show the title
        intentBuilder.setShowTitle(true)
        // Set the color of Toolbar
        intentBuilder.setToolbarColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        return intentBuilder.build()
    }

    private fun openDialog(title: Int, content: Int) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(title))
        alertDialog.setMessage(getString(content).toSpanned())
        alertDialog.setPositiveButton(getString(R.string.close)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }

    /*
     Ovaj dialog je drukciji jer koristi WebView s HTML stranicom kako bi licence lijepo izgledale :)
     https://www.bignerdranch.com/blog/open-source-licenses-and-android/
     */
    private fun openLicensesDialog() {
        val nullParent: ViewGroup? = null // Nema parenta
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.snippet_dialog_licenses, nullParent) as WebView
        view.loadUrl("file:///android_asset/licenses.html")
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        alertDialog.setView(view)
        alertDialog.setTitle(getString(R.string.about_licenses_title))
        alertDialog.setPositiveButton(getString(R.string.close)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }



}
