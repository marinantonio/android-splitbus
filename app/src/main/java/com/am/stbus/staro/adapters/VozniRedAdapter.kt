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

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.presentation.screens.gmaps.GmapsActivity
import com.am.stbus.staro.activities.VozniRedActivity
import com.am.stbus.staro.helpers.DatabaseHandler
import com.am.stbus.staro.helpers.Utils
import com.am.stbus.staro.models.VozniRed
import kotlinx.android.synthetic.main.item_row_vozni_red.view.*

class VozniRedAdapter constructor (private val context: Context?,
                                   private var items: ArrayList<VozniRed>,
                                   private var city: Int) : RecyclerView.Adapter<VozniRedAdapter.ViewHolder>() {

    private lateinit var db: DatabaseHandler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_vozni_red, parent, false)


        return ViewHolder(v).listen { pos, type ->
            if (Utils().onlineStatus(parent.context)) {
                Log.e("itemsweb", (items[pos].web.toString()))
                db = DatabaseHandler(parent.context)
                val naziv = items[pos].naziv
                val web = items[pos].web
                val gmaps = items[pos].gmaps
                val podrucje = items[pos].tip
                val nedavno = items[pos].nedavno

                val intent = Intent(context, VozniRedActivity::class.java)
                intent.putExtra("naziv", naziv)
                intent.putExtra("web", web)
                intent.putExtra("gmaps", gmaps)
                intent.putExtra("podrucje", podrucje)
                intent.putExtra("nedavno", nedavno)
                parent.context.startActivity(intent)
            } else {
                Toast.makeText(parent.context, "Potreban je internet pristup!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position], context)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: VozniRed, context: Context?) {

            val gmaps = user.gmaps
            val line = user.naziv

            itemView.tv_line_number.text = gmaps
            itemView.tv_line_name.text = line

            itemView.iv_menu.setOnClickListener { view -> popupMenu(context, view, gmaps) }
        }

        private fun popupMenu(context: Context?, view: View, gmaps: String) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.fragment_recycler_view)

            popupMenu.setOnMenuItemClickListener {

                val intent = Intent(context, GmapsActivity::class.java)
                intent.putExtra("gmaps", gmaps)
                context!!.startActivity(intent)
                true
            }
            popupMenu.show()
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }
}
