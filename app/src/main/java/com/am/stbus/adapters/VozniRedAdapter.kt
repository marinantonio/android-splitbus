package com.am.stbus.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.am.stbus.R
import com.am.stbus.activities.VozniRedActivity
import com.am.stbus.fragments.VozniRedoviListFragment
import com.am.stbus.helpers.DatabaseHandler
import com.am.stbus.helpers.Utils
import com.am.stbus.models.VozniRed
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*


class VozniRedAdapter constructor (context: Context?, private var items: ArrayList<VozniRed>, private var city: Int) : RecyclerView.Adapter<VozniRedAdapter.ViewHolder>() {


    private val context: Context? = context
    private lateinit var vozniRed: List<VozniRed>
    private lateinit var db: DatabaseHandler
    private var activity: VozniRedoviListFragment? = null
    //var show = VozniRedoviListFragment() LEGACY KAD SAM ONO NEŠT TAMO POKUŠAVA
    //private var progressBar: ProgressBar = progress_bar
    var send = VozniRedoviListFragment()


/*    override fun onClick(v: View?) {
        Log.v("onCLICK", "test")
        // Toast.makeText(p0, "Test" + p0, )
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

/*    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }*/

    /*class VozniRedAdapter(val items : ArrayList<VozniRed>, context: Activity?) : RecyclerView.Adapter<VozniRedAdapter.ViewHolder>() {
        //this method is returning the view for each item in the list

        internal class ContentAdapter(
                private val activity: AppCompatActivity,
                private var dataList: ArrayList<DataList>
        ) : RecyclerView.Adapter<ContentAdapter.ViewHolder>(), View.OnClickListener {

        }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_row, parent, false)

        Log.e("Test", city.toString())

        return ViewHolder(v).listen { pos, type ->
            if (Utils().onlineStatus(parent.context)) {
                Log.e("itemsweb", (items[pos].web.toString()))
                db = DatabaseHandler(parent.context)
                //TODO: Iskljucit cu ovdje automatsko dodavanje
                //val vozniRed = db.getVozniRed(pos)



/*                Log.v("clicked",  "position " + pos)
                db = DatabaseHandler(parent.context)
                when (city) {
                    1 -> {
                        db.getGradSingle(pos)
                        vozniRed = db.getGradSingle(pos)
                    }
                    2 -> {
                        db.getPrigradSingle(pos)
                        vozniRed = db.getPrigradSingle(pos)
                    }
                    else -> Log.e("bug", "error")
                }
                //val vozniRed = db.getGradSingle(pos)*/
                val naziv = items[pos].naziv
                val web = items[pos].web
                val gmaps = items[pos].gmaps
                val podrucje = items[pos].tip
                val nedavno = items[pos].nedavno
                Log.e("podrucje", podrucje.toString())

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
        holder.bindItems(items[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return items.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: VozniRed) {
            itemView.item_name.text = user.naziv
            //itemView.item_status.naziv=user.preuzeto
            //itemView.iv_name.setImageResource(user.image)
            itemView.item_menu.setOnClickListener { view -> Popup(view, position) }
        }

        fun Popup(view: View, position: Int) {
            //var context: Context
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.fragment_recycler_view)
            //popupMenu.
            //popupMenu.setOnMenuItemClickListener(this@GradskiFragment)
            popupMenu.setOnMenuItemClickListener { myItem ->

                //Getting Id of selected Item
                val item = myItem!!.itemId

                when (item) {
                    R.id.nav_recent -> {
                        Log.v("TEST", "ON MENI CLICK " + position)
                       //background.setBackgroundColor(Color.RED)
                    }

                    R.id.nav_urban -> {
                       // background.setBackgroundColor(Color.BLUE)
                    }

                    R.id.nav_suburban -> {
                        //background.setBackgroundColor(Color.GREEN)
                    }
                }

                true
            }
            popupMenu.show()
            //menuposition = position
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }
}





/*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
/*        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
        return new ViewHolder(v);*/

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }


/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }*/

    // https://android.jlelse.eu/using-recyclerview-in-android-kotlin-722991e86bf3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder?.txtName?.naziv = items[position].vozniRed
        holder?.txtTitle?.naziv = items.get(position)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtTitle = view.txtTitle
        //val txtName = itemView.findViewById<TextView>(R.id.txtName)
        //val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)

    }
} */