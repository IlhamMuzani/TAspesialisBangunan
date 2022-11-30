package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.produkjasadetail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_produkjasadetail.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.text.NumberFormat
import java.util.*

class ProdukjasadetailActivity : AppCompatActivity(), ProdukjasadetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ProdukjasadetailPresenter
    lateinit var produkdetail: DataProduk
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkjasadetail)

        presenter = ProdukjasadetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukjasadetail(Constant.PRODUK_ID)
    }

    override fun initActivity() {
       tv_bgjasa.text="Detail Produk"
    }

    override fun initListener() {
        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onLoadingProdukJasadetail(loading: Boolean) {
        when (loading) {
            true -> progressdetailjas.visibility = View.VISIBLE
            false -> progressdetailjas.visibility = View.GONE
        }
    }

    override fun onResultProdukjasadetail(responseProdukDetail: ResponseProdukDetail) {
        produkdetail = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + produkdetail.gambar!!, imvgambardetailjas)
        namadetailjas.text = produkdetail.nama_toko
        jenispembuatandetailjas.text = produkdetail.jenis_pembuatan
        alamatdetailjas.text = produkdetail.alamat
        phonedetailjas.text = produkdetail.phone
        hargadetailjas.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(produkdetail.harga))
        deskripsidetailjas.text = produkdetail.deskripsi

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetailjas) as SupportMapFragment
        mapFragment.getMapAsync( this )

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produkdetail.latitude!!.toDouble(), produkdetail.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produkdetail.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}