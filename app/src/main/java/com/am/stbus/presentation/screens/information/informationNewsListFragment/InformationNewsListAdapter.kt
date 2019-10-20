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

package com.am.stbus.presentation.screens.information.informationNewsListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.domain.models.NewsListItem
import kotlinx.android.synthetic.main.item_row_news.view.*

class InformationNewsListAdapter(val context: Context?,
                                 var onClickListener: (NewsListItem) -> Unit
) : RecyclerView.Adapter<InformationNewsListAdapter.NotificationsViewHolder>() {

    private var informationNewsList = mutableListOf<NewsListItem>()

    fun addEntireData(newsData: List<NewsListItem>) {
        informationNewsList.addAll(newsData)
        notifyDataSetChanged()
    }

    fun addItem(news: NewsListItem) {
        informationNewsList.add(news)
    }

    fun clear() {
        informationNewsList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_news, parent, false)
        return NotificationsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return informationNewsList.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {

            val news = informationNewsList[position]

            itemView.apply {
                titleTextView.text = news.title
                dateTextView.text = news.date
                summaryTextView.text = news.desc
                setOnClickListener {
                    onClickListener(news)
                }
            }
        }

    }
}