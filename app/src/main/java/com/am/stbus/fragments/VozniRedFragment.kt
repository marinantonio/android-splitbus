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

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.stbus.R
import kotlinx.android.synthetic.main.fragment_vozni_red.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

class VozniRedFragment: Fragment() {

    var naziv: String? = ""
    var web = ""
    var podrucje: Int? = 0
    var day: Int? = 0
    //var nedavno: Int? = 0
    var fetchUrl: String? = ""

    var lineNumber = ""
    var validfrom = ""
    var htmltekst = ""
    var napomenaTekst = ""

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
        //nesto cemo samo ka prominit
        //FetchDataFromUrl(activity).execute(podrucje, day)
        //vozni_red.text = naziv
        //tv_naslov.text = naziv
        var napomena = ""
        if (vozniNapomena != "") {
            napomena = getString(R.string.vozni_red_napomena) + " " + vozniNapomena
        }

        tv_vrijedi_od.text = vozniVrijedi
        tv_vozni_red.text = vozniRed
        tv_napomena.text = napomena

        //getString(R.string.vozni_red_napomena) $vozniNapomena


        //GetVozniRed().execute(fetchUrl, day.toString())
    }

/*    fun showProgress(loading_progress: RelativeLayout, show: Boolean, errorView: Boolean) {
        if(activity != null) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
            //val vozniRed: TextView? = vozni_red
            vozni_red.visibility = if (show) View.GONE else View.VISIBLE
            vozni_red.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    (if (vozni_red != null) {
                        vozni_red.visibility = if (show) View.GONE else View.VISIBLE
                    })
                }
            })


            loading_progress.visibility = if (show) View.VISIBLE else View.GONE
            loading_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loading_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        }
    }*/

    private inner class GetVozniRed: AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            //showLoading(1)
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val doc1 = Jsoup.connect(params[0]).timeout(15000).get()

                val elements11 = doc1.select(".c-vozni-red__search-select option:contains(" + naziv!!.toUpperCase() + ")")
                val slika = elements11.attr("value")


                val doc = Jsoup.connect("http://www.promet-split.hr" + slika).timeout(15000).get()


                val elements = doc.select("h3.c-vozni-red__line")
                for (e in elements) {
                    lineNumber = e.text()
                }

                val elements1 = doc.select("p.c-vozni-red__valid")
                for (e in elements1) {
                    validfrom = e.text()
                }

                val elements2 = doc.select("div.c-vozni-red-note__items")
                for (e in elements2) {
                    napomenaTekst = e.text()
                }

                //var arrayList: ArrayList<String>

                val arrayListRadni = ArrayList<String>()
                val arrayListSubota = ArrayList<String>()
                val arrayListNedjelja = ArrayList<String>()



                //TODO: Bitni dio, 0 je za radni dan, 1 je za subotu, 2 za ned
                //https://stackoverflow.com/questions/36315461/using-jsoup-to-get-data-from-first-column-of-table
                //val el = doc.select("table.c-vozni-red__table td:eq(1)")
                val nesto = params[1]
                val el1 = doc.select("table.c-vozni-red__table td:eq(0)")
                for (e in el1) {
                    arrayListRadni.add("\n" + e.text())
                }

                val el2 = doc.select("table.c-vozni-red__table td:eq(1)")
                for (e in el2) {
                    arrayListSubota.add("\n" + e.text())
                }

                val el3 = doc.select("table.c-vozni-red__table td:eq(2)")
                for (e in el3) {
                    arrayListNedjelja.add("\n" + e.text())
                }

                htmltekst = arrayListRadni.toString()

                Log.e("LOG", "arrayradni " + arrayListRadni.toString())
                Log.e("LOG", "arrayListSubota " + arrayListSubota.toString())
                Log.e("LOG", "arrayListNedjelja " + arrayListNedjelja.toString())


            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("error", "srusilo se zbog IO EXCEPTION-a")
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: HttpStatusException) {
                e.printStackTrace()
            }

            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //showLoading(0)
            updateUI()

            Log.e("html Text", htmltekst)
            Log.e("htmlTextLenght", htmltekst.length.toString())

        }
    }

    private fun updateUI() {
        if (htmltekst.length > 2) {
            //showLoading(0)
            tv_vrijedi_od.text = validfrom
            var kztext83: String = htmltekst.replace(",", "").replace(" ", "\t\t")
            //Log.e("new_s", kztext83)
            //var loginToken = getName().toString()
            kztext83 = kztext83.substring(1, kztext83.length - 1)

            tv_vozni_red.text = lineNumber + "\n" + kztext83
            //vozni_red.text = kztext83
            tv_napomena.text = "NAPOMENA:\n$napomenaTekst"
        } else {
            //showLoading(3)
            Log.e("Nije uspio load", "nikako")
        }

    }


  /*  private fun showLoading(viewState: Int) {
        // 0 je prikaz voznog reda, 1 je loading, 2 je error
        when (viewState) {
            0 -> {
                cl_vozni_red_container.visibility = View.VISIBLE
                snippet_loading.visibility = View.INVISIBLE
                snippet_error.visibility = View.INVISIBLE
            }
            1 -> {
                cl_vozni_red_container.visibility = View.INVISIBLE
                snippet_loading.visibility = View.VISIBLE
                snippet_error.visibility = View.INVISIBLE
            }
            2 -> {
                cl_vozni_red_container.visibility = View.GONE
                snippet_loading.visibility = View.GONE
                snippet_error.visibility = View.VISIBLE
            }
        }
    }*/

}




