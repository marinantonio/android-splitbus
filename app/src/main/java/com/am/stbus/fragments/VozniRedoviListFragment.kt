/*
 * MIT License
 *
 * Copyright (c) 2013 - 2018 Antonio Marin
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.adapters.VozniRedAdapter
import com.am.stbus.helpers.DatabaseHandler
import kotlinx.android.synthetic.main.fragment_vozni_redovi_list.view.*

/**
 * Created by Antonio Marin  on 10.3.2018.
 */
class VozniRedoviListFragment : Fragment() {

    private lateinit var vozniRedAdapter: VozniRedAdapter
    private lateinit var db: DatabaseHandler
    private lateinit var rootView: View
    private var args: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_vozni_redovi_list, container, false)
        val areaArg = arguments
        args = areaArg!!.getInt("ARGUMENT_PODRUCJE", 0)

        rootView.recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rootView.recycler_view.setHasFixedSize(true)


        db = DatabaseHandler(activity)
        // Favoriti
        vozniRedAdapter = VozniRedAdapter(context, db.gradSplit, args)
        rootView.recycler_view.adapter = vozniRedAdapter

        when (args) {
            1 -> {
                vozniRedAdapter = VozniRedAdapter(context, db.gradSplit, args)
                rootView.recycler_view.adapter = vozniRedAdapter
            }
            2 -> {
                vozniRedAdapter = VozniRedAdapter(context, db.urbanoPodrucje, args)
                rootView.recycler_view.adapter = vozniRedAdapter
            }
            3 -> {
                vozniRedAdapter = VozniRedAdapter(context, db.prigradskoPodrucje, args)
                rootView.recycler_view.adapter = vozniRedAdapter
            }
            4 -> {
                vozniRedAdapter = VozniRedAdapter(context, db.trogirSolta, args)
                rootView.recycler_view.adapter = vozniRedAdapter
            }
            else -> {
                vozniRedAdapter = VozniRedAdapter(context, db.nedavnoList, args)
                rootView.recycler_view.adapter = vozniRedAdapter
            }
        }

        return rootView
    }
}
