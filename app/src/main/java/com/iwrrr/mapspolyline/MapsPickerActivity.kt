package com.iwrrr.mapspolyline

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.iwrrr.mapspolyline.data.entity.LocationData
import com.iwrrr.mapspolyline.data.mapper.ResponseMapper
import com.iwrrr.mapspolyline.data.network.WebServices
import com.iwrrr.mapspolyline.databinding.ActivityMapsPickerBinding
import com.iwrrr.mapspolyline.manager.LocationManager
import com.iwrrr.mapspolyline.utils.BaseActivityBinding
import com.iwrrr.mapspolyline.utils.toast
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class MapsPickerActivity : BaseActivityBinding<ActivityMapsPickerBinding>() {

    private var isPanelShow = false

    private val locationManager: LocationManager by lazy { LocationManager(this) }

    private val webServices: WebServices by lazy { WebServices.create() }

    override fun inflateBinding(): ActivityMapsPickerBinding {
        return ActivityMapsPickerBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(onMapReady())

        hidePanel()
    }

    @FlowPreview
    private fun onMapReady(): OnMapReadyCallback = OnMapReadyCallback { map ->
        locationManager.getLastLocation { location ->
            val latLng = location.toLatLng()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f))
        }

        map.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
        }

        map.setOnCameraMoveListener {
            println("move...")
            hidePanel()
        }

        map.setOnCameraIdleListener {
            println("idle... -> ${map.cameraPosition.target}")
            runBlocking {
                val coordinate = map.cameraPosition.target
                println("loading...")
                map.uiSettings.isScrollGesturesEnabled = false
                reverseLocationFlow(coordinate)
                    .debounce(1000L)
                    .collect {
                        println("result -> ${it.name}")
                        onLocationResult(it)
                        map.uiSettings.isScrollGesturesEnabled = true
                    }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onLocationResult(locationData: LocationData) {
        showPanel()
        with(binding) {
            tvLocationResult.text = "${locationData.name}\n${locationData.address.country}"
        }
    }

    private fun hidePanel() {
        isPanelShow = false
        with(binding) {
            panelLocation.animate()
                .translationY(400f)
                .start()
        }
    }

    private fun showPanel() {
        isPanelShow = true
        with(binding) {
            panelLocation.animate()
                .translationY(0f)
                .start()
        }
    }

    private fun togglePanel() {
        if (isPanelShow) {
            hidePanel()
        } else {
            showPanel()
        }
    }

    private fun reverseLocationFlow(latLng: LatLng): Flow<LocationData> {
        return flow {
            val coordinateString = "${latLng.latitude},${latLng.longitude}"
            val response = webServices.reverseCoordinate(coordinateString)
            if (response.isSuccessful) {
                val data = ResponseMapper.mapReverseLocationResponseToLocation(response.body())
                emit(data)
            } else {
                toast("error: ${response.message()}")
            }
        }
    }
}