/*    private inner class FetchDataFromUrl(var context: Context?): AsyncTask<Int, Void, Void>() {
        init {
            this.context = context!!.applicationContext
        }

        override fun onPreExecute() {
            super.onPreExecute()
            changeLayouts(true, false)
        }

        override fun doInBackground(vararg p0: Int?): Void {
            val fetchUrl: String = when (podrucje) {
                1 -> "http://www.promet-split.hr/vozni-red/split/"
                2 -> "http://www.promet-split.hr/vozni-red/urbano-podrucje"
                3 -> "http://www.promet-split.hr/vozni-red/prigradsko-podrucje"
                4 -> "http://www.promet-split.hr/vozni-red/trogir/"
                5 -> "http://www.promet-split.hr/vozni-red/otok-solta/"
                else -> "http://www.promet-split.hr/vozni-red/split/"
            }

            try {
                val doc1 = Jsoup.connect(fetchUrl).timeout(15000).get()

                val elements11 = doc1.select(".c-vozni-red__search-select option:contains(" + naziv!!.toUpperCase() + ")")
                val slika = elements11.attr("value")


                val doc = Jsoup.connect("http://www.promet-split.hr" + slika).timeout(15000).get()

                val arrayList = ArrayList<String>()

                val elements = doc.select("h3.c-vozni-red__line")
                for (e in elements) {
                    lineNumber = e.text()
                }

                val elements1 = doc.select("p.c-vozni-red__valid")
                for (e in elements1) {
                    validfrom = e.text()
                }

                val elements2 = doc.select("div.c-vozni-red-note__items")
                for (e in elements2) {
                    napomenaTekst = e.text()
                }

                //var arrayList: ArrayList<String>

                //TODO: Bitni dio, 0 je za radni dan, 1 je za subotu, 2 za ned
                //https://stackoverflow.com/questions/36315461/using-jsoup-to-get-data-from-first-column-of-table
                //val el = doc.select("table.c-vozni-red__table td:eq(1)")

                val el = doc.select("table.c-vozni-red__table td:eq($day)")
                for (e in el) {
                    arrayList.add("\n" + e.text())
                }

                htmltekst = arrayList.toString()

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("error", "srusilo se zbog IO EXCEPTION-a")
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: HttpStatusException) {
                e.printStackTrace()
            }
            return
        }


        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.e("html Text", htmltekst)
            Log.e("htmlTextLenght", htmltekst.length.toString())
            if (vrijedi_od != null && htmltekst.length > 2) {
                showProgress(loading_layout, false)
                vrijedi_od.text = validfrom
                var kztext83: String = htmltekst.replace(",", "").replace(" ", "\t\t")
                //Log.e("new_s", kztext83)
                //var loginToken = getName().toString()
                kztext83 = kztext83.substring(1, kztext83.length - 1)

                vozni_red.text = lineNumber + "\n" + kztext83
                //vozni_red.text = kztext83
                napomena.text = "NAPOMENA:\n$napomenaTekst"
            } else {
                showProgress(loading_layout, false)
                Log.e("Nije uspio load", "nikako")
            }

        }
    }
} */