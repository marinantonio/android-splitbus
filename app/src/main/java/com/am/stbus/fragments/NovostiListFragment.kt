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

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.adapters.NovostiAdapter
import com.am.stbus.helpers.Utils
import com.am.stbus.models.Novost
import com.am.stbus.helpers.*
import kotlinx.android.synthetic.main.fragment_novosti_list.view.*
import kotlinx.android.synthetic.main.snippet_error.view.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

private val TAG = NovostiListFragment::class.java.simpleName

class NovostiListFragment : Fragment() {

    /*
     TODO: Prepisat ovaj dio koda, mislim da je stariji kod
     VozniRedActivity ima taj kod
      */
    private lateinit var rootView: View
    private var data = ArrayList<Novost>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_novosti_list, container, false)
        rootView.rv_novosti_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        GetNovostiList(activity).execute()

        rootView.btn_error.setOnClickListener { Utils().reportIssue(context!!, "Novosti List") }
        rootView.btn_promet.setOnClickListener { Utils().openUrl(context!!, PROMET_URL) }

        return rootView
    }

    inner class GetNovostiList(var context: Context?): AsyncTask<Void, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            showLoading(1)
        }

        override fun doInBackground(vararg p0: Void?): String {
            try {
                val doc = Jsoup.connect(PROMET_NOVOSTI_URL).timeout(15000).get()

                ArrayList<String>()
                var url: String
                var naslov: String
                var datum: String
                var sazetak: String = ""
                var count = 0

                val elements = doc.select("h3.c-article-card__title > a")
                //val elements = doc.select("div.c-article-list__item c-article-list__item--card-layout")
                for (e in elements) {
                    //val link = doc.select("td.topic.starter > a")
                    url = e.attr("href")
                    naslov = e.text()

                    val elements1 = doc.select("div.c-article-card__date")[count]
                    datum = elements1.text()
                    //datum = e.attr("div.c-article-card__date")
                    //datum = e.allElements[4].text()

                    val elements2 = doc.select("div.c-article-card__content")[count]
                    val size =  elements2.select("div.c-article-card__summary").size

                    sazetak = if (size > 0) {
                        elements2.child(2).text()
                    } else {
                        ""
                    }

                    val novost = Novost(url, naslov, datum, sazetak)
                    data.add(novost)
                    count += 1
                }


            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "srusilo se zbog IO EXCEPTION-a")
            } catch (e: NullPointerException) {
                e.printStackTrace()
                Log.e(TAG, "printStackTrace")
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                Log.e(TAG, "HttpStatusException")
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Inicijalizacija adaptera
            val novostiAdapter = NovostiAdapter(context, data)
            rootView.rv_novosti_list.adapter = novostiAdapter

            // Sakrij loading
            if (novostiAdapter.itemCount > 0) {
                showLoading(0)
            } else {
                showLoading(2)
            }
        }
    }


    private fun showLoading(viewState: Int) {
        // 0 je prikaz rv-a s novostima, 1 je loading, 2 je error
        when (viewState) {
            0 -> {
                rootView.rv_novosti_list.visibility = View.VISIBLE
                rootView.snippet_loading.visibility = View.GONE
                rootView.snippet_error.visibility = View.GONE
            }
            1 -> {
                rootView.rv_novosti_list.visibility = View.GONE
                rootView.snippet_loading.visibility = View.VISIBLE
                rootView.snippet_error.visibility = View.GONE
            }
            2 -> {
                rootView.rv_novosti_list.visibility = View.GONE
                rootView.snippet_loading.visibility = View.GONE
                rootView.snippet_error.visibility = View.VISIBLE
            }
        }
    }

}