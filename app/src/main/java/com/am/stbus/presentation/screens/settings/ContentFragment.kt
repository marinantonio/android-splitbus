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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.common.extensions.toSpanned
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.fragment_favourites.toolbar
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ContentFragment : Fragment() {

    private val args: ContentFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_close)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        when (args.type) {
            FIRST_RUN_CONTENT -> updateViews(getString(R.string.content_first_run_toolbar), getString(R.string.content_first_run_title), getString(R.string.content_first_run_desc))
            UPDATE_APP_CONTENT -> updateViews(getString(R.string.content_update_toolbar), getString(R.string.content_update_title, generateChangelogTitle()), getString(R.string.content_update_desc))
            CHANGELOG_CONTENT -> updateViews(getString(R.string.about_changelog_toolbar), getString(R.string.content_update_title, generateChangelogTitle()), getString(R.string.content_update_desc))
            FAQ_CONTENT -> updateViews(getString(R.string.content_faq_toolbar), getString(R.string.content_faq_title), getString(R.string.content_faq_desc))
            LICENCES_CONTENT -> updateLicensesView()
        }
    }

    private fun updateViews(toolbarTitle: String, title: String?, content: String) {
        toolbar.title = toolbarTitle

        tv_welcome_title.text = title ?: ""
        tv_welcome_content.text = content.toSpanned()
    }

    private fun generateChangelogTitle(): String {
        val buildDate = Instant.ofEpochMilli(BuildConfig.BUILD_TIME.time).atZone(ZoneId.systemDefault()).toLocalDate()
        return "${BuildConfig.VERSION_NAME} - ${buildDate.format(DateTimeFormatter.ofLocalizedDate( FormatStyle.MEDIUM ))}"
    }

    private fun updateLicensesView() {
        toolbar.title = getString(R.string.content_licenses_toolbar)
        tv_welcome_title.isVisible = false
        web_view.loadUrl("file:///android_asset/licenses.html")
    }

    companion object{
        const val FIRST_RUN_CONTENT = 0
        const val UPDATE_APP_CONTENT = 1
        const val CHANGELOG_CONTENT = 2
        const val FAQ_CONTENT = 3
        const val LICENCES_CONTENT = 4
    }

}
