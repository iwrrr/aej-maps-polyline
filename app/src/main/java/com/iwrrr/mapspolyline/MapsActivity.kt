package com.iwrrr.mapspolyline

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.iwrrr.mapspolyline.databinding.ActivityMapsBinding
import com.iwrrr.mapspolyline.manager.LocationManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val locationManager: LocationManager by lazy {
        LocationManager(this)
    }

    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isCompassEnabled = true
            isZoomControlsEnabled = true
        }

        val routes = Sources.getResultRoutes()
        val coordinates = routes.data?.route.orEmpty()
            .map {
                LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
            }

        val point = coordinates.firstOrNull()
        point?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 14f))
        }

        val polyline = PolylineOptions()
            .addAll(coordinates)
            .width(6f)
            .color(Color.RED)
        mMap.addPolyline(polyline)

        getLocationWithPermission()

        binding.tvResultCoordinate.setOnClickListener {
//            Intent(this, UserActivity::class.java).also {
//                startActivity(it)
//            }
            locationManager.getLastLocation {
                Toast.makeText(this, it.toLatLng().toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(value = RC_LOCATION)
    private fun getLocationWithPermission() {
        val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
        if (EasyPermissions.hasPermissions(this, fineLocation, coarseLocation)) {
            // get location
            getLocation()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Granted for location",
                RC_LOCATION,
                fineLocation,
                coarseLocation
            )
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        MainScope().launch {
            locationManager.getLocationFlow().collect(onLocationResult())
        }
    }

    private fun onLocationResult() = FlowCollector<Location> { location ->
        binding.tvResultCoordinate.text = "${location.latitude}, ${location.longitude}"
        println("----- LOCATION UPDATE -> ${location.latitude} ${location.longitude} -----")

        val newLatLng = LatLng(location.latitude, location.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 20f))

        if (marker == null) {
            val markerOptions = MarkerOptions()
                .position(newLatLng)
            marker = mMap.addMarker(markerOptions)
        }

        marker?.moveSmoothly(newLatLng)
    }

    companion object {
        private const val RC_LOCATION = 15
    }
}

inline fun <T> List<T>.sumOfDistance(selector: (T?, T?) -> Float): Float {
    var sum = 0f

    this.forEachIndexed { index, t ->
        when {
            index > 0 -> {
                val prev = this[index - 1]
                sum += selector(t, prev)
            }
            index == size - 1 -> {
                val next = this[size - 1]
                sum += selector(next, t)
            }
            else -> {
                selector(null, null)
            }
        }
    }

    return sum
}