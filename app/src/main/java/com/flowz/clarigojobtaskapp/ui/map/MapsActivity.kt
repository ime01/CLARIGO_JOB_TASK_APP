package com.flowz.clarigojobtaskapp.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flowz.clarigojobtaskapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        myCurentLocation which is Port Harcourt, Rivers State, Nigeria
        val sourceLocation = LatLng(4.815554, 7.049844)

//        task Location which is TI Mall Indore
        val destinationLocation = LatLng(53.265390, -6.168110)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sourceLocation).title("Port Harcourt, Rivers State, Nigeria"))
        mMap.addMarker(MarkerOptions().position(destinationLocation).title("Task destination Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sourceLocation))
        mMap.addPolyline(PolylineOptions()
            .clickable(true)
            .add(sourceLocation)
            .add(destinationLocation)
            .width(8f)
            .color(resources.getColor(R.color.black)))

        mMap.addCircle(CircleOptions()
            .center(destinationLocation)
            .radius(50000.0)
            .strokeWidth(3f)
            .strokeColor(getResources().getColor(R.color.design_default_color_error))
            .fillColor(getResources().getColor(R.color.design_default_color_error)))

        mMap.isMyLocationEnabled
    }
}