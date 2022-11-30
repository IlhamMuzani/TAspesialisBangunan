package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.produkjasadetail

import android.annotation.SuppressLint
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
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.activity_produkjasadetail.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class ProdukjasadetailActivity : AppCompatActivity(), ProdukjasadetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ProdukjasadetailPresenter
    lateinit var produkdetail: DataProduk
    lateinit var prefsManager: PrefsManager

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkjasadetail)

        presenter = ProdukjasadetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukjasadetail(Constant.PRODUK_ID)
        presenter.getRatingjasa(Constant.PRODUK_ID)

    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
       tv_bgjasa.text="Detail Produk"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {
        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onLoadingProdukJasadetail(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }


    override fun onResultProdukjasadetail(responseProdukDetail: ResponseProdukDetail) {
        produkdetail = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + produkdetail.gambar!!, imvgambardetailjas)
        categorydetailjasa.text = produkdetail.category
        jenispembuatandetailjasa.text = produkdetail.jenis_pembuatan
        detailmodeljasa.text = produkdetail.model
//        detailbahanjasa.text = produkdetail.bahan
        detailukuranjasa.text = produkdetail.ukuran
//        detailberatjasa.text = produkdetail.berat
        kecamatandetailjasa.text = produkdetail.kecamatan
        kelurahandetailjasa.text = produkdetail.kelurahan
        detailalamatprodukjasa.text = produkdetail.alamat
        phonedetailjasa.text = produkdetail.phone
        hargadetailjasa.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(produkdetail.harga))
        deskripsidetailjasa.text = produkdetail.deskripsi
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetailjas) as SupportMapFragment
        mapFragment.getMapAsync( this )

        if (produkdetail.category == "Produk Jasa dan Material"){
            layoutdetailukuranjasa.visibility = View.VISIBLE
        }else{
            layoutdetailukuranjasa.visibility = View.GONE
        }

    }

    override fun onResultgetRatingjasa(responseRating: ResponseRating) {
        val status = responseRating.status

        if (status) {
            val df = DecimalFormat("#.#")
            val rating = df.format(responseRating.dataRating!!.toDouble())
            layoutratingjasa.visibility = View.VISIBLE
            txvbintangjasa.text = rating
        } else {
            layoutratingjasa.visibility = View.GONE
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produkdetail.latitude!!.toDouble(), produkdetail.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produkdetail.user.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}