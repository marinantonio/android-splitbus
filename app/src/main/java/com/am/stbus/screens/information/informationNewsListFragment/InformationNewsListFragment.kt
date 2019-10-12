package com.am.stbus.screens.information.informationNewsListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.repositories.models.NewsListItem
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.information_news_list_fragment.*
import kotlinx.android.synthetic.main.snippet_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class InformationNewsListFragment : Fragment() {

    private val viewModel: InformationNewsListViewModel by viewModel()
    private val informationNewsListAdapter = InformationNewsListAdapter(context) { onNewsItemClicked(it)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.information_news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.toolbar.title = getString(R.string.information_news_title)

        viewModel.newsList.observe(this, Observer<List<NewsListItem>> {
            onNewsListAdded(it)
        })

        viewModel.loading.observe(this, Observer<Boolean>{
            snippet_loading.isVisible = it
            rv_news_list.isVisible = !it
        })

        viewModel.error.observe(this, Observer<String> {
            handleErrorScreen(it)
        })

        rv_news_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = informationNewsListAdapter
        }
    }

    private fun handleErrorScreen(it: String?) {
        rv_news_list.isVisible = false
        snippet_loading.isVisible = false
        snippet_error.isVisible = true

        btn_promet.setOnClickListener {
            Timber.e("Promet!")
            // TODO
        }

        btn_error.setOnClickListener {
            Timber.e("Report error")
            // TODO
        }

    }

    private fun onNewsListAdded(it: List<NewsListItem>) {
        informationNewsListAdapter.clear()
        informationNewsListAdapter.addEntireData(it)
    }

    private fun onNewsItemClicked(it: NewsListItem) {
        view?.findNavController()?.navigate(InformationNewsListFragmentDirections
                .actionInformationNewsListFragmentToInformationNewsDetailFragment(
                        it.title,
                        it.date,
                        it.url
        ))
    }

}
