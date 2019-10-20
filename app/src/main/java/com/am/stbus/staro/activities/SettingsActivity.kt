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
import androidx.appcompat.app.AppCompatActivity
import com.am.stbus.R
import com.am.stbus.presentation.screens.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by Antonio Marin on 29.8.2018.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = getString(R.string.action_settings)
            toolbar.setNavigationOnClickListener { finish() }
        }

       supportFragmentManager.beginTransaction().replace(R.id.content_frame, SettingsFragment()).commit()
    }
}
