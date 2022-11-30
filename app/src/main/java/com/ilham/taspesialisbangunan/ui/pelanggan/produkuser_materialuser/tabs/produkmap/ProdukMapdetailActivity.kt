package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_produkmapdetail.*


class ProdukMapdetailActivity : AppCompatActivity(), ProdukMapdetailContract.View, OnMapReadyCallback {

    private lateinit var presenter: ProdukMapdetailPresenter
    private lateinit var produk: List<DataProduk>
    private lateinit var adaptermap: ProdukmapAdapter

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var arrayLatLng: ArrayList<LatLng>? = null
    lateinit var strCurrentLocation: String
    lateinit var simpleLocation: SimpleLocation
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkmapdetail)
        presenter = ProdukMapdetailPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getMapproduk()
    }

    override fun initActivity() {
        tv_nama.text ="Daftar Produk"

        adaptermap = ProdukmapAdapter(this, ArrayList())
        rcvProdukmap.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvProdukmap.adapter = adaptermap
        rcvProdukmap.setHasFixedSize(true)
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onLoadingProduk(loading: Boolean) {
        when (loading) {
            true -> progressmapdetail.visibility = View.VISIBLE
            false -> progressmapdetail.visibility = View.GONE
        }
    }

    override fun onResultProduk(responseProdukList: ResponseProdukList) {
        produk = responseProdukList.dataProduk!!

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomControlsEnabled = true
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        googleMap.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Lokasi Saya"))
            }
        }

        for (i in produk.indices) {
            val latLngMarker =
                LatLng(produk[i].latitude!!.toDouble(), produk[i].longitude!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(latLngMarker).title(produk[i].user.nama_toko))

            googleMap.uiSettings.setAllGesturesEnabled(true)
            googleMap.uiSettings.isZoomGesturesEnabled = true
        }

        googleMap.setOnMarkerClickListener {
            val markerPosition = it.position
            googleMap.addMarker(MarkerOptions().position(markerPosition))

            if (markerPosition.latitude == strCurrentLatitude && markerPosition.longitude == strCurrentLongitude) {
                layoutprodukmap.visibility = View.GONE
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            strCurrentLatitude,
                            strCurrentLongitude
                        ), 12f
                    )
                )
            } else {
                layoutprodukmap.visibility = View.VISIBLE
                adaptermap.setData(produk)
                var markerSelected = -1
                for (i in produk.indices) {
                    if (markerPosition.latitude == produk[i].latitude!!.toDouble() && markerPosition.longitude == produk[i].longitude!!.toDouble()) {
                        markerSelected = i
                    }
                }
                val cameraPosition =
                    CameraPosition.Builder().target(markerPosition).zoom(12f).build()
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                adaptermap.notifyDataSetChanged()
                if (markerSelected != -1) {
                    rcvProdukmap.smoothScrollToPosition(markerSelected)
                } else {
                    layoutprodukmap.visibility = View.GONE
                }
                rcvProdukmap.isNestedScrollingEnabled = false
                it.showInfoWindow()
            }
            false
        }
    }
}