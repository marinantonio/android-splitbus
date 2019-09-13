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

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.staro.helpers.*
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = getString(R.string.pref_about)
            toolbar.setNavigationOnClickListener { finish() }
        }

        tv_app_version.text = BuildConfig.VERSION_NAME

        btn_changelog.setOnClickListener { openDialog(R.string.about_changelog_title, R.string.about_changelog_content) }
        btn_github.setOnClickListener { Utils().openUrl(this, GITHUB_URL) }
        btn_facebook.setOnClickListener { Utils().openUrl(this, FACEBOOK_URL) }
        btn_faq.setOnClickListener { openDialog(R.string.about_faq_title, R.string.about_faq_content) }
        btn_licence.setOnClickListener { openLicensesDialog() }
        btn_contact.setOnClickListener { Utils().reportIssue(this, "Kontakt") }
        btn_linkedin.setOnClickListener { Utils().openUrl(this, LINKEDIN_URL) }
        btn_website.setOnClickListener { Utils().openUrl(this, WEBSITE_URL) }
    }

    private fun openDialog(title: Int, content: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(title))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            alertDialog.setMessage(Html.fromHtml(getString(content), Html.FROM_HTML_MODE_LEGACY))
        } else {
            alertDialog.setMessage(Html.fromHtml(getString(content)))
        }
        alertDialog.setMessage(getString(content))
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
