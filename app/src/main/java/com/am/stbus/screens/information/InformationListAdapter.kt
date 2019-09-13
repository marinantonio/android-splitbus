package com.am.stbus.screens.information

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.networking.models.Information
import kotlinx.android.synthetic.main.item_row_information.view.*

class InformationListAdapter(val context: Context?,
                             var onClickListener: (Information) -> Unit
) : RecyclerView.Adapter<InformationListAdapter.NotificationsViewHolder>() {

    private var informationList = mutableListOf<Information>()

    fun addEntireData(notificationsData: List<Information>) {
        informationList.addAll(notificationsData)
        notifyDataSetChanged()
    }

    fun addItem(information: Information) {
        informationList.add(information)
    }

    fun clear() {
        informationList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_information, parent, false)
        return NotificationsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return informationList.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val information = informationList[position]

            itemView.apply {
                titleTextView.text = information.informationTitle
                descTextView.text = information.informationDesc
                setOnClickListener {
                    onClickListener(information)
                }
            }
        }

    }
}