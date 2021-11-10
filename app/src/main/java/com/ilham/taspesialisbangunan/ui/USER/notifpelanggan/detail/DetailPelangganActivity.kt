package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_detail_pelanggan.*
import kotlinx.android.synthetic.main.activity_detail_pelanggan.btnKirim
import kotlinx.android.synthetic.main.activity_detail_pelanggan.imvBukti
import kotlinx.android.synthetic.main.activity_detail_pelanggan.imvPengajuan
import kotlinx.android.synthetic.main.activity_detail_pelanggan.progresspeng
import kotlinx.android.synthetic.main.activity_detail_pelanggan.txtDeskripsi

class DetailPelangganActivity : AppCompatActivity(), DetailPelangganContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: DetailPelangganPresenter
    lateinit var pengajuan: DataPengajuan
    lateinit var rekeningAdapter: RekeningAdapter
    lateinit var rekening: DataTambahrek



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pelanggan)

        presenter = DetailPelangganPresenter(this)
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

        rekeningAdapter = RekeningAdapter(this , arrayListOf()){
                datatambahrek: DataTambahrek, position: Int, type: String ->
            Constant.TAMBAHREK_ID = datatambahrek.kd_rekening!!

            rekening = datatambahrek

        }

    }

    override fun initListener() {
        btnKirim.setOnClickListener {
            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT).show()
            } else {
                presenter.buktiPengajuan(pengajuan.kd_pengajuan!!, FileUtils.getFile(this, uriImage))
            }
        }
        imvBukti.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }
    }

    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan

        GlideHelper.setImage(this, Constant.IP_IMAGE + "uploads/" + pengajuan.gambar, imvPengajuan)
        txtDeskripsi.text = pengajuan.deskripsi
        txtHarga.text = pengajuan.harga
        hargaDp.text = (Integer.valueOf(pengajuan.harga) * 30 / 100).toString()
        txtstatus2.text = pengajuan.status

        presenter.getTampilprodukrekening(pengajuan.kd_jasa!!)

        when (pengajuan.status) {
            "Menunggu" -> {
                layout_bukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
            }
            "Diterima" -> {
                layout_bukti.visibility = View.VISIBLE
                layout_harga1.visibility = View.VISIBLE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
                layout_rekeninge.visibility = View.VISIBLE
            }
            "DP" -> {
                layout_bukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
            }
            "Diproses" -> {
                layout_bukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
            }
            "Pelunasan" -> {
                layout_bukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
            }
            "Selesai" -> {
                layout_bukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status1.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil Mengirim Bukti", Toast.LENGTH_SHORT).show()
    }

    override fun onResultTampilprodukrek(responseTambahrekList: ResponseTambahrekList) {
        val dataTambahrek: List<DataTambahrek> = responseTambahrekList.dataTambahrek
        rekeningAdapter.setData(dataTambahrek)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvBukti.setImageURI(uriImage)
        }
    }
}