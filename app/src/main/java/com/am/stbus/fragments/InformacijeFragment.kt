package com.am.stbus.fragments

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.am.stbus.R
import com.am.stbus.activities.SlikaActivity
import com.am.stbus.adapters.InformacijeAdapter
import com.am.stbus.models.Informacija
import kotlinx.android.synthetic.main.fragment_informacije.view.*



class InformacijeFragment : Fragment() {
    private val TAG = InformacijeAdapter::class.java.simpleName

    private val PROMET_URL = "http://www.promet-split.hr/"
    private val KARTA_GRAD_URL = "https://www.dropbox.com/s/wdh2p8tbfzbtk5b/kartagrad.png?dl=1"
    private val KARTA_PRIGRAD_URL = "https://www.dropbox.com/s/0k6c96k4e261kwf/kartaprigrad.png?dl=1"
    private val TARIFNE_URL = "https://www.dropbox.com/s/ujsw5xjicx7i0az/tarifnezone.png?dl=1"
    private val PARKING_URL = "http://www.splitparking.hr/parkiralista"
    private val GARAZE_URL = "http://www.splitparking.hr/garaze"


    private lateinit var rootView: View
    private lateinit var informacijeAdapter: InformacijeAdapter
    private var data = ArrayList<Informacija>()

    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"
    private var customTabsServiceConnection: CustomTabsServiceConnection? = null
    private var client: CustomTabsClient? = null
    private var customTabsSession: CustomTabsSession? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /** RECYCLER VIEW
         *  https://github.com/andijakl/PartsList/blob/master/app/src/main/java/com/andresjakl/partslist/MainActivity.kt
         * */

        rootView = inflater.inflate(R.layout.fragment_informacije, container, false)
        rootView.rv_informacije.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rootView.rv_informacije.hasFixedSize()

        data.add(Informacija(getString(R.string.informacije_prometsplit_naslov), getString(R.string.informacije_prometsplit_opis)))
        data.add(Informacija(getString(R.string.informacije_gmaps_naslov), getString(R.string.informacije_gmaps_opis)))
        data.add(Informacija(getString(R.string.informacije_karta_grad_naslov), getString(R.string.informacije_karta_grad_opis)))
        data.add(Informacija(getString(R.string.informacije_karta_prigrad_naslov), getString(R.string.informacije_karta_prigrad_opis)))
        data.add(Informacija(getString(R.string.informacije_tarifne_zone_naslov), getString(R.string.informacije_tarifne_zone_opis)))
        data.add(Informacija(getString(R.string.informacije_parking_naslov), getString(R.string.informacije_parking_opis)))
        data.add(Informacija(getString(R.string.informacije_garaze_naslov), getString(R.string.informacije_garaze_opis)))

        informacijeAdapter = InformacijeAdapter(data) { informacija: Informacija -> onItemClicked(informacija)}
        rootView.rv_informacije.adapter = informacijeAdapter


        customTabsServiceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
                //Pre-warming
                client = customTabsClient
                client?.warmup(0L)
                customTabsSession = client?.newSession(null)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                client = null
            }
        }
        CustomTabsClient.bindCustomTabsService(context, CUSTOM_TAB_PACKAGE_NAME, customTabsServiceConnection)

        return rootView
    }

    private fun onItemClicked(informacija: Informacija) {
        when {
            informacija.naslov == getString(R.string.informacije_prometsplit_naslov) -> loadUrl(PROMET_URL)
            informacija.naslov == getString(R.string.informacije_gmaps_naslov) -> Toast.makeText(context, getString(R.string.nijejos), Toast.LENGTH_SHORT).show()
            informacija.naslov == getString(R.string.informacije_karta_grad_naslov) -> startSlikeActivity(informacija.naslov, KARTA_GRAD_URL)
            informacija.naslov == getString(R.string.informacije_karta_prigrad_naslov) -> startSlikeActivity(informacija.naslov, KARTA_PRIGRAD_URL)
            informacija.naslov == getString(R.string.informacije_tarifne_zone_naslov) -> startSlikeActivity(informacija.naslov, TARIFNE_URL)
            informacija.naslov == getString(R.string.informacije_parking_naslov) -> loadUrl(PARKING_URL)
            informacija.naslov == getString(R.string.informacije_garaze_naslov) -> loadUrl(GARAZE_URL)
        }
    }

    private fun loadUrl(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder(customTabsSession)
                .setToolbarColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
                .setShowTitle(true)
                .build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    private fun startSlikeActivity(naslov: String, url: String) {
        val intent = Intent(context, SlikaActivity::class.java)
        intent.putExtra("naslov", naslov)
        intent.putExtra("url", url)
        startActivity(intent)
    }

}
