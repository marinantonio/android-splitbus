package com.am.stbus.screens.information

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.common.InformationConstants.TYPE_HEADER
import com.am.stbus.common.InformationConstants.TYPE_ITEM
import com.am.stbus.repositories.models.Information
import kotlinx.android.synthetic.main.item_row_information_header.view.*
import kotlinx.android.synthetic.main.item_row_information_item.view.*


class InformationListAdapter(val context: Context?,
                             var onClickListener: (Information) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var informationList = mutableListOf<Information>()
    private val positionsThatShouldHideDivider: List<Int> = listOf(1, 7, 12)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_HEADER -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_information_header, parent, false))
            TYPE_ITEM -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_information_item, parent, false))
            else -> throw IllegalArgumentException("Wrong type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(position)
            }
            is HeaderViewHolder -> {
                holder.bind(position)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return informationList[position].viewType
    }

    override fun getItemCount(): Int {
        return informationList.size
    }


    inner class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val information = informationList[position]

            itemView.apply {
                tv_category.text = information.informationTitle
            }
        }
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val information = informationList[position]

            itemView.apply {
                tv_title.text = information.informationTitle
                tv_desc.text = information.informationDesc

                view.isVisible = !positionsThatShouldHideDivider.contains(position)

                setOnClickListener {
                    onClickListener(information)
                }
            }
        }
    }

}