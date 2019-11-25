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
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.common.Constants.FACEBOOK_URL
import com.am.stbus.common.Constants.GITHUB_URL
import com.am.stbus.common.Constants.LINKEDIN_URL
import com.am.stbus.common.Constants.WEBSITE_URL
import com.am.stbus.common.extensions.toSpanned
import kotlinx.android.synthetic.main.activity_about.*
import org.koin.android.ext.android.inject

class AboutActivity : AppCompatActivity() {

    private val preferencesManager: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        tv_app_version.text = BuildConfig.VERSION_NAME

        btn_changelog.setOnClickListener { openDialog(R.string.about_changelog_title, R.string.about_changelog_content) }
        btn_github.setOnClickListener { loadUrl(GITHUB_URL) }
        btn_facebook.setOnClickListener { loadUrl(FACEBOOK_URL) }
        btn_faq.setOnClickListener { openDialog(R.string.about_faq_title, R.string.about_faq_content) }
        btn_licence.setOnClickListener { openLicensesDialog() }
        //btn_contact.setOnClickListener { Utils().reportIssue(this, "Kontakt") }
        btn_linkedin.setOnClickListener { loadUrl(LINKEDIN_URL) }
        btn_website.setOnClickListener { loadUrl(WEBSITE_URL) }
    }

    private fun loadUrl(url: String) {
        if (preferencesManager.getBoolean("open_urls", true)) {
            val customTabsIntent : CustomTabsIntent = buildCustomTabsIntent()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun buildCustomTabsIntent(): CustomTabsIntent {
        val intentBuilder = CustomTabsIntent.Builder()
        // Show the title
        intentBuilder.setShowTitle(true)
        // Set the color of Toolbar
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        return intentBuilder.build()
    }

    private fun openDialog(title: Int, content: Int) {
        val alertDialog = AlertDialog.Builder(this)
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
        val view = LayoutInflater.from(this).inflate(R.layout.snippet_dialog_licenses, nullParent) as WebView
        view.loadUrl("file:///android_asset/licenses.html")
        val alertDialog = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
        alertDialog.setView(view)
        alertDialog.setTitle(getString(R.string.about_licenses_title))
        alertDialog.setPositiveButton(getString(R.string.close)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }

}
