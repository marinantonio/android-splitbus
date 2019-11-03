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

package com.am.stbus.presentation.screens.timetables.timetablesListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.common.TimetablesData
import com.am.stbus.domain.models.Timetable
import kotlinx.android.synthetic.main.item_row_timetable.view.*

class TimetablesListAdapter(val context: Context?,
                            var onClickListener: (Timetable) -> Unit,
                            var onClickFavourites: (Int, Timetable) -> Unit,
                            var onClickMenuGmaps: (Timetable) -> Unit
) : RecyclerView.Adapter<TimetablesListAdapter.NotificationsViewHolder>() {

    var items = mutableListOf<Timetable>()

    fun addEntireData(timetables: List<Timetable>) {
        items.addAll(timetables)
        notifyDataSetChanged()
    }

    fun updateFavourite(position: Int, favourite: Int) {
        items[position].favourite = favourite
        notifyItemChanged(position)
    }

    fun addItem(news: Timetable) {
        items.add(news)
    }

    fun clear() {
        items.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_timetable, parent, false)
        return NotificationsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val item = items[position]

            itemView.apply {
                tv_line_id.text = item.lineNumber
                tv_line_name.text = context.getString(TimetablesData().getTimetableTitle(item.lineId))
                setOnClickListener {
                    onClickListener(item)
                }

                iv_menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.fragment_recycler_view)

                        // Favourites labels
                        menu.findItem(R.id.nav_favourites).let { menuItem ->
                            if (item.favourite == 0) {
                                menuItem.title = "Add to favourites"
                            } else {
                                menuItem.title = "Remove from favourites"
                            }
                        }

                        // onClickListeners
                        setOnMenuItemClickListener {menuItem ->
                            when(menuItem.itemId) {
                                R.id.nav_favourites -> onClickFavourites(position, item)
                                R.id.nav_recent -> onClickMenuGmaps(item)
                            }
                            true
                        }
                    }.show()
                }
            }
        }

    }
}