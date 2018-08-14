package com.am.stbus.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.am.stbus.R
import com.am.stbus.adapters.VozniRedAdapter
import com.am.stbus.helpers.DatabaseHandler
import com.am.stbus.models.VozniRed
import kotlinx.android.synthetic.main.fragment_recyclerview.view.*

/**
 * Created by marin on 10.3.2018..
 */

class RecyclerViewFragment : Fragment() {

    private lateinit var db: DatabaseHandler
    private lateinit var rootView: View
    private var index: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        val args = arguments
        index = args!!.getInt("ARGUMENT_PODRUCJE", 0)

        rootView.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val users = ArrayList<VozniRed>()
        /*  users.add(VozniRed("1",2,3))
        users.add(VozniRed("naziv dva",3,5))*/

        db = DatabaseHandler(activity)
        val obj_adapter = VozniRedAdapter(context, db.gradSplit, index)
        rootView.recycler_view.adapter = obj_adapter


        when (index) {
            1 -> {
                val obj_adapter = VozniRedAdapter(context, db.gradSplit, index)
                rootView.recycler_view.adapter = obj_adapter
//            Log.e("ITEMS RV", db.gradSplit[1].naziv)
            }
            2 -> {
                //val obj_adapter = VozniRedAdapter(context, users)
                val obj_adapter = VozniRedAdapter(context, db.urbanoPodrucje, index)
                rootView.recycler_view.adapter = obj_adapter
            }
            3 -> {
                val obj_adapter = VozniRedAdapter(context, db.prigradskoPodrucje, index)
                rootView.recycler_view.adapter = obj_adapter
            }
            4 -> {
                val objectAdapter = VozniRedAdapter(context, db.trogirSolta, index)
                rootView.recycler_view.adapter = objectAdapter
            }
            else -> {
                val obj_adapter = VozniRedAdapter(context, db.nedavnoList, index)
                rootView.recycler_view.adapter = obj_adapter
            }
        }

        return rootView
        //return inflater.inflate(R.layout.fragment_recyclerview, container, false)

    }

/*    override fun onResume() {
        super.onResume()
        when (index) {
            1 -> {
                val obj_adapter = VozniRedAdapter(context, db.gradSplit, index)
                rootView.recycler_view.adapter = obj_adapter
//            Log.e("ITEMS RV", db.gradSplit[1].naziv)
            }
            2 -> {
                //val obj_adapter = VozniRedAdapter(context, users)
                val obj_adapter = VozniRedAdapter(context, db.urbanoPodrucje, index)
                rootView.recycler_view.adapter = obj_adapter
            }
            3 -> {
                val obj_adapter = VozniRedAdapter(context, db.prigradskoPodrucje, index)
                rootView.recycler_view.adapter = obj_adapter
            }
            4 -> {
                val objectAdapter = VozniRedAdapter(context, db.trogirSolta, index)
                rootView.recycler_view.adapter = objectAdapter
            }
            else -> {
                val obj_adapter = VozniRedAdapter(context, db.nedavnoList, index)
                rootView.recycler_view.adapter = obj_adapter
            }
        }
    }*/


}
