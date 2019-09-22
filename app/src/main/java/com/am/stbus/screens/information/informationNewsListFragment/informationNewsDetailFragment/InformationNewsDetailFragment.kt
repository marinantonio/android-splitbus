package com.am.stbus.screens.information.informationNewsListFragment.informationNewsDetailFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.am.stbus.R
import com.am.stbus.repositories.models.NewsItem
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.information_news_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class InformationNewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = InformationNewsDetailFragment()
    }

    val args: InformationNewsDetailFragmentArgs by navArgs()

    private val viewModel: InformationNewsDetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.information_news_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.toolbar.title = getString(R.string.information_news_title)

        tv_title.text = args.newsTitle
        tv_date.text = args.newsDate

        viewModel.fetchAndPopulateNewsItem(args.newsUrl)

        viewModel.newsItem.observe(this, Observer<NewsItem> {
            onNewsLoaded(it)
        })

        viewModel.loading.observe(this, Observer<Boolean> {
            pb_loading.isVisible = it
            web_view.isVisible = !it
        })
    }

    private fun onNewsLoaded(it: NewsItem) {
        web_view.apply {
            setBackgroundColor(Color.TRANSPARENT)
            isHorizontalScrollBarEnabled = false
            //loadData(formatWebViewText(it.newsItemContent), "text/html", null)
            loadDataWithBaseURL("file:///android_res/", formatNewsTextForWebView(it.newsItemContent), "text/html", "utf-8", null)
        }
    }

    private fun formatNewsTextForWebView(newsContent: String): String {
        val textColor = "#" + Integer.toHexString(ContextCompat.getColor(context!!, R.color.colorText) and 0x00ffffff)
        val textFixedImageTags = newsContent.replace("src=\"/", "src=\"http://www.promet-split.hr/")
        return "<head>\n " +
                "</head>\n" +
                "<body>" +
                "<style type=\"text/css\">\n" +
                "body {font-family: \"Roboto\"; font-size: 15px; color:" + textColor + "; line-height: 15pt;}" +
                "</style>\n" +
                textFixedImageTags + "</body>\n" +
                "</html>"

    }

}
