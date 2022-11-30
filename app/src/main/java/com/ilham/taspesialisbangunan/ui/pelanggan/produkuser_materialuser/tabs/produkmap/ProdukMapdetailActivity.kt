package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.saran.DataSaran
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.ui.pelanggan.pengajuan.PengajuanActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserAdapter
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_pengajuan.view.*
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*
import android.content.pm.PackageManager
import com.ilham.taspesialisbangunan.MainActivity
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.ui.pelanggan.login.LoginuserActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk.ProdukCreateActivity
import com.ilham.taspesialisbangunan.ui.userjasa.tambahrek.TambahRekActivity
import kotlinx.android.synthetic.main.activity_detailalamat.*
import java.text.DecimalFormat


class ProdukdetailActivity : AppCompatActivity(), ProdukdetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ProdukdetailPresenter
    lateinit var produkdetail: DataProduk
    lateinit var prefsManager: PrefsManager
    lateinit var saranAdapter: SaranAdapter
    lateinit var produkuserAdapter: ProdukuserAdapter
    lateinit var saran: DataSaran


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkdetail)

        presenter = ProdukdetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukdetail(Constant.PRODUK_ID)
        presenter.getsaranproduk(Constant.PRODUK_ID)
        presenter.getRating(Constant.PRODUK_ID)

    }

    override fun initActivity() {
        tv_nama.text ="Detail Produk"

    }

    override fun initListener() {

        saranAdapter = SaranAdapter(this, arrayListOf()){
                dataSaran: DataSaran, position: Int, type: String ->

            saran = dataSaran
        }

        Rcv_Komentar.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saranAdapter
        }

        produkuserAdapter = ProdukuserAdapter(this, arrayListOf()){
                dataProduk: DataProduk, position: Int, type: String ->

            produkdetail = dataProduk
        }

        Rcv_produklainya.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produkuserAdapter
        }

        komentar.setOnClickListener {
            presenter.getsaranproduk(produkdetail.id!!)
            Rcv_Komentar.visibility = View.VISIBLE
            Rcv_produklainya.visibility = View.GONE
        }

        btn_produklainya.setOnClickListener {
            progressdetail.visibility = View.VISIBLE
            Rcv_produklainya.visibility = View.VISIBLE
            presenter.getProduklainya()
            Rcv_Komentar.visibility = View.GONE
        }

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnpengajuann.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(this, PengajuanActivity::class.java))
            } else {
                startActivity(Intent(this, LoginuserActivity::class.java))
            }
        }

//        imvcoppyphone.setOnClickListener {
//            val copyManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val copyText = ClipData.newPlainText("text", produkdetail.phone)
//            copyManager.setPrimaryClip(copyText)
//
//            Toast.makeText(this, "Nomor telpone berhasil di salin", Toast.LENGTH_SHORT).show()
//        }

        imvwhatshapp.setOnClickListener {
                if (isWhatsappInstalled()) {

                    val i = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://api.whatsapp.com/send?phone=" +produkdetail.phone )
                    );
                    startActivity(i);


                } else {
                    Toast.makeText(
                        this,
                        "Whatshapp is not installed",
                        Toast.LENGTH_SHORT
                    ).show();
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
        detailalamatproduk.text = produkdetail.alamat
        kecamatandetail.text = produkdetail.kecamatan
        kelurahandetail.text = produkdetail.kelurahan
        phonedetail.text = produkdetail.phone
        hargadetail.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(produkdetail.harga))
        deskripsidetail.text = produkdetail.deskripsi
        categorydetail.text = produkdetail.category
        modeldetail.text = produkdetail.model

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetail) as SupportMapFragment
        mapFragment.getMapAsync( this )

    }

    override fun onResultSaranProduk(responseSaranList: ResponseSaranList) {
        val dataSaran: List<DataSaran> = responseSaranList.dataSaran
        saranAdapter.setData(dataSaran)
    }

    override fun onResultProduklainya(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
        produkuserAdapter.setData(dataProduk)
    }

    override fun onResultgetRating(responseRating: ResponseRating) {
        val status = responseRating.status

        if (status) {
            val df = DecimalFormat("#.#")
            val rating = df.format(responseRating.dataRating!!.toDouble())
            layoutrating.visibility = View.VISIBLE
            txvbintang.text = rating
        } else {
            layoutrating.visibility = View.GONE
        }
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
            view.imvPengajuanmulai.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun isWhatsappInstalled(): Boolean {
        val packageMaganer = packageManager
        var whatshappInstalled: Boolean
        try {
            packageMaganer.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            whatshappInstalled = true
        } catch (e: PackageManager.NameNotFoundException) {
            whatshappInstalled = false
        }
        return whatshappInstalled
    }

}