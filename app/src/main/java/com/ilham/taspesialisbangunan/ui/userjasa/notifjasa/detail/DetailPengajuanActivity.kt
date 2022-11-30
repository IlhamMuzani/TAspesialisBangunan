package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.saran.DataSaran
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_detail_pengajuan.*
import kotlinx.android.synthetic.main.activity_detail_pengajuan.txtDeskripsi
import kotlinx.android.synthetic.main.activity_loginjasa.*
import kotlinx.android.synthetic.main.dialog_bukti.view.*
import kotlinx.android.synthetic.main.dialog_harga.*
import kotlinx.android.synthetic.main.dialog_harga.view.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.text.NumberFormat
import java.util.*

class DetailPengajuanActivity : AppCompatActivity(), DetailPengajuanContract.View,
    OnMapReadyCallback {

    lateinit var presenter: DetailPengajuanPresenter
    lateinit var pengajuan: DataPengajuan
    lateinit var produk: DataProduk
    lateinit var saran: DataSaran
    lateinit var saranJasaAdapter: SaranJasaAdapter
    lateinit var harga1: EditText

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



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
        presenter.getSaranperproduk(Constant.PENGAJUAN_ID)

    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text = "Detail Notifikasi"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        saranJasaAdapter = SaranJasaAdapter(
            this,
            arrayListOf()
        ) { dataSaran: DataSaran, position: Int, type: String ->

            saran = dataSaran
        }

        Rcv_Saran.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = saranJasaAdapter
        }

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btnKonfirmasi.setOnClickListener {
            onShowDialogkonfirmasi()
        }

        btnProses.setOnClickListener {
            onShowDialogkerjakan()
        }

        btnProsesbayarcash.setOnClickListener {
            onShowDialogkerjakanbayarcash()
        }

        btnPelunasanjasa.setOnClickListener {
            onShowDialogkepelunasan()
        }

        btnPelunasanjasabayarcash.setOnClickListener {
            onShowDialogkepelunasanbayarcash()
        }

        imvBuktibayar.setOnClickListener {
            Constant.PENGAJUAN_ID = pengajuan.id!!
            onShowDialogdetailgambar()
        }

        imvPengajuanjasa.setOnClickListener {
            Constant.PENGAJUAN_ID = pengajuan.id!!
            onShowDialogdetailgambarpengajuanfull()
        }

        btnTolak2.setOnClickListener {
            onShowDialogHapuslainya()
        }
        btnTerima2.setOnClickListener {
            onShowDialogHarga()
        }

        hubungipembeli.setOnClickListener {
            if (isWhatsappInstalled()) {

                val i = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=" + pengajuan.phone)
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

    override fun onShowDialogkonfirmasi() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Terima ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.cektempatlokasi(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onShowDialogkerjakan() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Kerjakan ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanjasaDPkeProses(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()

        }

        dialog.show()

    }

    override fun onShowDialogkerjakanbayarcash() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Kerjakan ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanjasaDPkeProsesbayarcash(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()

        }

        dialog.show()

    }

    override fun onShowDialogkepelunasan() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Lanjutkan ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanjasaProseskePelunasan(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()

        }

        dialog.show()

    }

    override fun onShowDialogkepelunasanbayarcash() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("${pengajuan.produk.jenis_pembuatan} Sudah Selesai?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanjasaProseskePelunasanbayarcash(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()

        }

        dialog.show()

    }

    override fun onShowDialogdetailgambar() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_gambar, null)
        val imvgambar = view.findViewById<ImageView>(R.id.imvgambar)

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.bukti, imvgambar)

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onShowDialogdetailgambarpengajuanfull() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_gambarpengajuan, null)
        val imvPengajuan = view.findViewById<ImageView>(R.id.imvgambarpengajuan)

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.gambar, imvPengajuan)

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onShowDialogHapus() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deletePengajuanjasa(Constant.PENGAJUAN_ID)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onShowDialogHapuslainya() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deletePengajuanjasa(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onShowDialogHarga() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_harga, null)
        harga1 = view.edtHargad

        val spinnerDP = view.findViewById<Spinner>(R.id.spinnerDP)
        spinnerDP(spinnerDP)

