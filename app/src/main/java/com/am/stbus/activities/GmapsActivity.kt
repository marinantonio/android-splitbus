/*
 * MIT License
 *
 * Copyright (c) 2013 - 2018 Antonio Marin
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

package com.am.stbus.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.am.stbus.R
import com.am.stbus.gmaps.*
import com.am.stbus.helpers.Utils
import com.am.stbus.models.GmapsStanica
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import kotlinx.android.synthetic.main.activity_gmaps.*

private val TAG = GmapsActivity::class.java.simpleName
private const val LOCATION_PERMISSION_REQUEST_CODE = 1

/**
 *
 * Created by Antonio Marin on 26.8.2018.
 * */
class GmapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var lin1: Polyline
    private lateinit var lin2: Polyline
    private lateinit var lin3: Polyline
    private lateinit var lin301: Polyline
    private lateinit var lin5: Polyline
    private lateinit var lin5A: Polyline
    private lateinit var lin6: Polyline
    private lateinit var lin61: Polyline
    private lateinit var lin7: Polyline
    private lateinit var lin8: Polyline
    private lateinit var lin9: Polyline
    private lateinit var lin10: Polyline
    private lateinit var lin11: Polyline
    private lateinit var lin12: Polyline
    private lateinit var lin14: Polyline
    private lateinit var lin15: Polyline
    private lateinit var lin16: Polyline
    private lateinit var lin17: Polyline
    private lateinit var lin18: Polyline
    private lateinit var lin21: Polyline
    private lateinit var lin22: Polyline
    private lateinit var lin23: Polyline
    private lateinit var lin24: Polyline
    private lateinit var lin25: Polyline
    private lateinit var lin26: Polyline
    private lateinit var lin27: Polyline
    private lateinit var lin28: Polyline
    private lateinit var lin29: Polyline
    private lateinit var lin31: Polyline
    private lateinit var lin32: Polyline
    private lateinit var lin33: Polyline
    private lateinit var lin34: Polyline
    private lateinit var lin39: Polyline
    private lateinit var lin40: Polyline
    private lateinit var lin30: Polyline
    private lateinit var lin37: Polyline
    private lateinit var lin38: Polyline
    private lateinit var lin60: Polyline
    private lateinit var lin67: Polyline
    private lateinit var lin36: Polyline
    private lateinit var lin68: Polyline
    private lateinit var lin69: Polyline
    private lateinit var lin71: Polyline
    private lateinit var lin72: Polyline
    private lateinit var lin76: Polyline
    private lateinit var lin77: Polyline
    private lateinit var lin80: Polyline
    private lateinit var linVrkadglav: Polyline
    private lateinit var lin81: Polyline
    private lateinit var lin73: Polyline
    private lateinit var lin86: Polyline
    // fixevi
    private lateinit var linFix8: Polyline
    private lateinit var linFix: Polyline
    private lateinit var linPoj: Polyline
    private lateinit var linPojs: Polyline

    private lateinit var marHnk: Marker
    private lateinit var marOpcinaz: Marker
    private lateinit var marOpcinai: Marker
    private lateinit var marSolinskakol: Marker
    private lateinit var marZabaz: Marker
    private lateinit var marZabai: Marker
    private lateinit var marGuruz: Marker
    private lateinit var marGurui: Marker
    private lateinit var marKman1: Marker
    private lateinit var marKman2: Marker
    private lateinit var marKman3: Marker
    private lateinit var marKman4: Marker
    private lateinit var marKocunar1: Marker
    private lateinit var marKauflandj: Marker
    private lateinit var marKauflands: Marker
    private lateinit var marTommys: Marker
    private lateinit var marBilla: Marker
    private lateinit var marKocunar2: Marker
    private lateinit var marKocunar3: Marker
    private lateinit var marDomrata1: Marker
    private lateinit var marSplitmall: Marker
    private lateinit var marZbngs: Marker
    private lateinit var marZbngj: Marker
    private lateinit var marDujmovaca: Marker
    private lateinit var marPrima: Marker
    private lateinit var marSjlukas: Marker
    private lateinit var marSjlukaj: Marker
    private lateinit var marPopravnidom: Marker
    private lateinit var marPrometovagarazas: Marker
    private lateinit var marBrda1: Marker
    private lateinit var marPosta: Marker
    private lateinit var marHerc1s: Marker
    private lateinit var marHerc2j: Marker
    private lateinit var marPeugs: Marker
    private lateinit var marPeugj: Marker
    private lateinit var marRotorbrda: Marker
    private lateinit var marMostarska: Marker
    private lateinit var marMostarska1: Marker
    private lateinit var marKaufland: Marker
    private lateinit var marKauflandi: Marker
    private lateinit var marZagputs: Marker
    private lateinit var marZagputj: Marker
    private lateinit var marZagput9: Marker
    private lateinit var marPujankes: Marker
    private lateinit var marPujankej: Marker
    private lateinit var marPujanke1s: Marker
    private lateinit var marPujanke1j: Marker
    private lateinit var marJokers: Marker
    private lateinit var marJokerj: Marker
    private lateinit var marBrodogradiliste: Marker
    private lateinit var marLidllora: Marker
    private lateinit var marPlavakava: Marker
    private lateinit var marLorai: Marker
    private lateinit var marLoraz: Marker
    private lateinit var marBazeni: Marker
    private lateinit var marZf1: Marker
    private lateinit var marUlhrvmorn: Marker
    private lateinit var marUlhrvmorn1s: Marker
    private lateinit var marUlhrvmorn1j: Marker
    private lateinit var marSplitstadion: Marker
    private lateinit var marUlhrvmorn3: Marker
    private lateinit var marPoljud: Marker
    private lateinit var marKineskizid: Marker
    private lateinit var marMurall: Marker
    private lateinit var marMurall1: Marker
    private lateinit var marInaspinut: Marker
    private lateinit var marInaspinut1: Marker
    private lateinit var marSpinut: Marker
    private lateinit var marTunel: Marker
    private lateinit var marTunelj: Marker
    private lateinit var marMihanoviceva: Marker
    private lateinit var marMihanoviceva1: Marker
    private lateinit var marSupilova: Marker
    private lateinit var marSupilova1: Marker
    private lateinit var marGunjecin: Marker
    private lateinit var marPutmeja: Marker
    private lateinit var marSetalisteim: Marker
    private lateinit var marSetalisteim1: Marker
    private lateinit var marSetalisteim2: Marker
    private lateinit var marMedenizvoncac: Marker
    private lateinit var marMedenizvoncac1: Marker
    private lateinit var marDrazanac: Marker
    private lateinit var marZapobala: Marker
    private lateinit var marZapobala1: Marker
    private lateinit var marSvfrane: Marker
    private lateinit var marBene: Marker
    private lateinit var marVertigo: Marker
    private lateinit var marVertigo1: Marker
    private lateinit var marStariplac: Marker
    private lateinit var marStariplaci: Marker
    private lateinit var marSportshop: Marker
    private lateinit var marSud: Marker
    private lateinit var marDubrov1: Marker
    private lateinit var marCfvrime: Marker
    private lateinit var marDubrov2: Marker
    private lateinit var marDubrov3: Marker
    private lateinit var marDubrov4: Marker
    private lateinit var marDubrov5: Marker
    private lateinit var marDubrov6: Marker
    private lateinit var marDubrov7: Marker
    private lateinit var marVel1: Marker
    private lateinit var marPlavazgrada: Marker
    private lateinit var marPlavazgrada1: Marker
    private lateinit var marVel2: Marker
    private lateinit var marVel3: Marker
    private lateinit var marVel4: Marker
    private lateinit var marVel5: Marker
    private lateinit var marVelvicta: Marker
    private lateinit var marMcgrgo: Marker
    private lateinit var marVel6i: Marker
    private lateinit var marVel6z: Marker
    private lateinit var marVel7z: Marker
    private lateinit var marVel7i: Marker
    private lateinit var marMedeniznjani: Marker
    private lateinit var marMedeniznjanz: Marker
    private lateinit var marPlaza1: Marker
    private lateinit var marPlaza2: Marker
    private lateinit var marPapeivanapavla1: Marker
    private lateinit var marPapeivanapavla2: Marker
    private lateinit var marDuilovo1: Marker
    private lateinit var marDuilovo2: Marker
    private lateinit var marDuilovo: Marker
    private lateinit var marKila: Marker
    private lateinit var marVrboran: Marker
    private lateinit var marVuk1s: Marker
    private lateinit var marVuk1j: Marker
    private lateinit var marVuk2s: Marker
    private lateinit var marVuk2j: Marker
    private lateinit var marVuk3s: Marker
    private lateinit var marVuk3j: Marker
    private lateinit var marVukvicta: Marker
    private lateinit var marVuksucidar: Marker
    private lateinit var marVukkampus: Marker
    private lateinit var marVukkampusj: Marker
    private lateinit var marVuk4s: Marker
    private lateinit var marVuk4j: Marker
    private lateinit var marVuk5: Marker
    private lateinit var marVuk6: Marker
    private lateinit var marVuk7: Marker
    private lateinit var marVukopcina: Marker
    private lateinit var marMaz1: Marker
    private lateinit var marMaz2: Marker
    private lateinit var marBrnik: Marker
    private lateinit var marBrnik1: Marker
    private lateinit var marFesb: Marker
    private lateinit var marSplit3: Marker
    private lateinit var marDjackidoms: Marker
    private lateinit var marDjackidomj: Marker
    private lateinit var marMathrv1s: Marker
    private lateinit var marMathrv1j: Marker
    private lateinit var marKotekss: Marker
    private lateinit var marKoteksj: Marker
    private lateinit var marUlslobodei: Marker
    private lateinit var marUlslobodez: Marker
    private lateinit var marBb1: Marker
    private lateinit var marMioc: Marker
    private lateinit var marBb2: Marker
    private lateinit var marBb3: Marker
    private lateinit var marPazarz: Marker
    private lateinit var marPazari: Marker
    private lateinit var marTrluka: Marker
    private lateinit var marPopajpois: Marker
    private lateinit var marPoiss: Marker
    private lateinit var marAutohrv: Marker
    private lateinit var marFirule: Marker
    private lateinit var marPolj1s: Marker
    private lateinit var marPolj1j: Marker
    private lateinit var marPolj2s: Marker
    private lateinit var marPolj2j: Marker
    private lateinit var marTesla: Marker
    private lateinit var marTeslaj: Marker
    private lateinit var marPolj3s: Marker
    private lateinit var marPolj3j: Marker
    private lateinit var marHeps: Marker
    private lateinit var marHepj: Marker
    private lateinit var marUlzbng5: Marker
    private lateinit var marUlzbng6: Marker
    private lateinit var marUlzbng7: Marker
    private lateinit var marUlzbng8: Marker
    private lateinit var marLovrinac: Marker
    private lateinit var marLovrinacj: Marker
    private lateinit var marBracka1: Marker
    private lateinit var marBracka2: Marker
    private lateinit var marZnjan: Marker
    private lateinit var marOlly: Marker
    private lateinit var marTrstenik: Marker
    private lateinit var marMolhrv: Marker
    private lateinit var marKsucurac: Marker
    private lateinit var marStarine: Marker
    private lateinit var marDracevac: Marker
    private lateinit var marJapirko: Marker
    private lateinit var marNincevici: Marker
    private lateinit var marKlis: Marker
    private lateinit var marSolin: Marker
    private lateinit var marTtts: Marker
    private lateinit var marStobrec: Marker
    private lateinit var marKamen: Marker
    private lateinit var marZrnovnica: Marker
    private lateinit var marDubrava: Marker
    private lateinit var marNaklice: Marker
    private lateinit var marMutogras: Marker
    private lateinit var marVranjic: Marker
    private lateinit var marKucine: Marker
    private lateinit var marKosa: Marker
    private lateinit var marKoprivno: Marker
    private lateinit var marTrogir: Marker
    private lateinit var marResnik: Marker
    private lateinit var marOmis: Marker
    private lateinit var marGornjidolac: Marker
    private lateinit var marKovacevici: Marker
    private lateinit var marSestanovac: Marker
    private lateinit var marSutina: Marker
    private lateinit var marZelovo: Marker
    private lateinit var marOgorje: Marker
    private lateinit var marMuc: Marker
    private lateinit var marCrivac: Marker
    private lateinit var marKljaci: Marker
    private lateinit var marDrnis: Marker
    private lateinit var marKadglav: Marker
    private lateinit var marAksplit: Marker
    private lateinit var marBrstanovo: Marker
    private lateinit var marNisko: Marker
    private lateinit var marKladnjice: Marker
    private lateinit var marLecevica: Marker

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var gmapsId: String? = ""
    private var selectedItems: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gmaps)

        // TODO: Napisati firstRun dialog i objasnjenje za menu izbornik

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = getString(R.string.informacije_gmaps_naslov)
            setSupportActionBar(toolbar)

            // Nakon sto se stavi elevation na app_bar
            // onda ga zovnes natrag
            app_bar.bringToFront()
            //toolbar.elevation = 0f

            toolbar.setNavigationOnClickListener { finish() }
        }

        // Prozirni status bar
        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }

        gmapsId = intent.getStringExtra("gmaps")

        //setStatusBarTranslucent(true)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment: SupportMapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setPadding(0, Utils().dpToPx(80), 0, 0)
        map.uiSettings.isZoomControlsEnabled = true

        val update = CameraUpdateFactory.newLatLngZoom(Stanice.VUKKAMPUSS, 12f)
        map.animateCamera(update)
        map.uiSettings.isMapToolbarEnabled = true

        val myPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val maptypeint = myPreference.getString("maptype", "2")
        when (maptypeint) {
            "1" -> map.mapType = GoogleMap.MAP_TYPE_HYBRID
            "2" -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
            "3" -> map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }

        if (myPreference.getBoolean("traffic", false)) {
            map.isTrafficEnabled = true
        }

        val customInfoWindow = GmapsStanicaInfo(this)
        map.setInfoWindowAdapter(customInfoWindow)

        map.setOnInfoWindowClickListener(this)


        initAllMarkers()

        // Init ovih polylineova koji se koriste u vise linija
        // Ne initam sve polylineove jer mislim da bi to bilo previse posla
        lin61 = map.addPolyline(Storage.vr071)
        linPoj = map.addPolyline(Storage.vrpoj)
        linPojs = map.addPolyline(Storage.vrpojs)

        lin61.isVisible = false
        linPoj.isVisible = false
        linPojs.isVisible = false


        hideMainMarkers()
        hideCityMarkers()
        hideUrbanMarkers()

        if (gmapsId != null) {
            selectedItems.add(gmapsId!!)
            displayLine(gmapsId!!)
        } else {
            // Ako je null prikazi glavne markere
            // dogada se nakon ulaza iz informacija
            showMainMarkers()
        }

        setUpMap()
    }


    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        // 1
        map.isMyLocationEnabled = true

     /*   // 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }*/
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Dopusteno
                    setUpMap()
                } else {
                    // Dopustenje odbijeno
                }
                return
            }

            // When se koristi za druga dopustenja kojih nemamo zasad
            else -> {
                // Ignoriraj sve ostalo
            }
        }
    }


    /**
     * Metoda koja generira AlertWindow i postavlja pocetne
     * tickove prema class level listi [selectedItems]
     */
    private fun createMarkerAlert(arLinije: ArrayList<String>) {
        // Prvi Array se pravi prema linijama pojedine stanice
        val linijeList: ArrayList<String> = arrayListOf()
        for (i in 0 until arLinije.size) {
            // Kako bi prikazao nazive linija, a ne samo brojeve onda iz Utilsa dohvatimo
            // za broj linije puni naziv (prema stringovima)
            linijeList.add(Utils().getFullName(this, arLinije[i]))
        }

        // Prvi array za linije generiramo iz arraya linijeList, razliciti su tipovi pa ih
        // ne mogu reciklirat
        val linije = linijeList.toArray(arrayOfNulls<CharSequence>(linijeList.size))


        // Drugi Array je prazan s booleanima i on je jednake
        // velicine kao i prvi po defaultima sve je to false
        val booleans = BooleanArray(arLinije.size)

        // Sad kod prolazi kroz selectedItems, linije koje sam dodao prije
        // provjerava nalazi li se ista ta u items i ako je onda za tu
        // poziciju u booleansima stavlja true
        // tldr: logika za popup prozore
        for (i in 0 until arLinije.size) {
            for(j in 0 until selectedItems.size) {
                if(selectedItems[j].contentEquals(arLinije[i])) {
                    booleans[i] = true
                }
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.gmaps_select_title)
        builder.setMultiChoiceItems(linije, booleans) { _, indexSelected, isChecked ->
            if (isChecked) {
                selectedItems.add(arLinije[indexSelected])
                displayLine(arLinije[indexSelected])
            } else {
                selectedItems.remove(arLinije[indexSelected])
                removeLine(arLinije[indexSelected])
            }
        }.setNegativeButton(R.string.close) { _, _ ->
        }
        builder.create().show()
    }

    /**
     * Helper metoda koja malo smanjuje kod za dodavanje
     * markera na kartu, dodaje opcije markera, naslov i linije
     * koje prolaze tom stanicom
     */
    private fun addMarker(markerOptions: MarkerOptions, title: String,
                          lines: ArrayList<String>): Marker {
        val marker = map.addMarker(markerOptions)
        marker.tag = GmapsStanica(title, lines)
        return marker
    }
    

    /**
     * Init i dodavanje pojedinih linija na kartu
     */
    private fun displayLine(linija: String) {
        when (linija) {
            "1" -> {
                lin1 = map.addPolyline(Storage1.vr01)

                marHnk.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marKman1.isVisible = true
                marKman2.isVisible = true
                marKman3.isVisible = true
                marKman4.isVisible = true
                marKocunar1.isVisible = true
                marKauflandj.isVisible = true
                marKauflands.isVisible = true
                marTommys.isVisible = true
                marBilla.isVisible = true
                marKocunar2.isVisible = true
                marKocunar3.isVisible = true
                marDomrata1.isVisible = true
                marSplitmall.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marStarine.isVisible = true
            }
            "2" -> {
                lin2 = map.addPolyline(Storage.vr02)

                marTrluka.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marDujmovaca.isVisible = true
                marPrima.isVisible = true
                marSjlukas.isVisible = true
                marSjlukaj.isVisible = true
                marPopravnidom.isVisible = true
                marPrometovagarazas.isVisible = true
                marBrda1.isVisible = true
                marPosta.isVisible = true
                marHerc1s.isVisible = true
                marHerc2j.isVisible = true
                marKsucurac.isVisible = true
            }
            "3" -> {
                lin3 = map.addPolyline(Storage.vr03)
                lin301 = map.addPolyline(Storage.vr04)

                marLovrinac.isVisible = true
                marUlzbng6.isVisible = true
                marHeps.isVisible = true
                marPolj3s.isVisible = true
                marTesla.isVisible = true
                marPolj2s.isVisible = true
                marPolj1s.isVisible = true
                marAutohrv.isVisible = true
                marPoiss.isVisible = true
                marPazari.isVisible = true
                marOpcinai.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marPosta.isVisible = true
                marHerc2j.isVisible = true
                marBrda1.isVisible = true
                marPeugj.isVisible = true
                marRotorbrda.isVisible = true

                // 320
                // marRotorbrda.isVisible = true
                marMostarska.isVisible = true
                marMostarska1.isVisible = true
                marPeugs.isVisible = true
                marPrometovagarazas.isVisible = true
                marHerc1s.isVisible = true
                marGurui.isVisible = true
                marZabaz.isVisible = true
                marPazarz.isVisible = true
                marOpcinaz.isVisible = true
                marPopajpois.isVisible = true
                marFirule.isVisible = true
                marPolj1j.isVisible = true
                marPolj2j.isVisible = true
                marPolj3j.isVisible = true
                marHepj.isVisible = true
                //marLovrinac.isVisible = true
                marLovrinacj.isVisible = true
                marUlzbng5.isVisible = true
            }

            "5" -> {
                lin5 = map.addPolyline(Storage.vr06)
                linPoj.isVisible = true

                marHnk.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marUlzbng7.isVisible = true
                marUlzbng8.isVisible = true
                marDracevac.isVisible = true
            }

            "5A" -> {
                lin5A = map.addPolyline(Storage.vr99)

                marHnk.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marUlzbng7.isVisible = true
                marUlzbng8.isVisible = true
                marDracevac.isVisible = true
            }

            "6" -> {
                lin6 = map.addPolyline(Storage.vr07)
                lin61.isVisible = true

                marHnk.isVisible = true
                marOpcinaz.isVisible = true
                marKila.isVisible = true
                marVrboran.isVisible = true
                marVuk1s.isVisible = true
                marVuk2s.isVisible = true
                marVuk2j.isVisible = true
                marVuk3s.isVisible = true
                marVuk3j.isVisible = true
                marVukvicta.isVisible = true
                marVuksucidar.isVisible = true
                marVukkampus.isVisible = true
                marVukkampusj.isVisible = true
                marVuk4s.isVisible = true
                marVuk4j.isVisible = true
                marDubrov3.isVisible = true
                marVuk6.isVisible = true
                marVuk7.isVisible = true
                marVukopcina.isVisible = true
                marMaz1.isVisible = true
                marMaz2.isVisible = true
            }

            "7" -> {
                lin7 = map.addPolyline(Storage.vr08)

                marBracka1.isVisible = true
                marBracka2.isVisible = true
                marZnjan.isVisible = true
                marVel1.isVisible = true
                marPlavazgrada.isVisible = true
                marPlavazgrada1.isVisible = true
                marVel2.isVisible = true
                marVel3.isVisible = true
                marVel4.isVisible = true
                marVel5.isVisible = true
                marVelvicta.isVisible = true
                marMcgrgo.isVisible = true
                marVel6i.isVisible = true
                marVel6z.isVisible = true
                marVel7z.isVisible = true
                marVel7i.isVisible = true
                marMedeniznjani.isVisible = true
                marDubrov1.isVisible = true
                marCfvrime.isVisible = true
                marJokerj.isVisible = true
                marJokers.isVisible = true
                marBrodogradiliste.isVisible = true
                marLidllora.isVisible = true
                marPlavakava.isVisible = true
                marBazeni.isVisible = true
                marZf1.isVisible = true
                marPoljud.isVisible = true
                marKineskizid.isVisible = true
                marMurall.isVisible = true
                marMurall1.isVisible = true
                marTunel.isVisible = true
                marTunelj.isVisible = true
                marGunjecin.isVisible = true
                marPutmeja.isVisible = true
                marMedenizvoncac.isVisible = true
                marDrazanac.isVisible = true
            }

            "8" -> {
                lin8 = map.addPolyline(Storage.vr09)
                linFix8 = map.addPolyline(Storage.vr091)
                linPoj.isVisible = true

                marTunel.isVisible = true
                marTunelj.isVisible = true
                marGunjecin.isVisible = true
                marPutmeja.isVisible = true
                marMedenizvoncac.isVisible = true
                marDrazanac.isVisible = true
                marVertigo.isVisible = true
                marVertigo1.isVisible = true
                marStariplac.isVisible = true
                marStariplaci.isVisible = true
                marSportshop.isVisible = true
                marSud.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marVel7z.isVisible = true
                marVel7i.isVisible = true
                marMedeniznjani.isVisible = true
                marBracka1.isVisible = true
                marBracka2.isVisible = true
                marZnjan.isVisible = true
            }

            "9" -> {
                lin9 = map.addPolyline(Storage.vr10)

                marKaufland.isVisible = true
                marKauflandi.isVisible = true
                marPeugj.isVisible = true
                marRotorbrda.isVisible = true
                marZagputs.isVisible = true
                marZagputj.isVisible = true
                marZagput9.isVisible = true
                marVel1.isVisible = true
                marPlavazgrada.isVisible = true
                marPlavazgrada1.isVisible = true
                marVel2.isVisible = true
                marVel3.isVisible = true
                marVel4.isVisible = true
                marDubrov1.isVisible = true
                marCfvrime.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marTrluka.isVisible = true
            }

            "10" -> {
                lin10 = map.addPolyline(Storage1.vr100)

                marTrluka.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marKman1.isVisible = true
                marKman2.isVisible = true
                marKman3.isVisible = true
                marKman4.isVisible = true
                marKocunar1.isVisible = true
                marKauflandj.isVisible = true
                marKauflands.isVisible = true
                marTommys.isVisible = true
                marBilla.isVisible = true
                marKocunar2.isVisible = true
                marKocunar3.isVisible = true
                marDomrata1.isVisible = true
                marSplitmall.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marJapirko.isVisible = true
            }

            "11" -> {
                lin11 = map.addPolyline(Storage1.vr110)
                linPoj.isVisible = true

                marHnk.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marBb2.isVisible = true
                marBb3.isVisible = true
                marFesb.isVisible = true
                marSplit3.isVisible = true
                marDjackidoms.isVisible = true
                marDjackidomj.isVisible = true
                marVuk3s.isVisible = true
                marVuk3j.isVisible = true
                marVukvicta.isVisible = true
                marMcgrgo.isVisible = true
                marVel6i.isVisible = true
                marVel6z.isVisible = true
                marPujankes.isVisible = true
                marPujankej.isVisible = true
                marPujanke1s.isVisible = true
                marPujanke1j.isVisible = true
                marZagputs.isVisible = true
                marZagputj.isVisible = true
                marPeugj.isVisible = true
                marRotorbrda.isVisible = true
                marKaufland.isVisible = true
                marKauflandi.isVisible = true
            }

            "12" -> {
                lin12 = map.addPolyline(Storage1.vr120)

                marBene.isVisible = true
                marTunelj.isVisible = true
                marMihanoviceva.isVisible = true
                marMihanoviceva1.isVisible = true
                marSupilova.isVisible = true
                marSupilova1.isVisible = true
                marGunjecin.isVisible = true
                marSetalisteim.isVisible = true
                marSetalisteim1.isVisible = true
                marSetalisteim2.isVisible = true
                marMedenizvoncac.isVisible = true
                marMedenizvoncac1.isVisible = true
                marZapobala.isVisible = true
                marZapobala1.isVisible = true
                marSvfrane.isVisible = true
            }

            "14" -> {
                lin14 = map.addPolyline(Storage1.vr140)
                linFix = map.addPolyline(Storage.fix)
                linPoj.isVisible = true

                marRotorbrda.isVisible = true
                marMostarska.isVisible = true
                marMostarska1.isVisible = true
                marPeugs.isVisible = true
                marPeugj.isVisible = true
                marPrometovagarazas.isVisible = true
                marPosta.isVisible = true
                marBrda1.isVisible = true
                marHerc1s.isVisible = true
                marHerc2j.isVisible = true
                marDubrov1.isVisible = true
                marCfvrime.isVisible = true
                marDubrov2.isVisible = true
                marDubrov3.isVisible = true
                marDubrov4.isVisible = true
                marDubrov5.isVisible = true
                marDubrov6.isVisible = true
                marDubrov7.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marHnk.isVisible = true
            }

            "15" -> {
                lin15 = map.addPolyline(Storage1.vr150)
                lin61.isVisible = true

                marTrluka.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marOpcinaz.isVisible = true
                marVuk6.isVisible = true
                marVuk7.isVisible = true
                marVukopcina.isVisible = true
                marMaz1.isVisible = true
                marMaz2.isVisible = true
                marVuk5.isVisible = true
                marVuk4s.isVisible = true
                marDubrov3.isVisible = true
                marBb1.isVisible = true
                marMioc.isVisible = true
                marBb2.isVisible = true
                marBb3.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marVel7z.isVisible = true
                marVel7i.isVisible = true
                marMedeniznjani.isVisible = true
                marMedeniznjanz.isVisible = true
                marPlaza1.isVisible = true
                marPlaza2.isVisible = true
                marPapeivanapavla1.isVisible = true
                marPapeivanapavla2.isVisible = true
                marDuilovo.isVisible = true
                marDuilovo1.isVisible = true
                marDuilovo2.isVisible = true
            }

            "16" -> {
                lin16 = map.addPolyline(Storage1.vr160)

                marHnk.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marKman1.isVisible = true
                marKman2.isVisible = true
                marKman3.isVisible = true
                marKman4.isVisible = true
                marKocunar1.isVisible = true
                marKauflandj.isVisible = true
                marKauflands.isVisible = true
                marTommys.isVisible = true
                marBilla.isVisible = true
                marKocunar2.isVisible = true
                marKocunar3.isVisible = true
                marDomrata1.isVisible = true
                marSplitmall.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marNincevici.isVisible = true
            }

            "17" -> {
                lin17 = map.addPolyline(Storage1.vr170)
                linPojs.isVisible = true

                marLorai.isVisible = true
                marLoraz.isVisible = true
                marBazeni.isVisible = true
                marZf1.isVisible = true
                marUlhrvmorn.isVisible = true
                marUlhrvmorn1s.isVisible = true
                marUlhrvmorn1j.isVisible = true
                marSplitstadion.isVisible = true
                marUlhrvmorn3.isVisible = true
                marPoljud.isVisible = true
                marKineskizid.isVisible = true
                marMurall.isVisible = true
                marMurall1.isVisible = true
                marInaspinut.isVisible = true
                marInaspinut1.isVisible = true
                marSpinut.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marBb2.isVisible = true
                marFesb.isVisible = true
                marDjackidoms.isVisible = true
                marVel7i.isVisible = true
                marOlly.isVisible = true
                marTrstenik.isVisible = true
                marMolhrv.isVisible = true
                marPazari.isVisible = true
                marPazarz.isVisible = true
            }

            "18" -> {
                lin18 = map.addPolyline(Storage1.vr180)

                marHnk.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marMathrv1s.isVisible = true
                marMathrv1j.isVisible = true
                marKotekss.isVisible = true
                marKoteksj.isVisible = true
                marUlslobodei.isVisible = true
                marUlslobodez.isVisible = true
                marFesb.isVisible = true
                marSplit3.isVisible = true
                marDjackidoms.isVisible = true
                marDjackidomj.isVisible = true
                marVuk3s.isVisible = true
                marVuk3j.isVisible = true
                marVukvicta.isVisible = true
                marMcgrgo.isVisible = true
                marVel6i.isVisible = true
                marVel6z.isVisible = true
                marVuk1s.isVisible = true
                marVuk1j.isVisible = true
                marVuk2s.isVisible = true
                marVuk2j.isVisible = true
                marVuk3s.isVisible = true
                marVuk3j.isVisible = true
                marBrnik1.isVisible = true
                marBrnik.isVisible = true
            }

            "21" -> {
                lin21 = map.addPolyline(Storage1.vr210)

                marTunelj.isVisible = true
                marMihanoviceva.isVisible = true
                marMihanoviceva1.isVisible = true
                marSupilova.isVisible = true
                marSupilova1.isVisible = true
                marGunjecin.isVisible = true
                marMedenizvoncac.isVisible = true
                marMedenizvoncac1.isVisible = true
                marZapobala.isVisible = true
                marZapobala1.isVisible = true
                marSvfrane.isVisible = true
            }

            "22" -> {
                lin22 = map.addPolyline(Storage1.vr220)

                marHnk.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marKman1.isVisible = true
                marKman2.isVisible = true
                marKman3.isVisible = true
                marKman4.isVisible = true
                marKocunar1.isVisible = true
                marKauflandj.isVisible = true
                marKauflands.isVisible = true
                marTommys.isVisible = true
                marBilla.isVisible = true
                marKocunar2.isVisible = true
                marKocunar3.isVisible = true
                marDomrata1.isVisible = true
                marSplitmall.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marKlis.isVisible = true
            }

            "23" -> {
                lin23 = map.addPolyline(Storage1.vr230)

                marHnk.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marGuruz.isVisible = true
                marGurui.isVisible = true
                marKman1.isVisible = true
                marKman2.isVisible = true
                marKman3.isVisible = true
                marKman4.isVisible = true
                marKocunar1.isVisible = true
                marKauflandj.isVisible = true
                marKauflands.isVisible = true
                marTommys.isVisible = true
                marBilla.isVisible = true
                marKocunar2.isVisible = true
                marKocunar3.isVisible = true
                marDomrata1.isVisible = true
                marSplitmall.isVisible = true
                marZbngs.isVisible = true
                marZbngj.isVisible = true
                marSolin.isVisible = true

            }

            "24" -> {
                lin24 = map.addPolyline(Storage1.vr240)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marUlzbng6.isVisible = true
                marUlzbng5.isVisible = true
                marTtts.isVisible = true
            }

            "25" -> {
                lin25 = map.addPolyline(Storage1.vr250)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marStobrec.isVisible = true
            }

            "26" -> {
                lin26 = map.addPolyline(Storage2.vr260)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marKamen.isVisible = true

            }

            "27" -> {
                lin27 = map.addPolyline(Storage2.vr270)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marZrnovnica.isVisible = true
            }

            "28" -> {
                lin28 = map.addPolyline(Storage2.vr280)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marDubrava.isVisible = true
            }

            "29" -> {
                lin29 = map.addPolyline(Storage2.vr290)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marNaklice.isVisible = true
            }

            "31" -> {
                lin31 = map.addPolyline(Storage2.vr310)
                marSolinskakol.isVisible = true
                marVranjic.isVisible = true
            }

            "32" -> {
                lin32 = map.addPolyline(Storage2.vr320)
                marSolinskakol.isVisible = true
                marKucine.isVisible = true
            }

            "33" -> {
                lin33 = map.addPolyline(Storage2.vr330)
                marSolinskakol.isVisible = true
                marKosa.isVisible = true
            }

            "34" -> {
                lin34 = map.addPolyline(Storage2.vr340)
                marSolinskakol.isVisible = true
                marKlis.isVisible = true
            }

            "39" -> {
                lin39 = map.addPolyline(Storage2.vr390)
                linPoj.isVisible = true

                marLorai.isVisible = true
                marLoraz.isVisible = true
                marBazeni.isVisible = true
                marZf1.isVisible = true
                marUlhrvmorn.isVisible = true
                marUlhrvmorn1s.isVisible = true
                marUlhrvmorn1j.isVisible = true
                marSplitstadion.isVisible = true
                marZabaz.isVisible = true
                marZabai.isVisible = true
                marOpcinaz.isVisible = true
                marOpcinai.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marPopajpois.isVisible = true
                marPoiss.isVisible = true
                marAutohrv.isVisible = true
                marFirule.isVisible = true
                marPolj1s.isVisible = true
                marPolj1j.isVisible = true
                marPolj2s.isVisible = true
                marPolj2j.isVisible = true
                marTesla.isVisible = true
                marTeslaj.isVisible = true
                marPolj3s.isVisible = true
                marPolj3j.isVisible = true
                marHeps.isVisible = true
                marHepj.isVisible = true
                marUlzbng6.isVisible = true
                marUlzbng5.isVisible = true
                marTtts.isVisible = true
            }

            "40" -> {
                lin40 = map.addPolyline(Storage2.vr400)
                lin61.isVisible = true

                marTrluka.isVisible = true
                marPazarz.isVisible = true
                marPazari.isVisible = true
                marOpcinaz.isVisible = true
                marKila.isVisible = true
                marVrboran.isVisible = true
                marVuk1s.isVisible = true
                marVuk2s.isVisible = true
                marVuk2j.isVisible = true
                marVuk3s.isVisible = true
                marVuk3j.isVisible = true
                marVukvicta.isVisible = true
                marVuksucidar.isVisible = true
                marVukkampus.isVisible = true
                marVukkampusj.isVisible = true
                marVuk4s.isVisible = true
                marVuk4j.isVisible = true
                marDubrov3.isVisible = true
                marVuk6.isVisible = true
                marVuk7.isVisible = true
                marVukopcina.isVisible = true
                marMaz1.isVisible = true
                marMaz2.isVisible = true
            }

            "30" -> {
                lin30 = map.addPolyline(Storage3.vr300)
                marSolinskakol.isVisible = true
                marMutogras.isVisible = true
            }

            "37" -> {
                lin37 = map.addPolyline(Storage3.vr370)
                marSolinskakol.isVisible = true
                marTrogir.isVisible = true
            }

            "38" -> {
                lin38 = map.addPolyline(Storage3.vr380)
                marSolinskakol.isVisible = true
                marResnik.isVisible = true
            }

            "60" -> {
                lin60 = map.addPolyline(Storage3.vr600)
                marSolinskakol.isVisible = true
                marOmis.isVisible = true
            }

            "67" -> {
                lin67 = map.addPolyline(Storage3.vr670)
                marSolinskakol.isVisible = true
                marGornjidolac.isVisible = true

            }

            "36" -> {
                lin36 = map.addPolyline(Storage4.vr360)
                marSolinskakol.isVisible = true
                marKoprivno.isVisible = true
            }

            "68" -> {
                lin68 = map.addPolyline(Storage4.vr680)
                linPoj.isVisible = true

                marSolinskakol.isVisible = true
                marSestanovac.isVisible = true
                marKovacevici.isVisible = true
            }

            "69" -> {
                lin68 = map.addPolyline(Storage5.vr690)

                marSolinskakol.isVisible = true
                marSestanovac.isVisible = true
            }

            "71" -> {
                lin71 = map.addPolyline(Storage5.vr710)

                marSolinskakol.isVisible = true
                marSutina.isVisible = true
            }

            "72" -> {
                lin72 = map.addPolyline(Storage5.vr720)

                marSolinskakol.isVisible = true
                marSutina.isVisible = true
                marZelovo.isVisible = true
            }

            "76" -> {
                lin76 = map.addPolyline(Storage5.vr760)

                marSolinskakol.isVisible = true
                marCrivac.isVisible = true
                marKljaci.isVisible = true
            }

            "77" -> {
                lin77 = map.addPolyline(Storage6.vr770)

                marSolinskakol.isVisible = true
                marMuc.isVisible = true
                marCrivac.isVisible = true
            }

            "80" -> {
                lin80 = map.addPolyline(Storage.vr800)
                linVrkadglav = map.addPolyline(Storage6.vrkadglav)

                marAksplit.isVisible = true
                marKadglav.isVisible = true
                marDrnis.isVisible = true
            }

            "81" -> {
                lin81 = map.addPolyline(Storage6.vr810)

                marSolinskakol.isVisible = true
                marBrstanovo.isVisible = true
                marNisko.isVisible = true
            }

            "73" -> {
                lin73 = map.addPolyline(Storage6.vr730)

                marSolinskakol.isVisible = true
                marOgorje.isVisible = true
                marMuc.isVisible = true

            }

            "86" -> {
                lin86 = map.addPolyline(Storage6.vr860)

                marSolinskakol.isVisible = true
                marKladnjice.isVisible = true
                marLecevica.isVisible = true
            }

            "20" -> {
                showToast()
            }

            "35" -> {
                showToast()
            }

            "88" -> {
                showToast()
            }

            "90" -> {
                showToast()
            }

            "91" -> {
                showToast()
            }

            "93" -> {
                showToast()
            }

            "" -> {
                showToast()
            }
        }
    }
    

    private fun removeLine(linija: String) {
        when (linija) {
            "1" -> {
                lin1.remove()

                marHnk.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marKman1.isVisible = false
                marKman2.isVisible = false
                marKman3.isVisible = false
                marKman4.isVisible = false
                marKocunar1.isVisible = false
                marKauflandj.isVisible = false
                marKauflands.isVisible = false
                marTommys.isVisible = false
                marBilla.isVisible = false
                marKocunar2.isVisible = false
                marKocunar3.isVisible = false
                marDomrata1.isVisible = false
                marSplitmall.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marStarine.isVisible = false
            }

            "2" -> {
                lin2.remove()

                marTrluka.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marDujmovaca.isVisible = false
                marPrima.isVisible = false
                marSjlukas.isVisible = false
                marSjlukaj.isVisible = false
                marPopravnidom.isVisible = false
                marPrometovagarazas.isVisible = false
                marBrda1.isVisible = false
                marPosta.isVisible = false
                marHerc1s.isVisible = false
                marHerc2j.isVisible = false
                marKsucurac.isVisible = false
            }

            "3" -> {
                lin3.remove()
                lin301.remove()

                // 310
                marLovrinac.isVisible = false
                marUlzbng6.isVisible = false
                marHeps.isVisible = false
                marPolj3s.isVisible = false
                marTesla.isVisible = false
                marPolj2s.isVisible = false
                marPolj1s.isVisible = false
                marAutohrv.isVisible = false
                marPoiss.isVisible = false
                marPazari.isVisible = false
                marOpcinai.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marPosta.isVisible = false
                marHerc2j.isVisible = false
                marBrda1.isVisible = false
                marPeugj.isVisible = false
                marRotorbrda.isVisible = false

                // 320
                // marRotorbrda.isVisible = false
                marMostarska.isVisible = false
                marMostarska1.isVisible = false
                marPeugs.isVisible = false
                marPrometovagarazas.isVisible = false
                marHerc1s.isVisible = false
                marGurui.isVisible = false
                marZabaz.isVisible = false
                marPazarz.isVisible = false
                marOpcinaz.isVisible = false
                marPopajpois.isVisible = false
                marFirule.isVisible = false
                marPolj1j.isVisible = false
                marPolj2j.isVisible = false
                marPolj3j.isVisible = false
                marHepj.isVisible = false
                //marLovrinac.isVisible = false
                marLovrinacj.isVisible = false
                marUlzbng5.isVisible = false
            }

            "5" -> {
                lin5.remove()
                linPoj.isVisible = false

                marHnk.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marUlzbng7.isVisible = false
                marUlzbng8.isVisible = false
                marDracevac.isVisible = false
            }

            "5A" -> {
                lin5A.remove()

                marHnk.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marUlzbng7.isVisible = false
                marUlzbng8.isVisible = false
                marDracevac.isVisible = false
            }

            "6" -> {
                lin6.remove()
                lin61.isVisible = false

                marHnk.isVisible = false
                marOpcinaz.isVisible = false
                marKila.isVisible = false
                marVrboran.isVisible = false
                marVuk1s.isVisible = false
                marVuk2s.isVisible = false
                marVuk2j.isVisible = false
                marVuk3s.isVisible = false
                marVuk3j.isVisible = false
                marVukvicta.isVisible = false
                marVuksucidar.isVisible = false
                marVukkampus.isVisible = false
                marVukkampusj.isVisible = false
                marVuk4s.isVisible = false
                marVuk4j.isVisible = false
                marDubrov3.isVisible = false
                marVuk6.isVisible = false
                marVuk7.isVisible = false
                marVukopcina.isVisible = false
                marMaz1.isVisible = false
                marMaz2.isVisible = false
            }

            "7" -> {
                lin7.remove()

                marBracka1.isVisible = false
                marBracka2.isVisible = false
                marZnjan.isVisible = false
                marVel1.isVisible = false
                marPlavazgrada.isVisible = false
                marPlavazgrada1.isVisible = false
                marVel2.isVisible = false
                marVel3.isVisible = false
                marVel4.isVisible = false
                marVel5.isVisible = false
                marVelvicta.isVisible = false
                marMcgrgo.isVisible = false
                marVel6i.isVisible = false
                marVel6z.isVisible = false
                marVel7z.isVisible = false
                marVel7i.isVisible = false
                marMedeniznjani.isVisible = false
                marDubrov1.isVisible = false
                marCfvrime.isVisible = false
                marJokerj.isVisible = false
                marJokers.isVisible = false
                marBrodogradiliste.isVisible = false
                marLidllora.isVisible = false
                marPlavakava.isVisible = false
                marBazeni.isVisible = false
                marZf1.isVisible = false
                marPoljud.isVisible = false
                marKineskizid.isVisible = false
                marMurall.isVisible = false
                marMurall1.isVisible = false
                marTunel.isVisible = false
                marTunelj.isVisible = false
                marGunjecin.isVisible = false
                marPutmeja.isVisible = false
                marMedenizvoncac.isVisible = false
                marDrazanac.isVisible = false
            }
            "8" -> {
                lin8.remove()
                linFix8.remove()
                linPoj.isVisible = false

                marTunel.isVisible = false
                marTunelj.isVisible = false
                marGunjecin.isVisible = false
                marPutmeja.isVisible = false
                marMedenizvoncac.isVisible = false
                marDrazanac.isVisible = false
                marVertigo.isVisible = false
                marVertigo1.isVisible = false
                marStariplac.isVisible = false
                marStariplaci.isVisible = false
                marSportshop.isVisible = false
                marSud.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marVel7z.isVisible = false
                marVel7i.isVisible = false
                marMedeniznjani.isVisible = false
                marBracka1.isVisible = false
                marBracka2.isVisible = false
                marZnjan.isVisible = false
            }

            "9" -> {
                lin9.remove()

                marKaufland.isVisible = false
                marKauflandi.isVisible = false
                marPeugj.isVisible = false
                marRotorbrda.isVisible = false
                marZagputs.isVisible = false
                marZagputj.isVisible = false
                marZagput9.isVisible = false
                marVel1.isVisible = false
                marPlavazgrada.isVisible = false
                marPlavazgrada1.isVisible = false
                marVel2.isVisible = false
                marVel3.isVisible = false
                marVel4.isVisible = false
                marDubrov1.isVisible = false
                marCfvrime.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marTrluka.isVisible = false
            }

            "10" -> {
                lin10.remove()

                marTrluka.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marKman1.isVisible = false
                marKman2.isVisible = false
                marKman3.isVisible = false
                marKman4.isVisible = false
                marKocunar1.isVisible = false
                marKauflandj.isVisible = false
                marKauflands.isVisible = false
                marTommys.isVisible = false
                marBilla.isVisible = false
                marKocunar2.isVisible = false
                marKocunar3.isVisible = false
                marDomrata1.isVisible = false
                marSplitmall.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marJapirko.isVisible = false

            }

            "11" -> {
                lin11.remove()
                linPoj.isVisible = false

                marHnk.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marBb2.isVisible = false
                marBb3.isVisible = false
                marFesb.isVisible = false
                marSplit3.isVisible = false
                marDjackidoms.isVisible = false
                marDjackidomj.isVisible = false
                marVuk3s.isVisible = false
                marVuk3j.isVisible = false
                marVukvicta.isVisible = false
                marMcgrgo.isVisible = false
                marVel6i.isVisible = false
                marVel6z.isVisible = false
                marPujankes.isVisible = false
                marPujankej.isVisible = false
                marPujanke1s.isVisible = false
                marPujanke1j.isVisible = false
                marZagputs.isVisible = false
                marZagputj.isVisible = false
                marPeugj.isVisible = false
                marRotorbrda.isVisible = false
                marKaufland.isVisible = false
                marKauflandi.isVisible = false
            }

            "12" -> {
                lin12.remove()

                marBene.isVisible = false
                marTunelj.isVisible = false
                marMihanoviceva.isVisible = false
                marMihanoviceva1.isVisible = false
                marSupilova.isVisible = false
                marSupilova1.isVisible = false
                marGunjecin.isVisible = false
                marSetalisteim.isVisible = false
                marSetalisteim1.isVisible = false
                marSetalisteim2.isVisible = false
                marMedenizvoncac.isVisible = false
                marMedenizvoncac1.isVisible = false
                marZapobala.isVisible = false
                marZapobala1.isVisible = false
                marSvfrane.isVisible = false
            }

            "14" -> {
                lin14.remove()
                linFix.remove()
                linPoj.isVisible = false

                marRotorbrda.isVisible = false
                marMostarska.isVisible = false
                marMostarska1.isVisible = false
                marPeugs.isVisible = false
                marPeugj.isVisible = false
                marPrometovagarazas.isVisible = false
                marPosta.isVisible = false
                marBrda1.isVisible = false
                marHerc1s.isVisible = false
                marHerc2j.isVisible = false
                marDubrov1.isVisible = false
                marCfvrime.isVisible = false
                marDubrov2.isVisible = false
                marDubrov3.isVisible = false
                marDubrov4.isVisible = false
                marDubrov5.isVisible = false
                marDubrov6.isVisible = false
                marDubrov7.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marHnk.isVisible = false
            }

            "15" -> {
                lin15.remove()
                lin61.isVisible = false

                marTrluka.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marOpcinaz.isVisible = false
                marVuk6.isVisible = false
                marVuk7.isVisible = false
                marVukopcina.isVisible = false
                marMaz1.isVisible = false
                marMaz2.isVisible = false
                marVuk5.isVisible = false
                marVuk4s.isVisible = false
                marDubrov3.isVisible = false
                marBb1.isVisible = false
                marMioc.isVisible = false
                marBb2.isVisible = false
                marBb3.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marVel7z.isVisible = false
                marVel7i.isVisible = false
                marMedeniznjani.isVisible = false
                marMedeniznjanz.isVisible = false
                marPlaza1.isVisible = false
                marPlaza2.isVisible = false
                marPapeivanapavla1.isVisible = false
                marPapeivanapavla2.isVisible = false
                marDuilovo.isVisible = false
                marDuilovo1.isVisible = false
                marDuilovo2.isVisible = false
            }

            "16" -> {
                lin16.remove()

                marHnk.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marKman1.isVisible = false
                marKman2.isVisible = false
                marKman3.isVisible = false
                marKman4.isVisible = false
                marKocunar1.isVisible = false
                marKauflandj.isVisible = false
                marKauflands.isVisible = false
                marTommys.isVisible = false
                marBilla.isVisible = false
                marKocunar2.isVisible = false
                marKocunar3.isVisible = false
                marDomrata1.isVisible = false
                marSplitmall.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marNincevici.isVisible = false
            }

            "17" -> {
                lin17.remove()
                linPojs.isVisible = false

                marLorai.isVisible = false
                marLoraz.isVisible = false
                marBazeni.isVisible = false
                marZf1.isVisible = false
                marUlhrvmorn.isVisible = false
                marUlhrvmorn1s.isVisible = false
                marUlhrvmorn1j.isVisible = false
                marSplitstadion.isVisible = false
                marUlhrvmorn3.isVisible = false
                marPoljud.isVisible = false
                marKineskizid.isVisible = false
                marMurall.isVisible = false
                marMurall1.isVisible = false
                marInaspinut.isVisible = false
                marInaspinut1.isVisible = false
                marSpinut.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marBb2.isVisible = false
                marFesb.isVisible = false
                marDjackidoms.isVisible = false
                marVel7i.isVisible = false
                marOlly.isVisible = false
                marTrstenik.isVisible = false
                marMolhrv.isVisible = false
                marPazari.isVisible = false
                marPazarz.isVisible = false
            }

            "18" -> {
                lin18.remove()

                marHnk.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marMathrv1s.isVisible = false
                marMathrv1j.isVisible = false
                marKotekss.isVisible = false
                marKoteksj.isVisible = false
                marUlslobodei.isVisible = false
                marUlslobodez.isVisible = false
                marFesb.isVisible = false
                marSplit3.isVisible = false
                marDjackidoms.isVisible = false
                marDjackidomj.isVisible = false
                marVuk3s.isVisible = false
                marVuk3j.isVisible = false
                marVukvicta.isVisible = false
                marMcgrgo.isVisible = false
                marVel6i.isVisible = false
                marVel6z.isVisible = false
                marVuk1s.isVisible = false
                marVuk1j.isVisible = false
                marVuk2s.isVisible = false
                marVuk2j.isVisible = false
                marVuk3s.isVisible = false
                marVuk3j.isVisible = false
                marBrnik1.isVisible = false
                marBrnik.isVisible = false
            }

            "21" -> {
                lin21.remove()

                marTunelj.isVisible = false
                marMihanoviceva.isVisible = false
                marMihanoviceva1.isVisible = false
                marSupilova.isVisible = false
                marSupilova1.isVisible = false
                marGunjecin.isVisible = false
                marMedenizvoncac.isVisible = false
                marMedenizvoncac1.isVisible = false
                marZapobala.isVisible = false
                marZapobala1.isVisible = false
                marSvfrane.isVisible = false
            }

            "22" -> {
                lin22.remove()

                marHnk.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marKman1.isVisible = false
                marKman2.isVisible = false
                marKman3.isVisible = false
                marKman4.isVisible = false
                marKocunar1.isVisible = false
                marKauflandj.isVisible = false
                marKauflands.isVisible = false
                marTommys.isVisible = false
                marBilla.isVisible = false
                marKocunar2.isVisible = false
                marKocunar3.isVisible = false
                marDomrata1.isVisible = false
                marSplitmall.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marKlis.isVisible = false
            }

            "23" -> {
                lin23.remove()

                marHnk.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marGuruz.isVisible = false
                marGurui.isVisible = false
                marKman1.isVisible = false
                marKman2.isVisible = false
                marKman3.isVisible = false
                marKman4.isVisible = false
                marKocunar1.isVisible = false
                marKauflandj.isVisible = false
                marKauflands.isVisible = false
                marTommys.isVisible = false
                marBilla.isVisible = false
                marKocunar2.isVisible = false
                marKocunar3.isVisible = false
                marDomrata1.isVisible = false
                marSplitmall.isVisible = false
                marZbngs.isVisible = false
                marZbngj.isVisible = false
                marSolin.isVisible = false
            }

            "24" -> {
                lin24.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marUlzbng6.isVisible = false
                marUlzbng5.isVisible = false
                marTtts.isVisible = false
            }

            "25" -> {
                lin25.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marStobrec.isVisible = false
            }

            "26" -> {
                lin26.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marKamen.isVisible = false
            }

            "27" -> {
                lin27.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marZrnovnica.isVisible = false
            }

            "28" -> {
                lin28.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marDubrava.isVisible = false
            }

            "29" -> {
                lin29.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marNaklice.isVisible = false
            }

            "31" -> {
                lin31.remove()
                marSolinskakol.isVisible = false
                marVranjic.isVisible = false

            }

            "32" -> {
                lin32.remove()
                marSolinskakol.isVisible = false
                marKucine.isVisible = false
            }

            "33" -> {
                lin33.remove()
                marSolinskakol.isVisible = false
                marKosa.isVisible = false
            }

            "34" -> {
                lin34.remove()
                marSolinskakol.isVisible = false
                marKlis.isVisible = false
            }

            "39" -> {
                lin39.remove()
                linPoj.isVisible = false

                marLorai.isVisible = false
                marLoraz.isVisible = false
                marBazeni.isVisible = false
                marZf1.isVisible = false
                marUlhrvmorn.isVisible = false
                marUlhrvmorn1s.isVisible = false
                marUlhrvmorn1j.isVisible = false
                marSplitstadion.isVisible = false
                marZabaz.isVisible = false
                marZabai.isVisible = false
                marOpcinaz.isVisible = false
                marOpcinai.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marPopajpois.isVisible = false
                marPoiss.isVisible = false
                marAutohrv.isVisible = false
                marFirule.isVisible = false
                marPolj1s.isVisible = false
                marPolj1j.isVisible = false
                marPolj2s.isVisible = false
                marPolj2j.isVisible = false
                marTesla.isVisible = false
                marTeslaj.isVisible = false
                marPolj3s.isVisible = false
                marPolj3j.isVisible = false
                marHeps.isVisible = false
                marHepj.isVisible = false
                marUlzbng6.isVisible = false
                marUlzbng5.isVisible = false
                marTtts.isVisible = false
            }

            "40" -> {
                lin40.remove()
                lin61.isVisible = false

                marTrluka.isVisible = false
                marPazarz.isVisible = false
                marPazari.isVisible = false
                marOpcinaz.isVisible = false
                marKila.isVisible = false
                marVrboran.isVisible = false
                marVuk1s.isVisible = false
                marVuk2s.isVisible = false
                marVuk2j.isVisible = false
                marVuk3s.isVisible = false
                marVuk3j.isVisible = false
                marVukvicta.isVisible = false
                marVuksucidar.isVisible = false
                marVukkampus.isVisible = false
                marVukkampusj.isVisible = false
                marVuk4s.isVisible = false
                marVuk4j.isVisible = false
                marDubrov3.isVisible = false
                marVuk6.isVisible = false
                marVuk7.isVisible = false
                marVukopcina.isVisible = false
                marMaz1.isVisible = false
                marMaz2.isVisible = false
            }

            "30" -> {
                lin30.remove()
                marSolinskakol.isVisible = false
                marMutogras.isVisible = false
            }

            "37" -> {
                lin37.remove()
                marSolinskakol.isVisible = false
                marTrogir.isVisible = false
            }

            "38" -> {
                lin38.remove()
                marSolinskakol.isVisible = false
                marResnik.isVisible = false
            }

            "60" -> {
                lin60.remove()
                marSolinskakol.isVisible = false
                marOmis.isVisible = false
            }

            "67" -> {
                lin67.remove()
                marSolinskakol.isVisible = false
                marGornjidolac.isVisible = false
            }

            "36" -> {
                lin36.remove()
                marSolinskakol.isVisible = false
                marKoprivno.isVisible = false
            }

            "68" -> {
                lin68.remove()
                linPoj.isVisible = false

                marSolinskakol.isVisible = false
                marSestanovac.isVisible = false
                marKovacevici.isVisible = false
            }

            "69" -> {
                lin69.remove()

                marSolinskakol.isVisible = false
                marSestanovac.isVisible = false
            }

            "71" -> {
                lin71.remove()

                marSolinskakol.isVisible = false
                marSutina.isVisible = false
            }

            "72" -> {
                lin72.remove()

                marSolinskakol.isVisible = false
                marSutina.isVisible = false
                marZelovo.isVisible = false
            }

            "76" -> {
                lin76.remove()

                marSolinskakol.isVisible = false
                marCrivac.isVisible = false
                marKljaci.isVisible = false
            }

            "77" -> {
                lin77.remove()

                marSolinskakol.isVisible = false
                marMuc.isVisible = false
                marCrivac.isVisible = false
            }

            "80" -> {
                lin80.remove()
                linVrkadglav.remove()

                marAksplit.isVisible = false
                marKadglav.isVisible = false
                marDrnis.isVisible = false
            }

            "81" -> {
                lin81.remove()

                marSolinskakol.isVisible = false
                marBrstanovo.isVisible = false
                marNisko.isVisible = false
            }

            "73" -> {
                lin73.remove()

                marSolinskakol.isVisible = false
                marOgorje.isVisible = false
                marMuc.isVisible = false
            }

            "86" -> {
                lin86.remove()

                marSolinskakol.isVisible = false
                marKladnjice.isVisible = false
                marLecevica.isVisible = false
            }

        }
    }


    /**
     * Init onInfoWindowClick za pojedini markere, array
     * za pojedinu stanicu nalazi se u Stanice gmaps utils klasi
     */
    override fun onInfoWindowClick(marker: Marker) {
        when (marker) {
            marHnk -> createMarkerAlert(Stanice.arHnk)
            marOpcinai -> createMarkerAlert(Stanice.arOpcinai)
            marOpcinaz -> createMarkerAlert(Stanice.arOpcinaz)
            marSolinskakol -> createMarkerAlert(Stanice.arSolinskakol)
            marPazarz -> createMarkerAlert(Stanice.arPazarz)
            marPazari -> createMarkerAlert(Stanice.arPazari)
            marTrluka -> createMarkerAlert(Stanice.arTrluka)
            marKaufland -> createMarkerAlert(Stanice.arKaufland)
            marSpinut -> createMarkerAlert(Stanice.arSpinut)
            marLorai -> createMarkerAlert(Stanice.arLorai)
            marLoraz -> createMarkerAlert(Stanice.arLoraz)
            marMedenizvoncac -> createMarkerAlert(Stanice.arMedenizvoncac)
            marSvfrane -> createMarkerAlert(Stanice.arSvfrane)
            marBene -> createMarkerAlert(Stanice.arBene)
            marDuilovo -> createMarkerAlert(Stanice.arDuilovo)
            marKila -> createMarkerAlert(Stanice.arKila)
            marBrnik -> createMarkerAlert(Stanice.arBrnik)
            marFirule -> createMarkerAlert(Stanice.arFirule)
            marLovrinac -> createMarkerAlert(Stanice.arLovrinac)
            marZnjan -> createMarkerAlert(Stanice.arZnjan)
            marTrstenik -> createMarkerAlert(Stanice.arTrstenik)
            marZabaz -> createMarkerAlert(Stanice.arZabaz)
            marZabai -> createMarkerAlert(Stanice.arZabai)
            marGuruz -> createMarkerAlert(Stanice.arGuruz)
            marGurui -> createMarkerAlert(Stanice.arGurui)
            marKman1 -> createMarkerAlert(Stanice.arKman1)
            marKman2 -> createMarkerAlert(Stanice.arKman2)
            marKman3 -> createMarkerAlert(Stanice.arKman3)
            marKman4 -> createMarkerAlert(Stanice.arKman4)
            marKocunar1 -> createMarkerAlert(Stanice.arKocunar1)
            marKauflandj -> createMarkerAlert(Stanice.arKauflandj)
            marKauflands -> createMarkerAlert(Stanice.arKauflands)
            marTommys -> createMarkerAlert(Stanice.arTommys)
            marBilla -> createMarkerAlert(Stanice.arBilla)
            marKocunar2 -> createMarkerAlert(Stanice.arKocunar2)
            marKocunar3 -> createMarkerAlert(Stanice.arKocunar3)
            marDomrata1 -> createMarkerAlert(Stanice.arDomrata1)
            marSplitmall -> createMarkerAlert(Stanice.arSplitmall)
            marZbngs -> createMarkerAlert(Stanice.arZbngs)
            marZbngj -> createMarkerAlert(Stanice.arZbngj)
            marDujmovaca -> createMarkerAlert(Stanice.arDujmovaca)
            marPrima -> createMarkerAlert(Stanice.arPrima)
            marSjlukas -> createMarkerAlert(Stanice.arSjlukas)
            marSjlukaj -> createMarkerAlert(Stanice.arSjlukaj)
            marPopravnidom -> createMarkerAlert(Stanice.arPopravnidom)
            marPrometovagarazas -> createMarkerAlert(Stanice.arPrometovagaraza)
            marBrda1 -> createMarkerAlert(Stanice.arBrda1)
            marPosta -> createMarkerAlert(Stanice.arPosta)
            marHerc1s -> createMarkerAlert(Stanice.arHerc1s)
            marHerc2j -> createMarkerAlert(Stanice.arHerc2j)
            marPeugs -> createMarkerAlert(Stanice.arPeugs)
            marPeugj -> createMarkerAlert(Stanice.arPeugj)
            marRotorbrda -> createMarkerAlert(Stanice.arRotorbrda)
            marMostarska -> createMarkerAlert(Stanice.arMostarska)
            marMostarska1 -> createMarkerAlert(Stanice.arMostarska1)
            marKauflandi -> createMarkerAlert(Stanice.arKauflandi)
            marZagputs -> createMarkerAlert(Stanice.arZagputs)
            marZagputj -> createMarkerAlert(Stanice.arZagputj)
            marZagput9 -> createMarkerAlert(Stanice.arZagput9)
            marPujankes -> createMarkerAlert(Stanice.arPujankes)
            marPujankej -> createMarkerAlert(Stanice.arPujankej)
            marPujanke1s -> createMarkerAlert(Stanice.arPujanke1s)
            marPujanke1j -> createMarkerAlert(Stanice.arPujanke1j)
            marJokers -> createMarkerAlert(Stanice.arJokers)
            marJokerj -> createMarkerAlert(Stanice.arJokerj)
            marBrodogradiliste -> createMarkerAlert(Stanice.arBrodogradiliste)
            marLidllora -> createMarkerAlert(Stanice.arLidllora)
            marPlavakava -> createMarkerAlert(Stanice.arPlavakava)
            marBazeni -> createMarkerAlert(Stanice.arBazeni)
            marZf1 -> createMarkerAlert(Stanice.arZf1)
            marUlhrvmorn -> createMarkerAlert(Stanice.arUlhrvmorn)
            marUlhrvmorn1s -> createMarkerAlert(Stanice.arUlhrvmorn1s)
            marUlhrvmorn1j -> createMarkerAlert(Stanice.arUlhrvmorn1j)
            marSplitstadion -> createMarkerAlert(Stanice.arSplitstadion)
            marUlhrvmorn3 -> createMarkerAlert(Stanice.arUlhrvmorn3)
            marPoljud -> createMarkerAlert(Stanice.arPoljud)
            marKineskizid -> createMarkerAlert(Stanice.arKineskizid)
            marMurall -> createMarkerAlert(Stanice.arMurall)
            marMurall1 -> createMarkerAlert(Stanice.arMurall1)
            marInaspinut -> createMarkerAlert(Stanice.arInaspinut)
            marInaspinut1 -> createMarkerAlert(Stanice.arInaspinut1)
            marTunel -> createMarkerAlert(Stanice.arTunel)
            marTunelj -> createMarkerAlert(Stanice.arTunelj)
            marMihanoviceva -> createMarkerAlert(Stanice.arMihanoviceva)
            marMihanoviceva1 -> createMarkerAlert(Stanice.arMihanoviceva1)
            marSupilova -> createMarkerAlert(Stanice.arSupilova)
            marSupilova1 -> createMarkerAlert(Stanice.arSupilova1)
            marGunjecin -> createMarkerAlert(Stanice.arGunjecin)
            marPutmeja -> createMarkerAlert(Stanice.arPutmeja)
            marSetalisteim -> createMarkerAlert(Stanice.arSetalisteim)
            marSetalisteim1 -> createMarkerAlert(Stanice.arSetalisteim1)
            marSetalisteim2 -> createMarkerAlert(Stanice.arSetalisteim2)
            marMedenizvoncac1 -> createMarkerAlert(Stanice.arMedenizvoncac1)
            marDrazanac -> createMarkerAlert(Stanice.arDrazanac)
            marZapobala -> createMarkerAlert(Stanice.arZapobala)
            marZapobala1 -> createMarkerAlert(Stanice.arZapobala1)
            marVertigo -> createMarkerAlert(Stanice.arVertigo)
            marVertigo1 -> createMarkerAlert(Stanice.arVertigo1)
            marStariplac -> createMarkerAlert(Stanice.arStariplac)
            marStariplaci -> createMarkerAlert(Stanice.arStariplaci)
            marSportshop -> createMarkerAlert(Stanice.arSportshop)
            marSud -> createMarkerAlert(Stanice.arSud)
            marDubrov1 -> createMarkerAlert(Stanice.arDubrov1)
            marCfvrime -> createMarkerAlert(Stanice.arCfvrime)
            marDubrov2 -> createMarkerAlert(Stanice.arDubrov2)
            marDubrov3 -> createMarkerAlert(Stanice.arDubrov3)
            marDubrov4 -> createMarkerAlert(Stanice.arDubrov4)
            marDubrov5 -> createMarkerAlert(Stanice.arDubrov5)
            marDubrov6 -> createMarkerAlert(Stanice.arDubrov6)
            marDubrov7 -> createMarkerAlert(Stanice.arDubrov7)
            marVel1 -> createMarkerAlert(Stanice.arVel1)
            marPlavazgrada -> createMarkerAlert(Stanice.arPlavazgrada)
            marPlavazgrada1 -> createMarkerAlert(Stanice.arPlavazgrada1)
            marVel2 -> createMarkerAlert(Stanice.arVel2)
            marVel3 -> createMarkerAlert(Stanice.arVel3)
            marVel4 -> createMarkerAlert(Stanice.arVel4)
            marVel5 -> createMarkerAlert(Stanice.arVel5)
            marVelvicta -> createMarkerAlert(Stanice.arVelvicta)
            marMcgrgo -> createMarkerAlert(Stanice.arMcgrgo)
            marVel6i -> createMarkerAlert(Stanice.arVel6i)
            marVel6z -> createMarkerAlert(Stanice.arVel6z)
            marVel7z -> createMarkerAlert(Stanice.arVel7z)
            marVel7i -> createMarkerAlert(Stanice.arVel7i)
            marMedeniznjani -> createMarkerAlert(Stanice.arMedeniznjani)
            marMedeniznjanz -> createMarkerAlert(Stanice.arMedeniznjanz)
            marPlaza1 -> createMarkerAlert(Stanice.arPlaza1)
            marPlaza2 -> createMarkerAlert(Stanice.arPlaza2)
            marPapeivanapavla1 -> createMarkerAlert(Stanice.arPapeivanapavla1)
            marPapeivanapavla2 -> createMarkerAlert(Stanice.arPapeivanapavla2)
            marDuilovo1 -> createMarkerAlert(Stanice.arDuilovo1)
            marDuilovo2 -> createMarkerAlert(Stanice.arDuilovo2)
            marVrboran -> createMarkerAlert(Stanice.arVrboran)
            marVuk1s -> createMarkerAlert(Stanice.arVuk1s)
            marVuk1j -> createMarkerAlert(Stanice.arVuk1j)
            marVuk2s -> createMarkerAlert(Stanice.arVuk2s)
            marVuk2j -> createMarkerAlert(Stanice.arVuk2j)
            marVuk3s -> createMarkerAlert(Stanice.arVuk3s)
            marVuk3j -> createMarkerAlert(Stanice.arVuk3j)
            marVukvicta -> createMarkerAlert(Stanice.arVukvicta)
            marVuksucidar -> createMarkerAlert(Stanice.arVuksucidar)
            marVukkampus -> createMarkerAlert(Stanice.arVukkampus)
            marVukkampusj -> createMarkerAlert(Stanice.arVukkampusj)
            marVuk4s -> createMarkerAlert(Stanice.arVuk4s)
            marVuk4j -> createMarkerAlert(Stanice.arVuk4j)
            marVuk5 -> createMarkerAlert(Stanice.arVuk5)
            marVuk6 -> createMarkerAlert(Stanice.arVuk6)
            marVuk7 -> createMarkerAlert(Stanice.arVuk7)
            marVukopcina -> createMarkerAlert(Stanice.arVukopcina)
            marMaz1 -> createMarkerAlert(Stanice.arMaz1)
            marMaz2 -> createMarkerAlert(Stanice.arMaz2)
            marBrnik1 -> createMarkerAlert(Stanice.arBrnik1)
            marFesb -> createMarkerAlert(Stanice.arFesb)
            marSplit3 -> createMarkerAlert(Stanice.arSplit3)
            marDjackidoms -> createMarkerAlert(Stanice.arDjackidoms)
            marDjackidomj -> createMarkerAlert(Stanice.arDjackidomj)
            marMathrv1s -> createMarkerAlert(Stanice.arMathrv1s)
            marMathrv1j -> createMarkerAlert(Stanice.arMathrv1j)
            marKotekss -> createMarkerAlert(Stanice.arKotekss)
            marKoteksj -> createMarkerAlert(Stanice.arKoteksj)
            marUlslobodei -> createMarkerAlert(Stanice.arUlslobodei)
            marUlslobodez -> createMarkerAlert(Stanice.arUlslobodez)
            marBb1 -> createMarkerAlert(Stanice.arBb1)
            marMioc -> createMarkerAlert(Stanice.arMioc)
            marBb2 -> createMarkerAlert(Stanice.arBb2)
            marBb3 -> createMarkerAlert(Stanice.arBb3)
            marPopajpois -> createMarkerAlert(Stanice.arPopajpois)
            marPoiss -> createMarkerAlert(Stanice.arPoiss)
            marAutohrv -> createMarkerAlert(Stanice.arAutohrv)
            marPolj1s -> createMarkerAlert(Stanice.arPolj1s)
            marPolj1j -> createMarkerAlert(Stanice.arPolj1j)
            marPolj2s -> createMarkerAlert(Stanice.arPolj2s)
            marPolj2j -> createMarkerAlert(Stanice.arPolj2j)
            marTesla -> createMarkerAlert(Stanice.arTesla)
            marTeslaj -> createMarkerAlert(Stanice.arTeslaj)
            marPolj3s -> createMarkerAlert(Stanice.arPolj3s)
            marPolj3j -> createMarkerAlert(Stanice.arPolj3j)
            marHeps -> createMarkerAlert(Stanice.arHeps)
            marHepj -> createMarkerAlert(Stanice.arHepj)
            marUlzbng5 -> createMarkerAlert(Stanice.arUlzbng5)
            marUlzbng6 -> createMarkerAlert(Stanice.arUlzbng6)
            marUlzbng7 -> createMarkerAlert(Stanice.arUlzbng7)
            marUlzbng8 -> createMarkerAlert(Stanice.arUlzbng8)
            marLovrinacj -> createMarkerAlert(Stanice.arLovrinacj)
            marBracka1 -> createMarkerAlert(Stanice.arBracka1)
            marBracka2 -> createMarkerAlert(Stanice.arBracka2)
            marOlly -> createMarkerAlert(Stanice.arOlly)
            marMolhrv -> createMarkerAlert(Stanice.arMolhrv)
            marKsucurac -> createMarkerAlert(Stanice.arKsucurac)
            marStarine -> createMarkerAlert(Stanice.arStarine)
            marDracevac -> createMarkerAlert(Stanice.arDracevac)
            marJapirko -> createMarkerAlert(Stanice.arJapirko)
            marNincevici -> createMarkerAlert(Stanice.arNincevici)
            marKlis -> createMarkerAlert(Stanice.arKlis)
            marSolin -> createMarkerAlert(Stanice.arSolin)
            marTtts -> createMarkerAlert(Stanice.arTtts)
            marStobrec -> createMarkerAlert(Stanice.arStobrec)
            marKamen -> createMarkerAlert(Stanice.arKamen)
            marZrnovnica -> createMarkerAlert(Stanice.arZrnovnica)
            marDubrava -> createMarkerAlert(Stanice.arDubrava)
            marNaklice -> createMarkerAlert(Stanice.arNaklice)
            marMutogras -> createMarkerAlert(Stanice.arMutogras)
            marVranjic -> createMarkerAlert(Stanice.arVranjic)
            marKucine -> createMarkerAlert(Stanice.arKucine)
            marKosa -> createMarkerAlert(Stanice.arKosa)
            marKoprivno -> createMarkerAlert(Stanice.arKoprivno)
            marTrogir -> createMarkerAlert(Stanice.arTrogir)
            marResnik -> createMarkerAlert(Stanice.arResnik)
            marOmis -> createMarkerAlert(Stanice.arOmis)
            marGornjidolac -> createMarkerAlert(Stanice.arGornjidolac)
            marKovacevici -> createMarkerAlert(Stanice.arKovacevici)
            marSestanovac -> createMarkerAlert(Stanice.arSestanovac)
            marSutina -> createMarkerAlert(Stanice.arSutina)
            marZelovo -> createMarkerAlert(Stanice.arZelovo)
            marOgorje -> createMarkerAlert(Stanice.arOgorje)
            marMuc -> createMarkerAlert(Stanice.arMuc)
            marCrivac -> createMarkerAlert(Stanice.arCrivac)
            marKljaci -> createMarkerAlert(Stanice.arKljaci)
            marDrnis -> createMarkerAlert(Stanice.arDrnis)
            marKadglav -> createMarkerAlert(Stanice.arKadglav)
            marAksplit -> createMarkerAlert(Stanice.arAksplit)
            marBrstanovo -> createMarkerAlert(Stanice.arBrstanovo)
            marNisko -> createMarkerAlert(Stanice.arNisko)
            marKladnjice -> createMarkerAlert(Stanice.arKladnjice)
            marLecevica -> createMarkerAlert(Stanice.arLecevica)
        }
    }
    
    private fun initAllMarkers() {
        marHnk = addMarker(Stanice.hnk, "HNK", Stanice.arHnk)
        marOpcinaz = addMarker(Stanice.opcinaz, getString(R.string.gmaps_stanica_opcina), Stanice.arOpcinaz)
        marOpcinai = addMarker(Stanice.opcinai, getString(R.string.gmaps_stanica_opcina), Stanice.arOpcinai)
        marSolinskakol = addMarker(Stanice.solinskakol, getString(R.string.gmaps_stanica_solinska), Stanice.arSolinskakol)
        marPazarz = addMarker(Stanice.pazarz, "PAZAR", Stanice.arPazarz)
        marPazari = addMarker(Stanice.pazari, "PAZAR", Stanice.arPazari)
        marTrluka = addMarker(Stanice.trluka, getString(R.string.gmaps_stanica_tr_luka), Stanice.arTrluka)
        marKaufland = addMarker(Stanice.kaufland, "RAVNE NJIVE", Stanice.arKaufland)
        marSpinut = addMarker(Stanice.spinut, "SPINUT", Stanice.arSpinut)
        marLorai = addMarker(Stanice.lorai, "LORA", Stanice.arLorai)
        marLoraz = addMarker(Stanice.loraz, "LORA", Stanice.arLoraz)
        marMedenizvoncac = addMarker(Stanice.medenizvoncac, "Zvončac", Stanice.arMedenizvoncac)
        marSvfrane = addMarker(Stanice.svfrane, "SV.FRANE", Stanice.arSvfrane)
        marBene = addMarker(Stanice.bene, "BENE", Stanice.arBene)
        marDuilovo = addMarker(Stanice.duilovo, "Duilovo", Stanice.arDuilovo)
        marKila = addMarker(Stanice.kila, "Kila", Stanice.arKila)
        marBrnik = addMarker(Stanice.brnik, "BRNIK", Stanice.arBrnik)
        marFirule = addMarker(Stanice.firule, "FIRULE", Stanice.arFirule)
        marLovrinac = addMarker(Stanice.lovrinac, "Lovrinac", Stanice.arLovrinac)
        marZnjan = addMarker(Stanice.znjan, "Žnjan", Stanice.arZnjan)
        marTrstenik = addMarker(Stanice.trstenik, "Trstenik", Stanice.arTrstenik)
        marRotorbrda = addMarker(Stanice.rotorbrda,"BRDA", Stanice.arRotorbrda)


        marZabaz = addMarker(Stanice.zabaz, "", Stanice.arZabaz)
        marZabai = addMarker(Stanice.zabai, "", Stanice.arZabai)
        marGuruz = addMarker(Stanice.guruz, "", Stanice.arGuruz)
        marGurui = addMarker(Stanice.gurui, "", Stanice.arGurui)
        marKman1 = addMarker(Stanice.kman1, "", Stanice.arKman1)
        marKman2 = addMarker(Stanice.kman2, "", Stanice.arKman2)
        marKman3 = addMarker(Stanice.kman3, "", Stanice.arKman3)
        marKman4 = addMarker(Stanice.kman4, "", Stanice.arKman4)
        marKocunar1 = addMarker(Stanice.kocunar1, "", Stanice.arKocunar1)
        marKauflandj = addMarker(Stanice.kauflandj, "", Stanice.arKauflandj)
        marKauflands = addMarker(Stanice.kauflands, "", Stanice.arKauflands)
        marTommys = addMarker(Stanice.tommys, "", Stanice.arTommys)
        marBilla = addMarker(Stanice.billa, "", Stanice.arBilla)
        marKocunar2 = addMarker(Stanice.kocunar2, "", Stanice.arKocunar2)
        marKocunar3 = addMarker(Stanice.kocunar3, "", Stanice.arKocunar3)
        marDomrata1 = addMarker(Stanice.domrata1, "", Stanice.arDomrata1)
        marSplitmall = addMarker(Stanice.splitmall, "", Stanice.arSplitmall)
        marZbngs = addMarker(Stanice.zbngs, "", Stanice.arZbngs)
        marZbngj = addMarker(Stanice.zbngj, "", Stanice.arZbngj)
        marDujmovaca = addMarker(Stanice.dujmovaca, "", Stanice.arDujmovaca)
        marPrima = addMarker(Stanice.prima, "", Stanice.arPrima)
        marSjlukas = addMarker(Stanice.sjlukas, "", Stanice.arSjlukas)
        marSjlukaj = addMarker(Stanice.sjlukaj, "", Stanice.arSjlukaj)
        marPopravnidom = addMarker(Stanice.popravnidom, "", Stanice.arPopravnidom)
        marPrometovagarazas = addMarker(Stanice.prometovagarazas, "", Stanice .arPrometovagaraza)
        marBrda1 = addMarker(Stanice.brda1, "", Stanice.arBrda1)
        marPosta = addMarker(Stanice.posta, "", Stanice.arPosta)
        marHerc1s = addMarker(Stanice.herc1s, "", Stanice.arHerc1s)
        marHerc2j = addMarker(Stanice.herc2j, "", Stanice.arHerc2j)
        marPeugs = addMarker(Stanice.peugs, "", Stanice.arPeugs)
        marPeugj = addMarker(Stanice.peugj, "", Stanice.arPeugj)
        marMostarska = addMarker(Stanice.mostarska, "", Stanice.arMostarska)
        marMostarska1 = addMarker(Stanice.mostarska1, "", Stanice.arMostarska1)
        marKauflandi = addMarker(Stanice.kauflandi, "", Stanice.arKauflandi)
        marZagputs = addMarker(Stanice.zagputs, "", Stanice.arZagputs)
        marZagputj = addMarker(Stanice.zagputj, "", Stanice.arZagputj)
        marZagput9 = addMarker(Stanice.zagput9, "", Stanice.arZagput9)
        marPujankes = addMarker(Stanice.pujankes, "", Stanice.arPujankes)
        marPujankej = addMarker(Stanice.pujankej, "", Stanice.arPujankej)
        marPujanke1s = addMarker(Stanice.pujanke1s, "", Stanice.arPujanke1s)
        marPujanke1j = addMarker(Stanice.pujanke1j, "", Stanice.arPujanke1j)
        marJokers = addMarker(Stanice.jokers, "", Stanice.arJokers)
        marJokerj = addMarker(Stanice.jokerj, "", Stanice.arJokerj)
        marBrodogradiliste = addMarker(Stanice.brodogradiliste, "", Stanice.arBrodogradiliste)
        marLidllora = addMarker(Stanice.lidllora, "", Stanice.arLidllora)
        marPlavakava = addMarker(Stanice.plavakava, "", Stanice.arPlavakava)
        marBazeni = addMarker(Stanice.bazeni, "", Stanice.arBazeni)
        marZf1 = addMarker(Stanice.zf1, "", Stanice.arZf1)
        marUlhrvmorn = addMarker(Stanice.ulhrvmorn, "", Stanice.arUlhrvmorn)
        marUlhrvmorn1s = addMarker(Stanice.ulhrvmorn1s, "", Stanice.arUlhrvmorn1s)
        marUlhrvmorn1j = addMarker(Stanice.ulhrvmorn1j, "", Stanice.arUlhrvmorn1j)
        marSplitstadion = addMarker(Stanice.splitstadion, "", Stanice.arSplitstadion)
        marUlhrvmorn3 = addMarker(Stanice.ulhrvmorn3, "", Stanice.arUlhrvmorn3)
        marPoljud = addMarker(Stanice.poljud, "", Stanice.arPoljud)
        marKineskizid = addMarker(Stanice.kineskizid, "", Stanice.arKineskizid)
        marMurall = addMarker(Stanice.murall, "", Stanice.arMurall)
        marMurall1 = addMarker(Stanice.murall1, "", Stanice.arMurall1)
        marInaspinut = addMarker(Stanice.inaspinut, "", Stanice.arInaspinut)
        marInaspinut1 = addMarker(Stanice.inaspinut1, "", Stanice.arInaspinut1)
        marTunel = addMarker(Stanice.tunel, "", Stanice.arTunel)
        marTunelj = addMarker(Stanice.tunelj, "", Stanice.arTunelj)
        marMihanoviceva = addMarker(Stanice.mihanoviceva, "", Stanice.arMihanoviceva)
        marMihanoviceva1 = addMarker(Stanice.mihanoviceva1, "", Stanice.arMihanoviceva1)
        marSupilova = addMarker(Stanice.supilova, "", Stanice.arSupilova)
        marSupilova1 = addMarker(Stanice.supilova1, "", Stanice.arSupilova1)
        marGunjecin = addMarker(Stanice.gunjecin, "", Stanice.arGunjecin)
        marPutmeja = addMarker(Stanice.putmeja, "", Stanice.arPutmeja)
        marSetalisteim = addMarker(Stanice.setalisteim, "", Stanice.arSetalisteim)
        marSetalisteim1 = addMarker(Stanice.setalisteim1, "", Stanice.arSetalisteim1)
        marSetalisteim2 = addMarker(Stanice.setalisteim2, "", Stanice.arSetalisteim2)
        marMedenizvoncac1 = addMarker(Stanice.medenizvoncac1, "", Stanice.arMedenizvoncac1)
        marDrazanac = addMarker(Stanice.drazanac, "", Stanice.arDrazanac)
        marZapobala = addMarker(Stanice.zapobala, "", Stanice.arZapobala)
        marZapobala1 = addMarker(Stanice.zapobala1, "", Stanice.arZapobala1)
        marVertigo = addMarker(Stanice.vertigo, "", Stanice.arVertigo)
        marVertigo1 = addMarker(Stanice.vertigo1, "", Stanice.arVertigo1)
        marStariplac = addMarker(Stanice.stariplac, "", Stanice.arStariplac)
        marStariplaci = addMarker(Stanice.stariplaci, "", Stanice.arStariplaci)
        marSportshop = addMarker(Stanice.sportshop, "", Stanice.arSportshop)
        marSud = addMarker(Stanice.sud, "", Stanice.arSud)
        marDubrov1 = addMarker(Stanice.dubrov1, "", Stanice.arDubrov1)
        marCfvrime = addMarker(Stanice.cfvrime, "", Stanice.arCfvrime)
        marDubrov2 = addMarker(Stanice.dubrov2, "", Stanice.arDubrov2)
        marDubrov3 = addMarker(Stanice.dubrov3, "", Stanice.arDubrov3)
        marDubrov4 = addMarker(Stanice.dubrov4, "", Stanice.arDubrov4)
        marDubrov5 = addMarker(Stanice.dubrov5, "", Stanice.arDubrov5)
        marDubrov6 = addMarker(Stanice.dubrov6, "", Stanice.arDubrov6)
        marDubrov7 = addMarker(Stanice.dubrov7, "", Stanice.arDubrov7)
        marVel1 = addMarker(Stanice.vel1, "", Stanice.arVel1)
        marPlavazgrada = addMarker(Stanice.plavazgrada, "", Stanice.arPlavazgrada)
        marPlavazgrada1 = addMarker(Stanice.plavazgrada1, "", Stanice.arPlavazgrada1)
        marVel2 = addMarker(Stanice.vel2, "", Stanice.arVel2)
        marVel3 = addMarker(Stanice.vel3, "", Stanice.arVel3)
        marVel4 = addMarker(Stanice.vel4, "", Stanice.arVel4)
        marVel5 = addMarker(Stanice.vel5, "", Stanice.arVel5)
        marVelvicta = addMarker(Stanice.velvicta, "", Stanice.arVelvicta)
        marMcgrgo = addMarker(Stanice.mcgrgo, "", Stanice.arMcgrgo)
        marVel6i = addMarker(Stanice.vel6i, "", Stanice.arVel6i)
        marVel6z = addMarker(Stanice.vel6z, "", Stanice.arVel6z)
        marVel7z = addMarker(Stanice.vel7z, "", Stanice.arVel7z)
        marVel7i = addMarker(Stanice.vel7i, "", Stanice.arVel7i)
        marMedeniznjani = addMarker(Stanice.medeniznjani, "", Stanice.arMedeniznjani)
        marMedeniznjanz = addMarker(Stanice.medeniznjanz, "", Stanice.arMedeniznjanz)
        marPlaza1 = addMarker(Stanice.plaza1, "", Stanice.arPlaza1)
        marPlaza2 = addMarker(Stanice.plaza2, "", Stanice.arPlaza2)
        marPapeivanapavla1 = addMarker(Stanice.papeivanapavla1, "", Stanice.arPapeivanapavla1)
        marPapeivanapavla2 = addMarker(Stanice.papeivanapavla2, "", Stanice.arPapeivanapavla2)
        marDuilovo1 = addMarker(Stanice.duilovo1, "", Stanice.arDuilovo1)
        marDuilovo2 = addMarker(Stanice.duilovo2, "", Stanice.arDuilovo2)
        marVrboran = addMarker(Stanice.vrboran, "", Stanice.arVrboran)
        marVuk1s = addMarker(Stanice.vuk1s, "", Stanice.arVuk1s)
        marVuk1j = addMarker(Stanice.vuk1j, "", Stanice.arVuk1j)
        marVuk2s = addMarker(Stanice.vuk2s, "", Stanice.arVuk2s)
        marVuk2j = addMarker(Stanice.vuk2j, "", Stanice.arVuk2j)
        marVuk3s = addMarker(Stanice.vuk3s, "", Stanice.arVuk3s)
        marVuk3j = addMarker(Stanice.vuk3j, "", Stanice.arVuk3j)
        marVukvicta = addMarker(Stanice.vukvicta, "", Stanice.arVukvicta)
        marVuksucidar = addMarker(Stanice.vuksucidar, "", Stanice.arVuksucidar)
        marVukkampus = addMarker(Stanice.vukkampus, "", Stanice.arVukkampus)
        marVukkampusj = addMarker(Stanice.vukkampusj, "", Stanice.arVukkampusj)
        marVuk4s = addMarker(Stanice.vuk4s, "", Stanice.arVuk4s)
        marVuk4j = addMarker(Stanice.vuk4j, "", Stanice.arVuk4j)
        marVuk5 = addMarker(Stanice.vuk5, "", Stanice.arVuk5)
        marVuk6 = addMarker(Stanice.vuk6, "", Stanice.arVuk6)
        marVuk7 = addMarker(Stanice.vuk7, "", Stanice.arVuk7)
        marVukopcina = addMarker(Stanice.vukopcina, "", Stanice.arVukopcina)
        marMaz1 = addMarker(Stanice.maz1, "", Stanice.arMaz1)
        marMaz2 = addMarker(Stanice.maz2, "", Stanice.arMaz2)
        marBrnik1 = addMarker(Stanice.brnik1, "", Stanice.arBrnik1)
        marFesb = addMarker(Stanice.fesb, "", Stanice.arFesb)
        marSplit3 = addMarker(Stanice.split3, "", Stanice.arSplit3)
        marDjackidoms = addMarker(Stanice.djackidoms, "", Stanice.arDjackidoms)
        marDjackidomj = addMarker(Stanice.djackidomj, "", Stanice.arDjackidomj)
        marMathrv1s = addMarker(Stanice.mathrv1s, "", Stanice.arMathrv1s)
        marMathrv1j = addMarker(Stanice.mathrv1j, "", Stanice.arMathrv1j)
        marKotekss = addMarker(Stanice.kotekss, "", Stanice.arKotekss)
        marKoteksj = addMarker(Stanice.koteksj, "", Stanice.arKoteksj)
        marUlslobodei = addMarker(Stanice.ulslobodei, "", Stanice.arUlslobodei)
        marUlslobodez = addMarker(Stanice.ulslobodez, "", Stanice.arUlslobodez)
        marBb1 = addMarker(Stanice.bb1, "", Stanice.arBb1)
        marMioc = addMarker(Stanice.mioc, "", Stanice.arMioc)
        marBb2 = addMarker(Stanice.bb2, "", Stanice.arBb2)
        marBb3 = addMarker(Stanice.bb3, "", Stanice.arBb3)
        marPopajpois = addMarker(Stanice.popajpois, "", Stanice.arPopajpois)
        marPoiss = addMarker(Stanice.poiss, "", Stanice.arPoiss)
        marAutohrv = addMarker(Stanice.autohrv, "", Stanice.arAutohrv)
        marPolj1s = addMarker(Stanice.polj1s, "", Stanice.arPolj1s)
        marPolj1j = addMarker(Stanice.polj1j, "", Stanice.arPolj1j)
        marPolj2s = addMarker(Stanice.polj2s, "", Stanice.arPolj2s)
        marPolj2j = addMarker(Stanice.polj2j, "", Stanice.arPolj2j)
        marTesla = addMarker(Stanice.tesla, "", Stanice.arTesla)
        marTeslaj = addMarker(Stanice.teslaj, "", Stanice.arTeslaj)
        marPolj3s = addMarker(Stanice.polj3s, "", Stanice.arPolj3s)
        marPolj3j = addMarker(Stanice.polj3j, "", Stanice.arPolj3j)
        marHeps = addMarker(Stanice.heps, "", Stanice.arHeps)
        marHepj = addMarker(Stanice.hepj, "", Stanice.arHepj)
        marUlzbng5 = addMarker(Stanice.ulzbng5, "", Stanice.arUlzbng5)
        marUlzbng6 = addMarker(Stanice.ulzbng6, "", Stanice.arUlzbng6)
        marUlzbng7 = addMarker(Stanice.ulzbng7, "", Stanice.arUlzbng7)
        marUlzbng8 = addMarker(Stanice.ulzbng8, "", Stanice.arUlzbng8)
        marLovrinacj = addMarker(Stanice.lovrinacj, "", Stanice.arLovrinacj)
        marBracka1 = addMarker(Stanice.bracka1, "", Stanice.arBracka1)
        marBracka2 = addMarker(Stanice.bracka2, "", Stanice.arBracka2)
        marOlly = addMarker(Stanice.olly, "", Stanice.arOlly)
        marMolhrv = addMarker(Stanice.molhrv, "", Stanice.arMolhrv)

        marKsucurac = addMarker(Stanice.ksucurac, "Kaštel Sućurac", Stanice.arKsucurac)
        marStarine = addMarker(Stanice.starine, "Starine", Stanice.arStarine)
        marDracevac = addMarker(Stanice.dracevac, "Dračevac", Stanice.arDracevac)
        marJapirko = addMarker(Stanice.japirko, "Japirko", Stanice.arJapirko)
        marNincevici = addMarker(Stanice.nincevici, "Ninčevići", Stanice.arNincevici)
        marKlis = addMarker(Stanice.klis, "Klis", Stanice.arKlis)
        marSolin = addMarker(Stanice.solin, "Solin", Stanice.arSolin)
        marTtts = addMarker(Stanice.ttts, "Ttts", Stanice.arTtts)
        marStobrec = addMarker(Stanice.stobrec, "Stobreč", Stanice.arStobrec)
        marKamen = addMarker(Stanice.kamen, "Kamen", Stanice.arKamen)
        marZrnovnica = addMarker(Stanice.zrnovnica, "Žrnovnica", Stanice.arZrnovnica)
        marDubrava = addMarker(Stanice.dubrava, "Dubrava", Stanice.arDubrava)
        marNaklice = addMarker(Stanice.naklice, "Naklice", Stanice.arNaklice)
        marMutogras = addMarker(Stanice.mutogras, "Mutogras", Stanice.arMutogras)
        marVranjic = addMarker(Stanice.vranjic, "Vranjic", Stanice.arVranjic)
        marKucine = addMarker(Stanice.kucine, "Kučine", Stanice.arKucine)
        marKosa = addMarker(Stanice.kosa, "Kosa", Stanice.arKosa)
        marKoprivno = addMarker(Stanice.koprivno, "Koprivno", Stanice.arKoprivno)
        marTrogir = addMarker(Stanice.trogir, "Trogir", Stanice.arTrogir)
        marResnik = addMarker(Stanice.resnik, "Resnik", Stanice.arResnik)
        marOmis = addMarker(Stanice.omis, "Omiš", Stanice.arOmis)
        marGornjidolac = addMarker(Stanice.gornjidolac, "Gornji Dolac", Stanice.arGornjidolac)
        marKovacevici = addMarker(Stanice.kovacevici, "Kovačevići", Stanice.arKovacevici)
        marSestanovac = addMarker(Stanice.sestanovac, "Šestanovac", Stanice.arSestanovac)
        marSutina = addMarker(Stanice.sutina, "Sutina", Stanice.arSutina)
        marZelovo = addMarker(Stanice.zelovo, "Zelovo", Stanice.arZelovo)
        marOgorje = addMarker(Stanice.ogorje, "Ogorje", Stanice.arOgorje)
        marMuc = addMarker(Stanice.muc, "Muć", Stanice.arMuc)
        marCrivac = addMarker(Stanice.crivac, "Crivac", Stanice.arCrivac)
        marKljaci = addMarker(Stanice.kljaci, "Kljaci", Stanice.arKljaci)
        marDrnis = addMarker(Stanice.drnis, "Drniš", Stanice.arDrnis)
        marKadglav = addMarker(Stanice.kadglav, "Kadina Glavica", Stanice.arKadglav)
        marAksplit = addMarker(Stanice.aksplit, getString(R.string.gmaps_stanica_ak_split), Stanice.arAksplit)
        marBrstanovo = addMarker(Stanice.brstanovo, "Brštanovo", Stanice.arBrstanovo)
        marNisko = addMarker(Stanice.nisko, "Nisko", Stanice.arNisko)
        marKladnjice = addMarker(Stanice.kladnjice, "Kladnjice", Stanice.arKladnjice)
        marLecevica = addMarker(Stanice.lecevica, "Lećevica", Stanice.arLecevica)
    }


    private fun showMainMarkers() {
        marHnk.isVisible = true
        marOpcinaz.isVisible = true
        marOpcinai.isVisible = true
        marSolinskakol.isVisible = true
        marPazarz.isVisible = true
        marPazari.isVisible = true
        marTrluka.isVisible = true
        marKaufland.isVisible = true
        marSpinut.isVisible = true
        marLorai.isVisible = true
        marLoraz.isVisible = true
        marMedenizvoncac.isVisible = true
        marSvfrane.isVisible = true
        marBene.isVisible = true
        marDuilovo.isVisible = true
        marKila.isVisible = true
        marBrnik.isVisible = true
        marFirule.isVisible = true
        marLovrinac.isVisible = true
        marZnjan.isVisible = true
        marTrstenik.isVisible = true
        marRotorbrda.isVisible = true
    }


    private fun hideMainMarkers() {
        marHnk.isVisible = false
        marOpcinaz.isVisible = false
        marOpcinai.isVisible = false
        marSolinskakol.isVisible = false
        marPazarz.isVisible = false
        marPazari.isVisible = false
        marTrluka.isVisible = false
        marKaufland.isVisible = false
        marSpinut.isVisible = false
        marLorai.isVisible = false
        marLoraz.isVisible = false
        marMedenizvoncac.isVisible = false
        marSvfrane.isVisible = false
        marBene.isVisible = false
        marDuilovo.isVisible = false
        marKila.isVisible = false
        marBrnik.isVisible = false
        marFirule.isVisible = false
        marLovrinac.isVisible = false
        marZnjan.isVisible = false
        marTrstenik.isVisible = false
        marRotorbrda.isVisible = false
    }


    private fun showCityMarkers() {
        marZabaz.isVisible = true
        marZabai.isVisible = true
        marGuruz.isVisible = true
        marGurui.isVisible = true
        marKman1.isVisible = true
        marKman2.isVisible = true
        marKman3.isVisible = true
        marKman4.isVisible = true
        marKocunar1.isVisible = true
        marKauflandj.isVisible = true
        marKauflands.isVisible = true
        marTommys.isVisible = true
        marBilla.isVisible = true
        marKocunar2.isVisible = true
        marKocunar3.isVisible = true
        marDomrata1.isVisible = true
        marSplitmall.isVisible = true
        marZbngs.isVisible = true
        marZbngj.isVisible = true
        marDujmovaca.isVisible = true
        marPrima.isVisible = true
        marSjlukas.isVisible = true
        marSjlukaj.isVisible = true
        marPopravnidom.isVisible = true
        marPrometovagarazas.isVisible = true
        marBrda1.isVisible = true
        marPosta.isVisible = true
        marHerc1s.isVisible = true
        marHerc2j.isVisible = true
        marPeugs.isVisible = true
        marPeugj.isVisible = true
        marMostarska.isVisible = true
        marMostarska1.isVisible = true
        marKauflandi.isVisible = true
        marZagputs.isVisible = true
        marZagputj.isVisible = true
        marZagput9.isVisible = true
        marPujankes.isVisible = true
        marPujankej.isVisible = true
        marPujanke1s.isVisible = true
        marPujanke1j.isVisible = true
        marJokers.isVisible = true
        marJokerj.isVisible = true
        marBrodogradiliste.isVisible = true
        marLidllora.isVisible = true
        marPlavakava.isVisible = true
        marBazeni.isVisible = true
        marZf1.isVisible = true
        marUlhrvmorn.isVisible = true
        marUlhrvmorn1s.isVisible = true
        marUlhrvmorn1j.isVisible = true
        marSplitstadion.isVisible = true
        marUlhrvmorn3.isVisible = true
        marPoljud.isVisible = true
        marKineskizid.isVisible = true
        marMurall.isVisible = true
        marMurall1.isVisible = true
        marInaspinut.isVisible = true
        marInaspinut1.isVisible = true
        marTunel.isVisible = true
        marTunelj.isVisible = true
        marMihanoviceva.isVisible = true
        marMihanoviceva1.isVisible = true
        marSupilova.isVisible = true
        marSupilova1.isVisible = true
        marGunjecin.isVisible = true
        marPutmeja.isVisible = true
        marSetalisteim.isVisible = true
        marSetalisteim1.isVisible = true
        marSetalisteim2.isVisible = true
        marMedenizvoncac1.isVisible = true
        marDrazanac.isVisible = true
        marZapobala.isVisible = true
        marZapobala1.isVisible = true
        marVertigo.isVisible = true
        marVertigo1.isVisible = true
        marStariplac.isVisible = true
        marStariplaci.isVisible = true
        marSportshop.isVisible = true
        marSud.isVisible = true
        marDubrov1.isVisible = true
        marCfvrime.isVisible = true
        marDubrov2.isVisible = true
        marDubrov3.isVisible = true
        marDubrov4.isVisible = true
        marDubrov5.isVisible = true
        marDubrov6.isVisible = true
        marDubrov7.isVisible = true
        marVel1.isVisible = true
        marPlavazgrada.isVisible = true
        marPlavazgrada1.isVisible = true
        marVel2.isVisible = true
        marVel3.isVisible = true
        marVel4.isVisible = true
        marVel5.isVisible = true
        marVelvicta.isVisible = true
        marMcgrgo.isVisible = true
        marVel6i.isVisible = true
        marVel6z.isVisible = true
        marVel7z.isVisible = true
        marVel7i.isVisible = true
        marMedeniznjani.isVisible = true
        marMedeniznjanz.isVisible = true
        marPlaza1.isVisible = true
        marPlaza2.isVisible = true
        marPapeivanapavla1.isVisible = true
        marPapeivanapavla2.isVisible = true
        marDuilovo1.isVisible = true
        marDuilovo2.isVisible = true
        marVrboran.isVisible = true
        marVuk1s.isVisible = true
        marVuk1j.isVisible = true
        marVuk2s.isVisible = true
        marVuk2j.isVisible = true
        marVuk3s.isVisible = true
        marVuk3j.isVisible = true
        marVukvicta.isVisible = true
        marVuksucidar.isVisible = true
        marVukkampus.isVisible = true
        marVukkampusj.isVisible = true
        marVuk4s.isVisible = true
        marVuk4j.isVisible = true
        marVuk5.isVisible = true
        marVuk6.isVisible = true
        marVuk7.isVisible = true
        marVukopcina.isVisible = true
        marMaz1.isVisible = true
        marMaz2.isVisible = true
        marBrnik1.isVisible = true
        marFesb.isVisible = true
        marSplit3.isVisible = true
        marDjackidoms.isVisible = true
        marDjackidomj.isVisible = true
        marMathrv1s.isVisible = true
        marMathrv1j.isVisible = true
        marKotekss.isVisible = true
        marKoteksj.isVisible = true
        marUlslobodei.isVisible = true
        marUlslobodez.isVisible = true
        marBb1.isVisible = true
        marMioc.isVisible = true
        marBb2.isVisible = true
        marBb3.isVisible = true
        marPopajpois.isVisible = true
        marPoiss.isVisible = true
        marAutohrv.isVisible = true
        marPolj1s.isVisible = true
        marPolj1j.isVisible = true
        marPolj2s.isVisible = true
        marPolj2j.isVisible = true
        marTesla.isVisible = true
        marTeslaj.isVisible = true
        marPolj3s.isVisible = true
        marPolj3j.isVisible = true
        marHeps.isVisible = true
        marHepj.isVisible = true
        marUlzbng5.isVisible = true
        marUlzbng6.isVisible = true
        marUlzbng7.isVisible = true
        marUlzbng8.isVisible = true
        marLovrinacj.isVisible = true
        marBracka1.isVisible = true
        marBracka2.isVisible = true
        marOlly.isVisible = true
        marMolhrv.isVisible = true
    }


    private fun showUrbanMarkers() {
        marKsucurac.isVisible = true
        marStarine.isVisible = true
        marDracevac.isVisible = true
        marJapirko.isVisible = true
        marNincevici.isVisible = true
        marKlis.isVisible = true
        marSolin.isVisible = true
        marTtts.isVisible = true
        marStobrec.isVisible = true
        marKamen.isVisible = true
        marZrnovnica.isVisible = true
        marDubrava.isVisible = true
        marNaklice.isVisible = true
        marMutogras.isVisible = true
        marVranjic.isVisible = true
        marKucine.isVisible = true
        marKosa.isVisible = true
        marKoprivno.isVisible = true
        marTrogir.isVisible = true
        marResnik.isVisible = true
        marOmis.isVisible = true
        marGornjidolac.isVisible = true
        marKovacevici.isVisible = true
        marSestanovac.isVisible = true
        marSutina.isVisible = true
        marZelovo.isVisible = true
        marOgorje.isVisible = true
        marMuc.isVisible = true
        marCrivac.isVisible = true
        marKljaci.isVisible = true
        marDrnis.isVisible = true
        marKadglav.isVisible = true
        marAksplit.isVisible = true
        marBrstanovo.isVisible = true
        marNisko.isVisible = true
        marKladnjice.isVisible = true
        marLecevica.isVisible = true
        marLecevica.isVisible = true
    }


    private fun hideCityMarkers() {
        marZabaz.isVisible = false
        marZabai.isVisible = false
        marGuruz.isVisible = false
        marGurui.isVisible = false
        marKman1.isVisible = false
        marKman2.isVisible = false
        marKman3.isVisible = false
        marKman4.isVisible = false
        marKocunar1.isVisible = false
        marKauflandj.isVisible = false
        marKauflands.isVisible = false
        marTommys.isVisible = false
        marBilla.isVisible = false
        marKocunar2.isVisible = false
        marKocunar3.isVisible = false
        marDomrata1.isVisible = false
        marSplitmall.isVisible = false
        marZbngs.isVisible = false
        marZbngj.isVisible = false
        marDujmovaca.isVisible = false
        marPrima.isVisible = false
        marSjlukas.isVisible = false
        marSjlukaj.isVisible = false
        marPopravnidom.isVisible = false
        marPrometovagarazas.isVisible = false
        marBrda1.isVisible = false
        marPosta.isVisible = false
        marHerc1s.isVisible = false
        marHerc2j.isVisible = false
        marPeugs.isVisible = false
        marPeugj.isVisible = false
        marMostarska.isVisible = false
        marMostarska1.isVisible = false
        marKauflandi.isVisible = false
        marZagputs.isVisible = false
        marZagputj.isVisible = false
        marZagput9.isVisible = false
        marPujankes.isVisible = false
        marPujankej.isVisible = false
        marPujanke1s.isVisible = false
        marPujanke1j.isVisible = false
        marJokers.isVisible = false
        marJokerj.isVisible = false
        marBrodogradiliste.isVisible = false
        marLidllora.isVisible = false
        marPlavakava.isVisible = false
        marBazeni.isVisible = false
        marZf1.isVisible = false
        marUlhrvmorn.isVisible = false
        marUlhrvmorn1s.isVisible = false
        marUlhrvmorn1j.isVisible = false
        marSplitstadion.isVisible = false
        marUlhrvmorn3.isVisible = false
        marPoljud.isVisible = false
        marKineskizid.isVisible = false
        marMurall.isVisible = false
        marMurall1.isVisible = false
        marInaspinut.isVisible = false
        marInaspinut1.isVisible = false
        marTunel.isVisible = false
        marTunelj.isVisible = false
        marMihanoviceva.isVisible = false
        marMihanoviceva1.isVisible = false
        marSupilova.isVisible = false
        marSupilova1.isVisible = false
        marGunjecin.isVisible = false
        marPutmeja.isVisible = false
        marSetalisteim.isVisible = false
        marSetalisteim1.isVisible = false
        marSetalisteim2.isVisible = false
        marMedenizvoncac1.isVisible = false
        marDrazanac.isVisible = false
        marZapobala.isVisible = false
        marZapobala1.isVisible = false
        marVertigo.isVisible = false
        marVertigo1.isVisible = false
        marStariplac.isVisible = false
        marStariplaci.isVisible = false
        marSportshop.isVisible = false
        marSud.isVisible = false
        marDubrov1.isVisible = false
        marCfvrime.isVisible = false
        marDubrov2.isVisible = false
        marDubrov3.isVisible = false
        marDubrov4.isVisible = false
        marDubrov5.isVisible = false
        marDubrov6.isVisible = false
        marDubrov7.isVisible = false
        marVel1.isVisible = false
        marPlavazgrada.isVisible = false
        marPlavazgrada1.isVisible = false
        marVel2.isVisible = false
        marVel3.isVisible = false
        marVel4.isVisible = false
        marVel5.isVisible = false
        marVelvicta.isVisible = false
        marMcgrgo.isVisible = false
        marVel6i.isVisible = false
        marVel6z.isVisible = false
        marVel7z.isVisible = false
        marVel7i.isVisible = false
        marMedeniznjani.isVisible = false
        marMedeniznjanz.isVisible = false
        marPlaza1.isVisible = false
        marPlaza2.isVisible = false
        marPapeivanapavla1.isVisible = false
        marPapeivanapavla2.isVisible = false
        marDuilovo1.isVisible = false
        marDuilovo2.isVisible = false
        marVrboran.isVisible = false
        marVuk1s.isVisible = false
        marVuk1j.isVisible = false
        marVuk2s.isVisible = false
        marVuk2j.isVisible = false
        marVuk3s.isVisible = false
        marVuk3j.isVisible = false
        marVukvicta.isVisible = false
        marVuksucidar.isVisible = false
        marVukkampus.isVisible = false
        marVukkampusj.isVisible = false
        marVuk4s.isVisible = false
        marVuk4j.isVisible = false
        marVuk5.isVisible = false
        marVuk6.isVisible = false
        marVuk7.isVisible = false
        marVukopcina.isVisible = false
        marMaz1.isVisible = false
        marMaz2.isVisible = false
        marBrnik1.isVisible = false
        marFesb.isVisible = false
        marSplit3.isVisible = false
        marDjackidoms.isVisible = false
        marDjackidomj.isVisible = false
        marMathrv1s.isVisible = false
        marMathrv1j.isVisible = false
        marKotekss.isVisible = false
        marKoteksj.isVisible = false
        marUlslobodei.isVisible = false
        marUlslobodez.isVisible = false
        marBb1.isVisible = false
        marMioc.isVisible = false
        marBb2.isVisible = false
        marBb3.isVisible = false
        marPopajpois.isVisible = false
        marPoiss.isVisible = false
        marAutohrv.isVisible = false
        marPolj1s.isVisible = false
        marPolj1j.isVisible = false
        marPolj2s.isVisible = false
        marPolj2j.isVisible = false
        marTesla.isVisible = false
        marTeslaj.isVisible = false
        marPolj3s.isVisible = false
        marPolj3j.isVisible = false
        marHeps.isVisible = false
        marHepj.isVisible = false
        marUlzbng5.isVisible = false
        marUlzbng6.isVisible = false
        marUlzbng7.isVisible = false
        marUlzbng8.isVisible = false
        marLovrinacj.isVisible = false
        marBracka1.isVisible = false
        marBracka2.isVisible = false
        marOlly.isVisible = false
        marMolhrv.isVisible = false
    }


    private fun hideUrbanMarkers() {
        marKsucurac.isVisible = false
        marStarine.isVisible = false
        marDracevac.isVisible = false
        marJapirko.isVisible = false
        marNincevici.isVisible = false
        marKlis.isVisible = false
        marSolin.isVisible = false
        marTtts.isVisible = false
        marStobrec.isVisible = false
        marKamen.isVisible = false
        marZrnovnica.isVisible = false
        marDubrava.isVisible = false
        marNaklice.isVisible = false
        marMutogras.isVisible = false
        marVranjic.isVisible = false
        marKucine.isVisible = false
        marKosa.isVisible = false
        marKoprivno.isVisible = false
        marTrogir.isVisible = false
        marResnik.isVisible = false
        marOmis.isVisible = false
        marGornjidolac.isVisible = false
        marKovacevici.isVisible = false
        marSestanovac.isVisible = false
        marSutina.isVisible = false
        marZelovo.isVisible = false
        marOgorje.isVisible = false
        marMuc.isVisible = false
        marCrivac.isVisible = false
        marKljaci.isVisible = false
        marDrnis.isVisible = false
        marKadglav.isVisible = false
        marAksplit.isVisible = false
        marBrstanovo.isVisible = false
        marNisko.isVisible = false
        marKladnjice.isVisible = false
        marLecevica.isVisible = false
    }

    private fun showToast() {
        Toast.makeText(this, getString(R.string.nijejos), Toast.LENGTH_SHORT).show()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (gmapsId == null) {
            gmapsId = "999"
            menu.findItem(R.id.action_main_markers).isChecked = true
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_gmaps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_main_markers -> {
                if (item.isChecked) {
                    hideMainMarkers()
                    item.isChecked = false
                } else {
                    showMainMarkers()
                    item.isChecked = true
                }
                return true
            }
            R.id.action_city_markers -> {
                if (item.isChecked) {
                    hideCityMarkers()
                    item.isChecked = false
                } else {
                    showCityMarkers()
                    item.isChecked = true
                }
                return true
            }
            R.id.action_urban_markers -> {
                if (item.isChecked) {
                    hideUrbanMarkers()
                    item.isChecked = false
                } else {
                    showUrbanMarkers()
                    item.isChecked = true
                }
                return true
            }
            R.id.action_report -> {
                Utils().reportIssue(this, selectedItems.toString())
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
