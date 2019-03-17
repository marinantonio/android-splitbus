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

package com.am.stbus.activities

import android.os.AsyncTask
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.am.stbus.R
import com.am.stbus.helpers.Utils
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_novost.*
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

class NovostActivity : AppCompatActivity() {
    private val TAG = NovostActivity::class.java.simpleName

    private lateinit var url: String
    private lateinit var urlSLike: String
    private lateinit var vijest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novost)
        setSupportActionBar(toolbar)

        app_bar.setExpanded(false)
        toolbar_layout.isTitleEnabled = false

        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { finish() }
        }

        val naslov = intent.getStringExtra("naslov")
        val datum = intent.getStringExtra("datum")
        url = intent.getStringExtra("url")

        tv_naslov.text = naslov
        tv_datum.text = datum

        if (Utils().onlineStatus(this)) {
            GetNovost().execute()
        }
    }


    inner class GetNovost : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            pb_loading.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Void?): String {

            try {
                val doc = Jsoup.connect(url).timeout(15000).get()
                val elementsNormalniText = doc.select("[class=c-article-detail__body c-text-body]")

                val elementsPosebniText = doc.select("[class=EDN_article_content]")

                val elementsSlika = doc.select("[class=c-gallery-fotorama__item c-gallery-fotorama__item--image js-gallery-unwrap]")
                val slika = elementsSlika.attr("data-img")

                urlSLike = "http://www.promet-split.hr$slika"

                Log.e(TAG, "elements.size " + elementsNormalniText.size + " elements " + elementsNormalniText.text())
                Log.e(TAG, "elementsText " + elementsPosebniText.size + " elementsText " + elementsPosebniText.text())

                if (elementsNormalniText.size > 0) {
                    vijest = elementsNormalniText.html()
                } else {
                    vijest = elementsPosebniText.html()
                }

            }

            catch (e: IOException) {
                e.printStackTrace()
                Log.e("error", "srusilo se zbog IO EXCEPTION-a")
            } catch (e: NullPointerException) {
                e.printStackTrace()
                Log.e("error", "printStackTrace")
            } catch (e: HttpStatusException) {
                e.printStackTrace()
                Log.e("error", "HttpStatusException")
            }
            return " "
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pb_loading.visibility = View.INVISIBLE

            loadImage(urlSLike)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tv_vijest.text = Html.fromHtml(vijest, Html.FROM_HTML_MODE_LEGACY)
            } else {
                tv_vijest.text = Html.fromHtml(vijest)
            }
        }

    }

    private fun loadImage(urlSlike : String) {
        if (urlSlike != "http://www.promet-split.hr") {
            Glide.with(this).load(urlSlike).into(iv_slika)
            app_bar.setExpanded(true)
        } else {
            ViewCompat.setNestedScrollingEnabled(nsv_container, false)
            val params = app_bar.layoutParams as CoordinatorLayout.LayoutParams
            if (params.behavior == null)
                params.behavior = AppBarLayout.Behavior()
            val behaviour = params.behavior as AppBarLayout.Behavior
            behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return false
                }
            })
        }
    }

}
