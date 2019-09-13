package com.am.stbus.screens.information.informationDetailFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.am.stbus.R
import com.am.stbus.screens.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_notification_detail.view.*

class InformationDetailFragment : Fragment() {

    val args: InformationDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).apply {
            toolbar.title = getString(R.string.nav_information)
        }

        view.tv_notification_detail_title.text = args.notificationTitle
        view.tv_notification_detail_msg.text = args.notificationMessage
        //val title = args.title

    }
}
