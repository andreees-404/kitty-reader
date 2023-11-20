package com.cutedomain.kittyreader.views

import android.Manifest
import android.graphics.Rect
import android.location.GpsStatus
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cutedomain.kittyreader.R
import com.cutedomain.kittyreader.databinding.ActivityMapsBinding
import org.osmdroid.api.IMapController
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapsActivity : AppCompatActivity(), MapListener, GpsStatus.Listener {
    private val TAG: String? = "MAP_ACTIVITY"
    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var myLocationOverlay: MyLocationNewOverlay

    private val locationPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.INTERNET
        )
    } else {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        org.osmdroid.config.Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )

        mMap = binding.mapView
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller

        //myLocationOverlay.enableMyLocation()
        //myLocationOverlay.enableFollowLocation()
        //myLocationOverlay.isDrawAccuracyEnabled = true
        //myLocationOverlay.runOnFirstFix{
        //runOnUiThread{
        //        controller.setCenter(myLocationOverlay.myLocation)
        //        controller.animateTo(myLocationOverlay.myLocation)
        //    }
        //}

        val latitude = -33.44921597411058
        val longitude = -70.66244814555698
        val location = GeoPoint(latitude, longitude)

        val marker = Marker(mMap)
        marker.position = location
        marker.title = "Marca personal"
        mMap.overlays.add(marker)
        mMap.invalidate()
        controller.setZoom(6.0)
        Log.d(TAG, "onCreate: ${controller.zoomIn()}")
        Log.d(TAG, "onCreate: ${controller.zoomOut()}")

        controller.animateTo(location)
        //mMap.overlays.add(myLocationOverlay)

        mMap.addMapListener(this)
    }



    override fun onScroll(event: ScrollEvent?): Boolean {
        Log.d(TAG, "onScroll: ${event?.source?.getMapCenter()?.latitude}")
        Log.d(TAG, "onScroll: ${event?.source?.getMapCenter()?.longitude}")
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        Log.d(TAG, "onZoom: ${event?.zoomLevel} source: ${event?.source}")
        return false
    }

    override fun onGpsStatusChanged(p0: Int) {
        TODO("Not yet implemented")
    }
}