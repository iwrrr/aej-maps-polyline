package com.iwrrr.mapspolyline

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import com.iwrrr.mapspolyline.databinding.ActivityMainBinding
import com.iwrrr.mapspolyline.manager.LocationManager
import com.iwrrr.mapspolyline.utils.BaseActivityBinding
import com.iwrrr.mapspolyline.utils.intentTo
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {

    private val locationManager: LocationManager by lazy {
        LocationManager(this)
    }

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        getPermission()

        with(binding) {
            btnMaps.setOnClickListener {
                intentTo(MapsActivity::class.java)
            }

            btnUser.setOnClickListener {
                intentTo(UserActivity::class.java)
            }

            btnPickerMaps.setOnClickListener {
                intentTo(MapsPickerActivity::class.java)
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
    private fun getPermission() {
        val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
        if (EasyPermissions.hasPermissions(this, fineLocation, coarseLocation)) {
            Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
//            onResult.invoke()
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

    companion object {
        private const val RC_LOCATION = 15
    }
}