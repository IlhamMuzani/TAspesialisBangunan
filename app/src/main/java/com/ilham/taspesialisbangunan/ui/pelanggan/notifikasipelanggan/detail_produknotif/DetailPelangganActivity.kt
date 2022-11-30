package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detailnotif

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekTampilrekening
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_detail_pelanggan.*
import kotlinx.android.synthetic.main.activity_detail_pelanggan.imvBukti
import kotlinx.android.synthetic.main.activity_detail_pelanggan.imvPengajuan
import kotlinx.android.synthetic.main.activity_detail_pelanggan.progresspeng
import kotlinx.android.synthetic.main.activity_detail_pelanggan.txtDeskripsi
import kotlinx.android.synthetic.main.dialog_rekening.view.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailPelangganActivity : AppCompatActivity(), DetailPelangganContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: DetailPelangganPresenter
    lateinit var pengajuan: DataPengajuan
    lateinit var produk: DataProduk
    lateinit var rekeningAdapter: RekeningAdapter
    lateinit var rekening: DataTambahrek
    lateinit var prefsManager: PrefsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pelanggan)
        presenter = DetailPelangganPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
//        layout.visibility = View.GONE
//        onLoading(true)
        presenter.getDetail(Constant.PENGAJUAN_ID)
    }

    override fun initActivity() {

        tv_nama.text = "Pengajuan"

        rekeningAdapter = RekeningAdapter(this , arrayListOf()){
                datatambahrek: DataTambahrek, position: Int, type: String ->
            Constant.TAMBAHREK_ID = datatambahrek.kd_rekening!!

            rekening = datatambahrek

        }

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnKirimBukti.setOnClickListener {
            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT).show()
            } else {
                presenter.buktiPengajuan(pengajuan.id!!, FileUtils.getFile(this, uriImage))

                layout_rekeninge2.visibility = View.GONE
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                layoutdp.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
            }

        }
        btnPelunasan.setOnClickListener {
            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT).show()
            } else {
                presenter.buktiPelunasan(pengajuan.id!!, FileUtils.getFile(this, uriImage))

                layoutpelunasan.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                layoutdp.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                layoutbukti.visibility = View.GONE
            }
        }

        btnSaran.setOnClickListener {
            val deskripsi = txtSaran.text

            if ( deskripsi.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertSaran(Constant.PRODUK_ID.toString(), prefsManager.prefsId, Constant.PENGAJUAN_ID.toString(), deskripsi.toString()
                )
            }
        }

        imvBukti.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        layout_rekeninge2.setOnClickListener {
            presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
        }

    }

    override fun onShowDialogRek(dataTambahrek: List<DataTambahrek>) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_rekening, null)

        rekeningAdapter.setData(dataTambahrek)

        view.layout_rekeninge.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekeningAdapter
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.produk.gambar, imvPengajuan)
        txtDeskripsi.text = pengajuan.deskripsi
        txtHarga.text = pengajuan.harga
        hargaDp.text = (Integer.valueOf(pengajuan.harga) * 30 / 100).toString()
        txtstatus.text = pengajuan.status
        hargapelunasan.text = (Integer.valueOf(pengajuan.harga) * 70 / 100).toString()

        when (pengajuan.status) {
            "Menunggu" -> {
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                layoutdp.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status2.visibility = View.VISIBLE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE

            }
            "Diterima" -> {
                layoutbukti.visibility = View.VISIBLE
                layout_harga1.visibility = View.VISIBLE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_rekeninge2.visibility = View.VISIBLE
                btnKirimBukti.visibility = View.VISIBLE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE

//                presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
            }
            "DP" -> {
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layoutdp.visibility = View.GONE
                layoutpelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                btnPelunasan.visibility = View.GONE

            }
            "Diproses" -> {
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layoutdp.visibility = View.GONE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE

            }
            "Pelunasan" -> {
                btnKirimBukti.visibility = View.GONE
                layoutpelunasan.visibility = View.VISIBLE
                layout_harga1.visibility = View.VISIBLE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layoutdp.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                layoutbukti.visibility = View.VISIBLE
                btnPelunasan.visibility = View.VISIBLE

//                presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
            }
            "Selesai" -> {
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layoutdp.visibility = View.GONE
                layout_saran.visibility = View.VISIBLE
                btnSaran.visibility = View.VISIBLE
                btnKirimBukti.visibility = View.GONE

            }
            "Tunggu" -> {
                layoutbukti.visibility = View.GONE
                layout_harga1.visibility = View.GONE
                layoutdp.visibility = View.GONE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status2.visibility = View.VISIBLE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE

            }

            "Terkonfirmasi" -> {
                layoutbukti.visibility = View.VISIBLE
                layout_harga1.visibility = View.VISIBLE
                imvPengajuan.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_rekeninge2.visibility = View.VISIBLE
                btnKirimBukti.visibility = View.VISIBLE
                layoutpelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
            }
        }
    }

    override fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil Mengirim Bukti", Toast.LENGTH_SHORT).show()
    }

    override fun onResultTampilprodukrek(responseTambahrekTampilrekening: ResponseTambahrekTampilrekening) {
        val dataTambahrek: List<DataTambahrek> = responseTambahrekTampilrekening.dataTambahrek

        onShowDialogRek(dataTambahrek)
    }

    override fun onResultSaran(responseSaranInsert: ResponseSaranInsert) {
        showMessage(responseSaranInsert.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progresspeng.visibility = View.VISIBLE
            }
            false -> {
                progresspeng.visibility = View.GONE
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