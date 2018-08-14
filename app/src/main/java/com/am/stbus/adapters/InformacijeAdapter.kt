package com.am.stbus.adapters;

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.stbus.R
import com.am.stbus.models.Informacija
import kotlinx.android.synthetic.main.item_row_informacije.view.*

class InformacijeAdapter constructor (private var item: ArrayList<Informacija>, private val clickListener: (Informacija) -> Unit): RecyclerView.Adapter<InformacijeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformacijeAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_row_informacije, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InformacijeAdapter.ViewHolder, position: Int) {
        holder.bindItems(item[position], clickListener)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return item.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(informacija: Informacija, clickListener: (Informacija) -> Unit) {
            itemView.tv_naslov.text = informacija.naslov
            itemView.tv_sadrzaj.text = informacija.sadrzaj
            itemView.setOnClickListener { clickListener(informacija)}
        }
    }

}