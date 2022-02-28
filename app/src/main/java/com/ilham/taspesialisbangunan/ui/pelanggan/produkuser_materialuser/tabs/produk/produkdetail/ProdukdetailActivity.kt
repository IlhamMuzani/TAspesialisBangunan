package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.ui.pelanggan.pengajuan.PengajuanActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_pengajuan.view.*
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.activity_saran.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class ProdukdetailActivity : AppCompatActivity(), ProdukdetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ProdukdetailPresenter
    lateinit var produkdetail: DataProduk
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkdetail)

        presenter = ProdukdetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukdetail(Constant.PRODUK_ID)
    }

    override fun initActivity() {
        tv_nama.text ="Detail Product"

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnpengajuann.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                Constant.PRODUK_ID = produkdetail.id!!
                startActivity(Intent(this, PengajuanActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

    }

    override fun onLoadingProdukdetail(loading: Boolean) {
        when (loading) {
            true -> progressdetail.visibility = View.VISIBLE
            false -> progressdetail.visibility = View.GONE
        }
    }

    override fun onResultProdukdetail(responseProdukDetail: ResponseProdukDetail) {
        produkdetail = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + produkdetail.gambar!!, imvgambardetail)
        namadetail.text = produkdetail.nama_toko
        jenispembuatandetail.text = produkdetail.jenis_pembuatan
        alamatdetail.text = produkdetail.alamat
        phonedetail.text = produkdetail.phone
        hargadetail.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(produkdetail.harga))
        deskripsidetail.text = produkdetail.deskripsi

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetail) as SupportMapFragment
        mapFragment.getMapAsync( this )

    }

    override fun onResultSaran(responseSaranInsert: ResponseSaranInsert) {
        showMessage("Saran terkirim")
    }

    override fun showDialogSaran(dataProduk: DataProduk) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.activity_saran, null)

        view.btn_sarann.setOnClickListener {
            val saran = view.edtdeskripsisaran.text
            if (saran.isNullOrEmpty()) {
                showMessage("Deskripsi harus diisi")
            } else {
                presenter.insertSaran(
                    dataProduk.kd_user!!,
                    prefsManager.prefsId,
                    saran.toString()
                )
            }
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produkdetail.latitude!!.toDouble(), produkdetail.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produkdetail.nama_toko ))
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