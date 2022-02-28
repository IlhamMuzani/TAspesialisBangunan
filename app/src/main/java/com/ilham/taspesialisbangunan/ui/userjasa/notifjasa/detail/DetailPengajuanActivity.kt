package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

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
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_detail_pengajuan.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class DetailPengajuanActivity : AppCompatActivity(), DetailPengajuanContract.View,
    OnMapReadyCallback {

    lateinit var presenter: DetailPengajuanPresenter
    lateinit var pengajuan: DataPengajuan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengajuan)

        presenter = DetailPengajuanPresenter(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetail(Constant.PENGAJUAN_ID)
    }

    override fun initActivity() {
        tv_bgjasa.text ="Details Notifications"

    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btnKirim.setOnClickListener {
            val harga = edtHarga.text
            if (harga.isEmpty()) {
                Toast.makeText(applicationContext, "Harga harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                presenter.hargaPengajuan(pengajuan.id!!, harga.toString())
            }

            layout_harga.visibility =View.GONE
        }
        btnProses.setOnClickListener {
            presenter.pengajuandiproses(pengajuan.id!!)
        }

        btnSelesai.setOnClickListener {
            presenter.pengajuanselesai(pengajuan.id!!)
        }
    }

    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan

        GlideHelper.setImage(this,  Constant.IP_IMAGE + pengajuan.gambar,imvPengajuan)
        GlideHelper.setImage(this,  Constant.IP_IMAGE + pengajuan.bukti,imvBukti)
        txtDeskripsi.text = pengajuan.deskripsi
        txtStatus.text = pengajuan.status

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetailpengajuan) as SupportMapFragment
        mapFragment.getMapAsync( this )

        when (pengajuan.status) {
            "Menunggu" -> {
                layout_harga.visibility = View.VISIBLE
                imvBukti.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnSelesai.visibility = View.GONE
            }
            "Diterima" -> {
                layout_harga.visibility = View.GONE
                imvBukti.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnSelesai.visibility = View.GONE
            }

            "DP" -> {
                layout_harga.visibility = View.GONE
                imvBukti.visibility = View.VISIBLE
                btnProses.visibility = View.VISIBLE
                btnSelesai.visibility = View.GONE
            }

            "Diproses" -> {
                layout_harga.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnSelesai.visibility = View.VISIBLE
            }

            "Selesai" -> {
                layout_harga.visibility = View.GONE
                imvBukti.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnSelesai.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultUpdateharga(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil memasukan harga", Toast.LENGTH_SHORT).show()
    }

    override fun onResultUpdateproses(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil Diproses", Toast.LENGTH_SHORT).show()
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progresspeng.visibility = View.VISIBLE
                btnKirim.visibility = View.GONE
            }
            false -> {
                progresspeng.visibility = View.GONE
                btnKirim.visibility = View.VISIBLE
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (pengajuan.latitude!!.toDouble(), pengajuan.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( pengajuan.kd_produk ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}