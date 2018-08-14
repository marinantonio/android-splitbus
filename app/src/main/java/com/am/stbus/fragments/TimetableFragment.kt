package com.am.stbus.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.am.stbus.R
import kotlinx.android.synthetic.main.fragment_timetable.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

class TimetableFragment: Fragment() {

    var naziv: String? = ""
    var web = ""
    var podrucje: Int? = 0
    var day: Int? = 0
    //var nedavno: Int? = 0

    var lineNumber = ""
    var validfrom = ""
    var htmltekst = ""
    var napomenaTekst = ""


    companion object {
        fun newInstance(naziv: String, web: Int, gmaps: Int, podrucje: Int, day: Int): TimetableFragment {
            val fragment = TimetableFragment()
            val bundle = Bundle()
            bundle.putString("naziv", naziv)
            bundle.putInt("web", web)
            bundle.putInt("gmaps", gmaps)
            bundle.putInt("podrucje", podrucje)
            bundle.putInt("day", day)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        naziv = arguments?.get("naziv").toString()
        web = arguments?.get("web").toString()
        podrucje = arguments?.getInt("podrucje")
        day = arguments?.getInt("day")
        //nedavno = arguments?.getInt("nedavno")

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //nesto cemo samo ka prominit
        //showProgress(loading_progress, true)
        //vozni_red.setNaziv("blaa")
        FetchDataFromUrl(activity, vozni_red, loading_progress, web, podrucje, day).execute()
        vozni_red.text = naziv
        tv_naslov.text = naziv
        Log.e("TAG", naziv)
    }

    fun showProgress(loading_progress: RelativeLayout, show: Boolean) {
        if(activity != null) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
            //val vozniRed: TextView? = vozni_red
            if (vozni_red != null) {
                vozni_red.visibility = if (show) View.GONE else View.VISIBLE
                vozni_red.animate().setDuration(shortAnimTime.toLong()).alpha(
                        (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        (if (vozni_red != null) {
                            vozni_red.visibility = if (show) View.GONE else View.VISIBLE
                        })
                    }
                })
            }

            loading_progress.visibility = if (show) View.VISIBLE else View.GONE
            loading_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                    (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loading_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
        }
    }

    inner class FetchDataFromUrl(var context: Context?, val vozni_red: TextView, val loading_layout: RelativeLayout,
                                 val web: String, val podrucje: Int?, val day: Int?): AsyncTask<Void, Void, String>() {
        init {
            this.context = context!!.applicationContext
        }


        override fun onPreExecute() {
            showProgress(loading_progress, true)
            //mozemo ovdje show progress il nesto
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Void?): String {
            //http://www.promet-split.hr/vozni-red/split/linijaid/1381"
/*            val fetchUrl: String = when (podrucje) {
                4 -> "http://www.promet-split.hr/vozni-red/trogir/linijaid/$web"
                5 -> "http://www.promet-split.hr/vozni-red/otok-solta/linijaid/$web"
                else -> "http://www.promet-split.hr/vozni-red/split/linijaid/$web"
            }*/

            val fetchUrl: String = when (podrucje) {
                1 -> "http://www.promet-split.hr/vozni-red/split/"
                2 -> "http://www.promet-split.hr/vozni-red/urbano-podrucje"
                3 -> "http://www.promet-split.hr/vozni-red/prigradsko-podrucje"
                4 -> "http://www.promet-split.hr/vozni-red/trogir/"
                5 -> "http://www.promet-split.hr/vozni-red/otok-solta/"
                else -> "http://www.promet-split.hr/vozni-red/split/"
            }

            try {
                Log.e("LOGTAG", "fetchUrl" + fetchUrl)
                val doc1 = Jsoup.connect(fetchUrl).timeout(15000).get()

                val elements11 = doc1.select(".c-vozni-red__search-select option:contains("+naziv!!.toUpperCase()+")")
                val slika = elements11.attr("value")
                Log.e("LOGTAG", slika.toString())



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
                    Log.e("NAPOMENA", e.text())
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

            return htmltekst
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //Log.e("htmlTextLenght", htmltekst.length.toString())
            if (vrijedi_od != null && htmltekst.length > 1) {
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
                Log.v("Nije uspio load", "nikako")
            }

        }
    }

}