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

package com.am.stbus.staro.adapters;

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.staro.activities.NovostActivity
import com.am.stbus.staro.models.Novost
import kotlinx.android.synthetic.main.item_row_novosti.view.*

class NovostiAdapter constructor (context: Context?, private var item: ArrayList<Novost>): RecyclerView.Adapter<NovostiAdapter.ViewHolder>() {

    //private val context: Context? = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovostiAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_novosti, parent, false)

        return ViewHolder(v).listen { pos, type ->
            val intent = Intent(parent.context, NovostActivity::class.java)
            intent.putExtra("naslov", item[pos].naslov)
            intent.putExtra("datum", item[pos].datum)
            intent.putExtra("url", item[pos].url)
            parent.context.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: NovostiAdapter.ViewHolder, position: Int) {
        holder.bindItems(item[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return item.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(novost: Novost) {
            itemView.tv_naslov.text = novost.naslov
            itemView.tv_datum.text = novost.datum
            itemView.tv_sazetak.text = novost.sazetak
            //itemView.item_status.naziv=user.preuzeto
            //itemView.iv_name.setImageResource(user.image)
           // itemView.item_menu.setOnClickListener { view -> popupMenu(view, position) }
        }
    }

    fun onlineStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }
}