package com.am.stbus.screens.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.stbus.R
import com.am.stbus.common.InformationConstants.ID_GMAPS
import com.am.stbus.common.InformationConstants.ID_LATEST_NEWS
import com.am.stbus.common.InformationConstants.ID_TOURIST_INFO
import com.am.stbus.repositories.models.Information
import com.am.stbus.screens.MainActivity
import com.am.stbus.screens.gmaps.GmapsActivity
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
        when (information.informationId) {
            ID_LATEST_NEWS -> navigateToFragment(information)
            ID_TOURIST_INFO -> Toast.makeText(context, context!!.getText(R.string.error_still_not_finished), Toast.LENGTH_SHORT).show()
            ID_GMAPS -> startGmapsActivity()
            else -> InformationListFragmentDirections.actionInformationFragmentToInformationDetailFragment(information.informationTitle, information.informationDesc)
        }
    }

    private fun navigateToFragment(information: Information) {
        view?.findNavController()?.navigate(let {
            when (information.informationId) {
                ID_LATEST_NEWS -> InformationListFragmentDirections.actionInformationListFragmentToInformationNewsListFragment()
                //ID_TOURIST_INFO -> Toast.makeText(context, context!!.getText(R.string.error_still_not_finished), Toast.LENGTH_SHORT).show()
                else -> InformationListFragmentDirections.actionInformationFragmentToInformationDetailFragment(information.informationTitle, information.informationDesc)
            }
        })
    }

    private fun startGmapsActivity() {
        val intent = Intent(context, GmapsActivity::class.java)
        startActivity(intent)
    }


}
