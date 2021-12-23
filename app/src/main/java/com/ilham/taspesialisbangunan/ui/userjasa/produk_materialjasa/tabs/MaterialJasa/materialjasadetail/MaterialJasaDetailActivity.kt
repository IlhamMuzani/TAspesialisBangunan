
package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa.materialjasadetail

import android.app.Activity
import android.content.Intent
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
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_material_detail1.*
import kotlinx.android.synthetic.main.activity_material_jasa_detail1.*
import kotlinx.android.synthetic.main.activity_pengajuan.view.*

class MaterialJasaDetailActivity : AppCompatActivity(), MaterialJasaDetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: MaterialJasaDetailPresenter
    lateinit var materialdetail: DataProduk
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_jasa_detail1)

        presenter = MaterialJasaDetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterialdetail(Constant.PRODUK_ID)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Detail Material"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
    }

    override fun onLoadingMaterialdetailjas(loading: Boolean) {
        when (loading) {
            true -> progressdetailjass.visibility = View.VISIBLE
            false -> progressdetailjass.visibility = View.GONE
        }
    }

    override fun onResultMaterialdetailjas(responseProdukDetail: ResponseProdukDetail) {
        materialdetail = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + materialdetail.gambar!!, imvgambardetailjass)
        namadetailjass.text = materialdetail.nama_toko
        jenispembuatandetailjass.text = materialdetail.jenis_pembuatan
        alamatdetailjass.text = materialdetail.alamat
        phonedetailjass.text = materialdetail.phone
        hargadetailjass.text = materialdetail.harga
        deskripsidetailjass.text = materialdetail.deskripsi

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetailjass) as SupportMapFragment
        mapFragment.getMapAsync( this )

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (materialdetail.latitude!!.toDouble(), materialdetail.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( materialdetail.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            val view = layoutInflater.inflate(R.layout.activity_pengajuan, null)
            view.imvPengajuan.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}