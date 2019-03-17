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

package com.am.stbus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.stbus.R
import kotlinx.android.synthetic.main.fragment_vozni_red.*

private val TAG = NovostiListFragment::class.java.simpleName

class VozniRedFragment: Fragment() {
    var vozniVrijedi = ""
    var vozniRed = ""
    var vozniNapomena = ""

    companion object {
        fun newInstance(vrijedi: String, vozniRed: String, napomena: String): VozniRedFragment {
            val fragment = VozniRedFragment()
            val bundle = Bundle()
            bundle.putString("vrijedi", vrijedi)
            bundle.putString("vozniRed", vozniRed)
            bundle.putString("napomena", napomena)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vozniVrijedi = arguments?.get("vrijedi").toString()
        vozniRed = arguments?.get("vozniRed").toString()
        vozniNapomena = arguments?.get("napomena").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vozni_red, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var napomena = ""
        if (vozniNapomena != "") {
            napomena = getString(R.string.vozni_red_napomena) + " " + vozniNapomena
        }

        tv_vrijedi_od.text = vozniVrijedi
        tv_vozni_red.text = vozniRed
        tv_napomena.text = napomena

    }

}