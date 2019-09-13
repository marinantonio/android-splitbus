/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.am.stbus.staro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.staro.models.Informacija
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