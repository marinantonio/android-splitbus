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

package com.am.stbus.presentation.screens.information.informationNewsListFragment.informationNewsDetailFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.am.stbus.R
import com.am.stbus.common.Constants
import com.am.stbus.common.extensions.loadUrl
import com.am.stbus.common.extensions.toPx
import com.am.stbus.domain.models.NewsItem
import kotlinx.android.synthetic.main.fragment_information_news_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InformationNewsDetailFragment : Fragment() {

    private val viewModel: InformationNewsDetailViewModel by viewModel { parametersOf(args.newsUrl) }

    private val args: InformationNewsDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        tv_title.text = args.newsTitle
        tv_date.text = args.newsDate

        viewModel.newsItem.observe(viewLifecycleOwner, Observer<NewsItem> {
            onNewsLoaded(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer<String> {
            handleErrorScreen(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer<Boolean> {
            pb_loading.isVisible = it
            web_view.isVisible = !it
        })
    }

    private fun onNewsLoaded(it: NewsItem) {
        web_view.apply {
            setBackgroundColor(Color.TRANSPARENT)
            isHorizontalScrollBarEnabled = false
            loadDataWithBaseURL("file:///android_res/", formatNewsTextForWebView(it.newsItemContent), "text/html", "utf-8", null)
        }

        web_view.webViewClient  = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                requireContext().loadUrl(Constants.PROMET_URL + url.replace("file://", ""))
                return true
            }
        }

    }

    private fun formatNewsTextForWebView(newsContent: String): String {
        val textColor = "#" + Integer.toHexString(ContextCompat.getColor(requireContext(), R.color.colorText) and 0x00ffffff)
        val textFixedImageTags = newsContent.replace("src=\"/", "src=\"http://www.promet-split.hr/")
        return "<head>\n " +
                "</head>\n" +
                "<body>" +
                "<style type=\"text/css\">\n" +
                "body {margin: 0px ${6.toPx()}px 200px ${6.toPx()}px; font-family: \"Roboto\"; font-size: 15px; color:" + textColor + "; line-height: 15pt;}" +
                "</style>\n" +
                textFixedImageTags + "</body>\n" +
                "</html>"

    }

    private fun handleErrorScreen(errorMessage: String?) {
        web_view.loadDataWithBaseURL(
                "file:///android_res/",
                errorMessage,
                "text/html",
                "utf-8",
                null)
    }
}
