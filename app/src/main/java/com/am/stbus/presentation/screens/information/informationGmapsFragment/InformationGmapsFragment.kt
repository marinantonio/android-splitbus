/*
 * MIT License
 *
 * Copyright (c) 2013 - 2021 Antonio Marin
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

package com.am.stbus.presentation.screens.information.informationGmapsFragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.am.stbus.R
import com.am.stbus.common.extensions.toPx
import com.am.stbus.presentation.screens.gmaps.data.*
import com.am.stbus.presentation.screens.gmaps.data.CityRoutesData.GLAVNE_STANICE
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_information_image_view.*


class InformationGmapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private val args: InformationGmapsFragmentArgs by navArgs()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var map: GoogleMap

    private var leftPadding: Int = 0
    private var topPadding: Int = 0
    private var rightPadding: Int = 0
    private var bottomPadding: Int = 0

    private var selectedItems = mutableSetOf<Int>()

    private var initialPopupShown = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        requireActivity().window.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = ContextCompat.getColor(requireContext(), R.color.poluprozirniStatusBar)
            }
        }

        return inflater.inflate(R.layout.fragment_information_gmaps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            inflateMenu(R.menu.menu_gmaps)
            setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            leftPadding = insets.systemWindowInsetLeft
            topPadding = insets.systemWindowInsetTop + 56.toPx()
            rightPadding = insets.systemWindowInsetRight
            bottomPadding = insets.systemWindowInsetBottom
            insets
        }

        val mapFragment: SupportMapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.apply {
            uiSettings.isZoomControlsEnabled = true
            animateCamera(CameraUpdateFactory.newLatLngZoom(Stanice.VUKKAMPUSS, 12f))
            uiSettings.isMapToolbarEnabled = true
        }

        val customInfoWindow = GmapsBusStopInfoView(requireContext())
        map.setInfoWindowAdapter(customInfoWindow)
        map.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        map.setOnInfoWindowClickListener(this)


        val myPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        when (myPreference.getString("maptype", "2")) {
            "1" -> map.mapType = GoogleMap.MAP_TYPE_HYBRID
            "2" -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
            "3" -> map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }

        if (myPreference.getBoolean("traffic", false)) {
            map.isTrafficEnabled = true
        }

        map.setOnCameraMoveListener {
            val cameraPosition = map.cameraPosition
            if (cameraPosition.zoom == 12f && !initialPopupShown) {
                showRouteDirectionPopup(args.gmapsId)
                initialPopupShown = true
            }
        }
//        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
//            @Override
//            public void onCameraMove() {
//                CameraPosition cameraPosition = googleMap.getCameraPosition();
//                if(cameraPosition.zoom > 18.0) {
//                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                } else {
//                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                }
//            }
//        });


        setupUserLocation()
    }

    override fun onInfoWindowClick(marker: Marker?) {
        createMarkerAlert(marker)
    }

    private fun setupUserLocation() {
        if (checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(
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
                    setupUserLocation()
                } else {
                    // TODO: Error message
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


    private fun showRouteDirectionPopup(gmapsId: Int) {
        if (gmapsId == 0) {
            showGeneralStops()
            return
        }

        // Prvo vidimo jeli ovaj gmapsId treba otvoriti
        // menu izbornik sa opcijama smjera
        val routeDirection = getListContainingRouteDirections(gmapsId)

        if (routeDirection.isEmpty()) {
            selectedItems.add(gmapsId)
            displayLine(gmapsId)
        } else {
            android.app.AlertDialog.Builder(requireContext()).apply {
                setTitle(R.string.information_gmaps_route_selector_popup_title)
                setItems(routeDirection.map {
                    getString(returnStringForGmapsId(it))
                }.toTypedArray()) { dialog, which ->
                    when (which) {
                        0 -> {
                            selectedItems.add(routeDirection[0])
                            displayLine(routeDirection[0])
                        }
                        1 -> {
                            selectedItems.add(routeDirection[1])
                            displayLine(routeDirection[1])
                        }
                    }
                }
            }.create().show()
        }
    }

    private fun createMarkerAlert(marker: Marker?) {
        val markerTag = marker!!.tag as MarkerData

        val gmapsList = markerTag.gmapsIds
        val booleans = BooleanArray(gmapsList.size)

        val routesList = ArrayList<String>()
        gmapsList.forEachIndexed { index, item ->
            routesList.add(requireContext().getString(returnStringForGmapsId(item)))
            booleans[index] = selectedItems.contains(item)
        }

        val routesListStringArray = routesList.toArray(arrayOfNulls<CharSequence>(routesList.size))

        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.gmaps_select_title)
            setMultiChoiceItems(routesListStringArray, booleans) { _, indexSelected, isChecked ->
                if (isChecked) {
                    selectedItems.add(gmapsList[indexSelected])
                    showRouteDirectionPopup(gmapsList[indexSelected])
                } else {
                    selectedItems.remove(gmapsList[indexSelected])
                    clearAndRedrawMap()
                }
            }
            setNegativeButton(R.string.information_gmaps_route_selector_popup_close) { _, _ ->

            }
            create().show()
        }
    }

    private fun displayLine(gmapsId: Int) {

        val listOfStops = getStopForRoute(gmapsId)

        if (listOfStops.isEmpty()) {
            Toast.makeText(requireContext(), R.string.nijejos, Toast.LENGTH_LONG).show()
            return
        }

        listOfStops.forEach {
            map.addMarker(MarkerOptions().position(it.latLng!!)).apply {
                tag = MarkerData(null, it.title, it.lineNumbers, it.gmapsIds)
                title = it.title
                setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
            }
        }

        map.addPolyline(PolylineOptions().addAll(listOfStops.map { it.latLng })
                .width(8f)
                .color(ContextCompat.getColor(requireContext(), R.color.zutaGmapsRute))
                .geodesic(true))
    }


    private fun clearAndRedrawMap() {
        map.clear()
        selectedItems.forEach {
            displayLine(it)
        }
    }

    private fun showGeneralStops() {
        GLAVNE_STANICE.forEach {
            map.addMarker(MarkerOptions().position(it.latLng!!)).apply {
                tag = MarkerData(null, it.title, it.lineNumbers, it.gmapsIds)
                title = it.title
                setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_main_markers -> {
                showGeneralStops()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class MarkerData(val latLng: LatLng?, val title: String, val lineNumbers: List<String>, val gmapsIds: List<Int>)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}