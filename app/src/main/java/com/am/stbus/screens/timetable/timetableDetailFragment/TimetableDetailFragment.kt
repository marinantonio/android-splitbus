package com.am.stbus.screens.timetable.timetableDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.am.stbus.R
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class TimetableDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TimetableDetailFragment()
    }

    private lateinit var viewModel: TimetableDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolbar.title = getString(R.string.nav_timetables)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimetableDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
