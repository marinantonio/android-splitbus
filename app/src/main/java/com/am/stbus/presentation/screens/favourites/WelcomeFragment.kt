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

package com.am.stbus.presentation.screens.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.am.stbus.BuildConfig
import com.am.stbus.R
import com.am.stbus.common.extensions.toSpanned
import com.am.stbus.presentation.screens.favourites.FavouritesFragment.Companion.FIRST_RUN_CONTENT
import com.am.stbus.presentation.screens.favourites.FavouritesFragment.Companion.UPDATE_APP_CONTENT
import kotlinx.android.synthetic.main.fragment_favourites.toolbar
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    private val args: WelcomeFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
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
            FIRST_RUN_CONTENT -> {
                toolbar.title = getString(R.string.welcome_first_run_toolbar)
                tv_welcome_title.text = getString(R.string.welcome_first_run_title)
                tv_welcome_content.text = getString(R.string.welcome_first_run_desc).toSpanned()

            }
            UPDATE_APP_CONTENT -> {
                toolbar.title = getString(R.string.welcome_update_toolbar)
                tv_welcome_title.text = getString(R.string.welcome_update_title, BuildConfig.VERSION_NAME)
                tv_welcome_content.text = getString(R.string.welcome_update_desc).toSpanned()
            }
        }
    }

}
