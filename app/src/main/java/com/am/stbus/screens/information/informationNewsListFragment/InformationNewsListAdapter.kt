package com.am.stbus.screens.information.informationNewsListFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.stbus.R
import com.am.stbus.networking.models.News
import kotlinx.android.synthetic.main.item_row_news.view.*

class InformationNewsListAdapter(val context: Context?,
                                 var onClickListener: (News) -> Unit
) : RecyclerView.Adapter<InformationNewsListAdapter.NotificationsViewHolder>() {

    private var informationNewsList = mutableListOf<News>()

    fun addEntireData(newsData: List<News>) {
        informationNewsList.addAll(newsData)
        notifyDataSetChanged()
    }

    fun addItem(news: News) {
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