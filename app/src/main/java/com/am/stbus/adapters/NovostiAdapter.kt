package com.am.stbus.adapters;

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.stbus.R
import com.am.stbus.activities.NovostActivity
import com.am.stbus.models.Novost
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
           // itemView.item_menu.setOnClickListener { view -> Popup(view, position) }
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