package com.am.stbus.screens.information.informationNewsListFragment.InformationNewsDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.am.stbus.R

class InformationNewsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = InformationNewsDetailFragment()
    }

    private lateinit var viewModel: InformationNewsDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.information_news_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InformationNewsDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
