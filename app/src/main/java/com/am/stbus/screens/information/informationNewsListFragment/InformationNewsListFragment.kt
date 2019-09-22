package com.am.stbus.screens.information.informationNewsListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.repositories.models.NewsListItem
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.information_news_list_fragment.*
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
            mainActivity.showLoadingView(it)
        })

        newsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = informationNewsListAdapter
        }
    }

    private fun onNewsListAdded(it: List<NewsListItem>) {
        Timber.i("onLoadingFinished")
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
