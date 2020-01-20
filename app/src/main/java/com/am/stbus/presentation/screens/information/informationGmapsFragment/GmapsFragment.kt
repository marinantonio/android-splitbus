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

package com.am.stbus.presentation.screens.information.informationGmapsFragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.am.stbus.R
import com.am.stbus.presentation.screens.gmaps.data.Stanice
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import timber.log.Timber

class GmapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return inflater.inflate(R.layout.fragment_information_gmaps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val myPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        when (myPreference.getString("maptype", "2")) {
            "1" -> map.mapType = GoogleMap.MAP_TYPE_HYBRID
            "2" -> map.mapType = GoogleMap.MAP_TYPE_NORMAL
            "3" -> map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }

        if (myPreference.getBoolean("traffic", false)) {
            map.isTrafficEnabled = true
        }

        setupUserLocation()
    }

    override fun onInfoWindowClick(p0: Marker?) {
    }

    private fun setupUserLocation() {
        Timber.i("Setup userLocation!")
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }



}