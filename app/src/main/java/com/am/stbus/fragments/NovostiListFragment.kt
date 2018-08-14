package com.am.stbus.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.am.stbus.R
import com.am.stbus.adapters.NovostiAdapter
import com.am.stbus.models.Novost
import kotlinx.android.synthetic.main.fragment_novosti_list.view.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

class NovostiListFragment : Fragment() {

    private lateinit var rootView: View
    private var data = ArrayList<Novost>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_novosti_list, container, false)
        rootView.rv_novosti_list.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        GetNovostiList(activity).execute()

        return rootView
    }

    inner class GetNovostiList(var context: Context?): AsyncTask<Void, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            loadingView(true)
        }

        override fun doInBackground(vararg p0: Void?): String {
            val fetchUrl = "http://www.promet-split.hr/obavijesti"

            try {
                val doc = Jsoup.connect(fetchUrl).timeout(15000).get()

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
                Log.e("error", "srusilo se zbog IO EXCEPTION-a")
            } catch (e: NullPointerException) {
                e.printStackTrace()
                Log.e("error", "printStackTrace")
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                Log.e("error", "HttpStatusException")
            }
            return fetchUrl
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loadingView(false)
            // load recyclerView
            val novostiAdapter = NovostiAdapter(context, data)
            rootView.rv_novosti_list.adapter = novostiAdapter

        }

    }

    private fun loadingView(boolean: Boolean){
        if (boolean) {
            rootView.rv_novosti_list.visibility = View.GONE
            rootView.rl_loading.visibility = View.VISIBLE
        } else {
            rootView.rv_novosti_list.visibility = View.VISIBLE
            rootView.rl_loading.visibility = View.GONE
        }
    }


}