//        val harga = view.findViewById<EditText>(R.id.edtHargad)

        val spinnerPanjang = view.findViewById<Spinner>(R.id.spinnerPanjang)
        spinnerPanjang(spinnerPanjang, harga1)

        val spinnerLebar = view.findViewById<Spinner>(R.id.spinnerLebar)
        spinnerLebar(spinnerLebar, harga1)

        view.btnKirimd.setOnClickListener {

            if (view.edtdeskripsilainya.text!!.isEmpty()) {
                view.edtdeskripsilainya.error = "Kolom Deskripsi Tidak Boleh Kosong"
                view.edtdeskripsilainya.requestFocus()
            } else if (Constant.STATUSDP_ID == 0) {
                showMessage("Pilih Kategori Produk")
            } else if (Constant.PANJANG_ID == 0) {
                showMessage("Pilih Panjang")
            } else if (Constant.LEBAR_ID == 0) {
                showMessage("Pilih Lebar")
            } else if (view.edtHargad.text!!.isEmpty()) {
                view.edtHargad.error = "Kolom Harga Tidak Boleh Kosong"
                view.edtHargad.requestFocus()
            } else {
                presenter.hargaPengajuan(
                    pengajuan.id!!,
                    view.edtHargad.text.toString(),
                    view.edtdeskripsilainya.text.toString(),
                    Constant.STATUSDP_NAME,
                    Constant.PANJANG_NAME,
                    Constant.LEBAR_NAME
                )

                dialog.dismiss()
                finish()
            }
        }

        dialog.setContentView(view)
        dialog.show()

    }

    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.gambar, imvPengajuanjasa)
        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.bukti, imvBuktibayar)
        txtDeskripsi.text = pengajuan.deskripsi
        txtStatus.text = pengajuan.status
        txtNama.text = pengajuan.user.username
        txtAlamatP.text = pengajuan.alamat
        txtjenisP.text = pengajuan.produk.jenis_pembuatan
        txtPhoneP.text = pengajuan.phone
        txtHargajasa.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))
        hargaDpJas.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 30 / 100).toString()
        hargaDp50.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 50 / 100).toString()
        hargaDp75.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 75 / 100).toString()
        hargapelunasanjas.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 70 / 100).toString()
        bayarlunas.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))
        txtTanggalPemesananjasa.text = pengajuan.created_at
        txtTanggalSelesaijasa.text = pengajuan.updated_at
        txvcategorydetailnotif.text = pengajuan.categori_pesanan
        txtDeskripsipesan.text = pengajuan.pesan

        txtDeskripsikesepakatanjasa.text = pengajuan.deskripsi_kesepakatan
        txtDPjasa.text = pengajuan.status_dp
        txtpanjangjasa.text = pengajuan.panjang
        txtlebarjasa.text = pengajuan.lebar

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.produk.gambar, imvImagedetailnotif)
        txvjenispembuatandetailnotif.text = pengajuan.produk.jenis_pembuatan
        txvhargadetailnotif.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.produk.harga))

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapdetailpengajuan) as SupportMapFragment
        mapFragment.getMapAsync(this)

        when (pengajuan.status) {
            "Menunggu" -> {
                layoutBukti1.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                layouthargajasa.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.GONE
            }

            "Dikonfirmasi" -> {
                layoutBukti1.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.VISIBLE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.GONE

            }

            "Bertemu" -> {
                layoutBukti1.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.VISIBLE
                pemberitahuanditolak.visibility = View.GONE
                layouthargajasa.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.GONE

            }
            "Ditolak" -> {
                layoutBukti1.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.VISIBLE
                pemberitahuanditolak.visibility = View.VISIBLE
                layouthargajasa.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.GONE

            }

            "Diterima" -> {
                layoutBukti1.visibility = View.GONE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE
            }

            "DP" -> {
                when (pengajuan.status_dp) {
                    "30 %" -> {
                        hargaDpJas.visibility = View.VISIBLE
                        hargaDp50.visibility = View.GONE
                        hargaDp75.visibility = View.GONE
                        layoutbayarlunas.visibility = View.GONE
                    }
                    "50 %" -> {
                        hargaDpJas.visibility = View.GONE
                        hargaDp50.visibility = View.VISIBLE
                        hargaDp75.visibility = View.GONE
                        layoutbayarlunas.visibility = View.GONE
                    }
                    "75 %" -> {
                        hargaDpJas.visibility = View.GONE
                        hargaDp50.visibility = View.GONE
                        hargaDp75.visibility = View.VISIBLE
                        layoutbayarlunas.visibility = View.GONE
                    }
                }
                btnProses.visibility = View.VISIBLE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutpelunasan2.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.VISIBLE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }

            "Sudah Lunas" -> {
                layoutBukti1.visibility = View.VISIBLE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutpelunasan2.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.VISIBLE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.VISIBLE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutbayardp.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }

            "Dikerjakan" -> {
                btnProses.visibility = View.GONE
                layoutBukti1.visibility = View.GONE
                btnPelunasanjasa.visibility = View.VISIBLE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.VISIBLE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }

            "Dikerjakan*" -> {
                btnProses.visibility = View.GONE
                layoutBukti1.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.VISIBLE
                pemberitahuanselesai.visibility = View.VISIBLE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }

            "Pelunasan" -> {
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.GONE
                layoutBukti1.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.GONE
                layout_tanggalSelesaijasa.visibility = View.GONE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }

            "Selesai" -> {
                layoutBukti1.visibility = View.VISIBLE
                btnProses.visibility = View.GONE
                btnPelunasanjasa.visibility = View.GONE
                layout_saranjasa.visibility = View.VISIBLE
                layoutdp2.visibility = View.GONE
                layoutTanggalPemesananjasa.visibility = View.VISIBLE
                layout_tanggalSelesaijasa.visibility = View.VISIBLE
                btnKonfirmasi.visibility = View.GONE
                pemberitahuantemui.visibility = View.GONE
                pemberitahuanproses.visibility = View.GONE
                pemberitahuanpelunasan.visibility = View.GONE
                btnProsesbayarcash.visibility = View.GONE
                btnPelunasanjasabayarcash.visibility = View.GONE
                pemberitahuanselesai.visibility = View.GONE
                layoutterimatolak2.visibility = View.GONE
                pemberitahuanditolak.visibility = View.GONE
                layoutkesepakatanjasa.visibility = View.VISIBLE

            }
        }
    }

    override fun onResultUpdateharga(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil Terkirim", Toast.LENGTH_SHORT).show()
    }

    override fun onResultUpdateproses(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onResultSaranJasaProduk(responseSaranList: ResponseSaranList) {
        val dataSaran: List<DataSaran> = responseSaranList.dataSaran
        saranJasaAdapter.setData(dataSaran)
    }

    override fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage(responsePengajuanUpdate.msg)
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(pengajuan.latitude!!.toDouble(), pengajuan.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title(pengajuan.kd_produk))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
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

    fun spinnerDP(spinner: Spinner) {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih DP")
        arrayString.add("30 %")
        arrayString.add("50 %")
        arrayString.add("75 %")


        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val selection = adapter.getPosition(Constant.STATUSDP_NAME)
        spinner.setSelection(selection)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.STATUSDP_ID = 0
                        Constant.STATUSDP_NAME = "Pilih DP"
                    }
                    1 -> {
                        Constant.STATUSDP_ID = 1
                        Constant.STATUSDP_NAME = "30 %"
                    }
                    2 -> {
                        Constant.STATUSDP_ID = 2
                        Constant.STATUSDP_NAME = "50 %"
                    }
                    else -> {
                        Constant.STATUSDP_ID = 3
                        Constant.STATUSDP_NAME = "75 %"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerPanjang(spinner: Spinner, edtHargad: EditText) {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Panjang")
        arrayString.add("1 m")
        arrayString.add("2 m")
        arrayString.add("3 m")
        arrayString.add("4 m")
        arrayString.add("5 m")
        arrayString.add("6 m")
        arrayString.add("7 m")
        arrayString.add("8 m")
        arrayString.add("9 m")
        arrayString.add("10 m")
        arrayString.add("11 m")
        arrayString.add("12 m")
        arrayString.add("13 m")
        arrayString.add("14 m")
        arrayString.add("15 m")
        arrayString.add("16 m")
        arrayString.add("17 m")
        arrayString.add("18 m")
        arrayString.add("19 m")
        arrayString.add("20 m")


        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val selection = adapter.getPosition(Constant.PANJANG_NAME)
        spinner.setSelection(selection)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.PANJANG_ID = 0
                        Constant.PANJANG_NAME = "Pilih Panjang"
                    }
                    1 -> {
                        Constant.PANJANG_ID = 1
                        Constant.PANJANG_NAME = "1 m"
                    }
                    2 -> {
                        Constant.PANJANG_ID = 2
                        Constant.PANJANG_NAME = "2 m"
                    }
                    3 -> {
                        Constant.PANJANG_ID = 3
                        Constant.PANJANG_NAME = "3 m"
                    }
                    4 -> {
                        Constant.PANJANG_ID = 4
                        Constant.PANJANG_NAME = "4 m"
                    }
                    5 -> {
                        Constant.PANJANG_ID = 5
                        Constant.PANJANG_NAME = "5 m"
                    }
                    6 -> {
                        Constant.PANJANG_ID = 6
                        Constant.PANJANG_NAME = "6 m"
                    }
                    7 -> {
                        Constant.PANJANG_ID = 7
                        Constant.PANJANG_NAME = "7 m"
                    }
                    8 -> {
                        Constant.PANJANG_ID = 8
                        Constant.PANJANG_NAME = "8 m"
                    }
                    9 -> {
                        Constant.PANJANG_ID = 9
                        Constant.PANJANG_NAME = "9 m"
                    }
                    10 -> {
                        Constant.PANJANG_ID = 10
                        Constant.PANJANG_NAME = "10 m"
                    }
                    11 -> {
                        Constant.PANJANG_ID = 11
                        Constant.PANJANG_NAME = "11 m"
                    }
                    12 -> {
                        Constant.PANJANG_ID = 12
                        Constant.PANJANG_NAME = "12 m"
                    }
                    13 -> {
                        Constant.PANJANG_ID = 13
                        Constant.PANJANG_NAME = "13 m"
                    }
                    14 -> {
                        Constant.PANJANG_ID = 14
                        Constant.PANJANG_NAME = "14 m"
                    }
                    15 -> {
                        Constant.PANJANG_ID = 15
                        Constant.PANJANG_NAME = "15 m"
                    }
                    16 -> {
                        Constant.PANJANG_ID = 16
                        Constant.PANJANG_NAME = "16 m"
                    }
                    17 -> {
                        Constant.PANJANG_ID = 17
                        Constant.PANJANG_NAME = "17 m"
                    }
                    18 -> {
                        Constant.PANJANG_ID = 18
                        Constant.PANJANG_NAME = "18 m"
                    }
                    19 -> {
                        Constant.PANJANG_ID = 19
                        Constant.PANJANG_NAME = "19 m"
                    }
                    else -> {
                        Constant.PANJANG_ID = 20
                        Constant.PANJANG_NAME = "20 m"
                    }
                }
                if (Constant.PANJANG_ID != 0 && Constant.LEBAR_ID != 0) {
                    if (pengajuan.produk.category == "Produk Jasa dan Material" && pengajuan.produk.ukuran == "1 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "2 m"){
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 2 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 2 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "3 m"){
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 3 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 3 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "4 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 4 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 4 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "5 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 5 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 5 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "6 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 6 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 6 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "7 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 7 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 7 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "8 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 8 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 8 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "9 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 9 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 9 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "10 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 10 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 10 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    }else{
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    fun spinnerLebar(spinner: Spinner, edtHargad: EditText) {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Lebar")
        arrayString.add("1 m")
        arrayString.add("2 m")
        arrayString.add("3 m")
        arrayString.add("4 m")
        arrayString.add("5 m")
        arrayString.add("6 m")
        arrayString.add("7 m")
        arrayString.add("8 m")
        arrayString.add("9 m")
        arrayString.add("10 m")
        arrayString.add("11 m")
        arrayString.add("12 m")
        arrayString.add("13 m")
        arrayString.add("14 m")
        arrayString.add("15 m")
        arrayString.add("16 m")
        arrayString.add("17 m")
        arrayString.add("18 m")
        arrayString.add("19 m")
        arrayString.add("20 m")


        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val selection = adapter.getPosition(Constant.LEBAR_NAME)
        spinner.setSelection(selection)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.LEBAR_ID = 0
                        Constant.LEBAR_NAME = "Pilih Lebar"
                    }
                    1 -> {
                        Constant.LEBAR_ID = 1
                        Constant.LEBAR_NAME = "1 m"
                    }
                    2 -> {
                        Constant.LEBAR_ID = 2
                        Constant.LEBAR_NAME = "2 m"
                    }

                    3 -> {
                        Constant.LEBAR_ID = 3
                        Constant.LEBAR_NAME = "3 m"
                    }
                    4 -> {
                        Constant.LEBAR_ID = 4
                        Constant.LEBAR_NAME = "4 m"
                    }
                    5 -> {
                        Constant.LEBAR_ID = 5
                        Constant.LEBAR_NAME = "5 m"
                    }
                    6 -> {
                        Constant.LEBAR_ID = 6
                        Constant.LEBAR_NAME = "6 m"
                    }
                    7 -> {
                        Constant.LEBAR_ID = 7
                        Constant.LEBAR_NAME = "7 m"
                    }
                    8 -> {
                        Constant.LEBAR_ID = 8
                        Constant.LEBAR_NAME = "8 m"
                    }
                    9 -> {
                        Constant.LEBAR_ID = 9
                        Constant.LEBAR_NAME = "9 m"
                    }
                    10 -> {
                        Constant.LEBAR_ID = 10
                        Constant.LEBAR_NAME = "10 m"
                    }
                    11 -> {
                        Constant.LEBAR_ID = 11
                        Constant.LEBAR_NAME = "11 m"
                    }
                    12 -> {
                        Constant.LEBAR_ID = 12
                        Constant.LEBAR_NAME = "12 m"
                    }
                    13 -> {
                        Constant.LEBAR_ID = 13
                        Constant.LEBAR_NAME = "13 m"
                    }
                    14 -> {
                        Constant.LEBAR_ID = 14
                        Constant.LEBAR_NAME = "14 m"
                    }
                    15 -> {
                        Constant.LEBAR_ID = 15
                        Constant.LEBAR_NAME = "15 m"
                    }
                    16 -> {
                        Constant.LEBAR_ID = 16
                        Constant.LEBAR_NAME = "16 m"
                    }
                    17 -> {
                        Constant.LEBAR_ID = 17
                        Constant.LEBAR_NAME = "17 m"
                    }
                    18 -> {
                        Constant.LEBAR_ID = 18
                        Constant.LEBAR_NAME = "18 m"
                    }
                    19 -> {
                        Constant.LEBAR_ID = 19
                        Constant.LEBAR_NAME = "19 m"
                    }
                    else -> {
                        Constant.LEBAR_ID = 20
                        Constant.LEBAR_NAME = "20 m"
                    }
                }
                if (Constant.PANJANG_ID != 0 && Constant.LEBAR_ID != 0) {
                    if (pengajuan.produk.category == "Produk Jasa dan Material" && pengajuan.produk.ukuran == "1 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "2 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt()/ 2 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt()/ 2 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "3 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 3 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 3 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "4 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 4 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 4 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "5 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 5 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 5 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "6 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 6 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 6 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "7 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 7 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 7 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "8 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 8 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 8 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "9 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 9 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 9 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    } else if (pengajuan.produk.ukuran == "10 m") {
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() / 10 * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() / 10 * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    }else{
                        val panjang = Constant.PANJANG_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        val lebar = Constant.LEBAR_ID * pengajuan.produk.harga!!.toInt() * 50 / 100
                        edtHargad.setText((panjang + lebar).toString())
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}