package com.am.stbus.screens.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.networking.models.Information
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_information_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationListFragment : Fragment() {

    private val viewModel: InformationListViewModel by viewModel()
    private val informationListAdapter = InformationListAdapter(context) { onInformationClicked(it) }
    private val informationListObserver = Observer<List<Information>> { informationListAdapter.addEntireData(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_information_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).toolbar.title = getString(R.string.nav_information)

        viewModel.informationList.observe(this, informationListObserver)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = informationListAdapter
        }
    }

    private fun onInformationClicked(information: Information) {
        view?.findNavController()?.navigate(let {
            when (information.informationId) {
                0 -> InformationListFragmentDirections.actionInformationListFragmentToInformationNewsListFragment()
                1 -> InformationListFragmentDirections.actionInformationFragmentToInformationDetailFragment(information.informationTitle, information.informationDesc)
                else -> InformationListFragmentDirections.actionInformationFragmentToInformationDetailFragment(information.informationTitle, information.informationDesc)
            }
        })
    }

}
