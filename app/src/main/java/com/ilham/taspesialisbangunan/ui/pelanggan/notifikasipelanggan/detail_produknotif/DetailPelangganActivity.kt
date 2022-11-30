package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detail_produknotif

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.printservice.PrintDocument
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.measurement.AppMeasurement
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
import com.ilham.taspesialisbangunan.ui.pelanggan.invoice.InvoiceActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_detail_pelanggan.*
import kotlinx.android.synthetic.main.activity_detail_pengajuan.*
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.dialog_bukti.view.*
import kotlinx.android.synthetic.main.dialog_cast.view.*
import kotlinx.android.synthetic.main.dialog_cast.view.layoutdp1
import kotlinx.android.synthetic.main.dialog_castdp.view.*
import kotlinx.android.synthetic.main.dialog_rekening.view.*
import kotlinx.android.synthetic.main.dialog_saran.view.*
import kotlinx.android.synthetic.main.dialog_tolakkesepakatan.view.*
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileOutputStream
import java.nio.file.SimpleFileVisitor
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import org.w3c.dom.Document as Document

class DetailPelangganActivity : AppCompatActivity(), DetailPelangganContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: DetailPelangganPresenter
    lateinit var pengajuan: DataPengajuan
    lateinit var produk: DataProduk
    lateinit var rekeningAdapter: RekeningAdapter
    lateinit var rekening: DataTambahrek
    lateinit var prefsManager: PrefsManager
    lateinit var imvBukti: ImageView
    lateinit var layoutdp1: LinearLayout
    lateinit var btnbukti: Button
    lateinit var layoutpelunasannn: LinearLayout
    lateinit var btnpelunasan: Button
    lateinit var btnCetak : Button
    lateinit var namatoko : TextView

    private val STORAGE_CADE = 1001

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


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
        onLoading(true)
        presenter.getDetail(Constant.PENGAJUAN_ID)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")
        btnCetak = findViewById(R.id.btnCetak)
        namatoko = findViewById(R.id.txv_Namatoko)

        tv_nama.text = "Pengajuan"

        rekeningAdapter = RekeningAdapter(
            this,
            arrayListOf()
        ) { datatambahrek: DataTambahrek, position: Int, type: String ->
            Constant.TAMBAHREK_ID = datatambahrek.kd_rekening!!

            rekening = datatambahrek

        }

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnCetak.setOnClickListener {
            presenter.getDetail(Constant.PENGAJUAN_ID)
            startActivity(Intent(this, InvoiceActivity::class.java))
        }

//        btnCetak.setOnClickListener {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_DENIED){
//                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    requestPermissions(permission, STORAGE_CADE)
//                }else{
//                    savePDF()
//                }
//            }else{
//                savePDF()
//            }
//        }

        detailwhatsap.setOnClickListener {
            if (isWhatsappInstalled()) {

                val i = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=" +pengajuan.produk.phone )
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

        btnKirimBukti.setOnClickListener {
            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT)
                    .show()
            } else {
                presenter.buktiPengajuan(pengajuan.id!!, FileUtils.getFile(this, uriImage))
                btnKirimBukti.visibility = View.GONE
            }
        }

        btnkonfirmasijasa.setOnClickListener {
//            presenter.pengajuanjasakonfirmasibertemu(pengajuan.id!!)
//            btnkonfirmasijasa.visibility = View.GONE
            onShowDialogTerima()
        }

        btnPelunasan.setOnClickListener {
            onShowDialogPelunasan()
        }

        val rating = rating_bar
        var ratingValue = 0F
        rating.setOnRatingBarChangeListener { ratingBar, _, _ ->
            ratingValue = ratingBar.rating
        }

        btnSaran.setOnClickListener {
            val deskripsi = txtSaran.text

            if (deskripsi.isNullOrEmpty()) {
                showMessage("Masukkan Saran")
            } else if (ratingValue == 0F) {
                Toast.makeText(
                    applicationContext,
                    "Berikan Rating terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                presenter.insertSaran(
                    pengajuan.kd_produk.toString(),
                    prefsManager.prefsId,
                    Constant.PENGAJUAN_ID.toString(),
                    deskripsi.toString(), ratingValue.toString(),
                    "produk"
                )
            }
        }

        btnTolakuser.setOnClickListener {
            onShowDialogtolakkesepakatan()
        }

        btnTerimauser.setOnClickListener {
            onShowDialogPilihcastdp()
        }

        btnbayarditempat.setOnClickListener {
            onShowDialogBayarditempat()
        }
        btnTolaksaran.setOnClickListener {
            onShowDialogtolaksaran()
        }
        btnTerimasaran.setOnClickListener {
            onShowDialogSaran()
        }
    }

    override fun onShowDialogPilihcastdp() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_castdp, null)

        view.btncast.setOnClickListener {
            onShowDialogbayarcast()
            dialog.dismiss()
        }

        view.btnDP.setOnClickListener {
            onShowDialogDP()
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onShowDialogTerima() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Lanjutkan ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanjasakonfirmasibertemu(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
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

    override fun onShowDialogbayarcast() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_cast, null)
        imvBukti = view.imvBuktimatt3

        view.txtHargacastdialog.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))

        view.hargacast.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))


        view.layout_rekeninge4.setOnClickListener {
            presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
        }

        view.imvBuktimatt3.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        view.btnKirimbukticast.setOnClickListener {

            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT)
                    .show()
            } else {
                presenter.buktibayarcast(pengajuan.id!!, FileUtils.getFile(this, uriImage))

                dialog.dismiss()
                finish()
            }
        }

        dialog.setContentView(view)
        dialog.show()

    }

    override fun onShowDialogDP() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_bukti, null)
        imvBukti = view.imvBuktimatt2

        when(pengajuan.status_dp){
            "30 %" ->{
                view.hargaDp1.visibility = View.VISIBLE
            view.hargaDp2.visibility = View.GONE
            view.hargaDp3.visibility = View.GONE
            }
            "50 %" ->{
                view.hargaDp1.visibility = View.GONE
            view.hargaDp2.visibility = View.VISIBLE
            view.hargaDp3.visibility = View.GONE
            }
            "75 %" ->{
                view.hargaDp1.visibility = View.GONE
            view.hargaDp2.visibility = View.GONE
            view.hargaDp3.visibility = View.VISIBLE
            }
        }


        layoutpelunasannn = view.layoutpelunasan1
        layoutpelunasannn.visibility = View.GONE

        btnpelunasan = view.btnKirimPelunasan
        btnpelunasan.visibility = View.GONE

        view.txtHargadialog.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))
        view.hargaDp1.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 30 / 100).toString()
        view.hargaDp2.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 50 / 100).toString()
        view.hargaDp3.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 75 / 100).toString()


        view.layout_rekeninge3.setOnClickListener {
            presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
        }

        view.imvBuktimatt2.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        view.btnKirimBukti2.setOnClickListener {

            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT)
                    .show()
            } else {
                presenter.buktiPengajuan(pengajuan.id!!, FileUtils.getFile(this, uriImage))

                dialog.dismiss()
                finish()
            }
        }

        dialog.setContentView(view)
        dialog.show()

    }

    override fun onShowDialogSaran() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_saran, null)
        val rating = view.rating_barr22

        var ratingValue = 0F
        rating.setOnRatingBarChangeListener { ratingBar, _, _ ->
            ratingValue = ratingBar.rating
        }

        view.btnSaran22.setOnClickListener {
            val deskripsi = view.txtSaran22.text

            if (deskripsi.isNullOrEmpty()) {
                showMessage("Masukkan Saran")
            } else if (ratingValue == 0F) {
                Toast.makeText(
                    applicationContext,
                    "Berikan Rating terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                presenter.insertSaran(
                    pengajuan.kd_produk.toString(),
                    prefsManager.prefsId,
                    Constant.PENGAJUAN_ID.toString(),
                    deskripsi.toString(), ratingValue.toString(),
                    "produk")

                presenter.pengajuanuserselesaicash(Constant.PENGAJUAN_ID)

                dialog.dismiss()
                finish()
            }
            }
        dialog.setContentView(view)
        dialog.show()

    }

    override fun onShowDialogPelunasan() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_bukti, null)
        imvBukti = view.imvBuktimatt2
        layoutdp1 = view.layoutdp1
        layoutdp1.visibility = View.GONE
        btnbukti = view.btnKirimBukti2
        btnbukti.visibility = View.GONE

        when(pengajuan.status_dp){
            "30 %" ->{
                view.hargapelunasan2.visibility = View.VISIBLE
                view.hargapelunasan3.visibility = View.GONE
                view.hargapelunasan4.visibility = View.GONE
            }
            "50 %" ->{
                view.hargapelunasan2.visibility = View.GONE
                view.hargapelunasan3.visibility = View.VISIBLE
                view.hargapelunasan4.visibility = View.GONE
            }
            "75 %" ->{
                view.hargapelunasan2.visibility = View.GONE
                view.hargapelunasan3.visibility = View.GONE
                view.hargapelunasan4.visibility = View.VISIBLE
            }
        }

        view.txtHargadialog.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))
        view.hargapelunasan2.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 75 / 100).toString()
        view.hargapelunasan3.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 50 / 100).toString()
        view.hargapelunasan4.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga) * 30 / 100).toString()

        view.layout_rekeninge3.setOnClickListener {
            presenter.getTampilprodukrekening(pengajuan.produk.kd_user!!)
        }

        view.imvBuktimatt2.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        view.btnKirimPelunasan.setOnClickListener {
            if (uriImage == null) {
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT)
                    .show()
            } else {
                presenter.buktiPelunasan(pengajuan.id!!, FileUtils.getFile(this, uriImage))


                btnPelunasan.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                layout_saran.visibility = View.VISIBLE
                btnSaran.visibility = View.VISIBLE
                dialog.dismiss()
            }
        }

        dialog.setContentView(view)
        dialog.show()

    }

    override fun onShowDialogHapuspengajuan() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deletePengajuanuser(Constant.PENGAJUAN_ID)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onShowDialogBayarditempat() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Sudah Bayar ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanbayarditempat(Constant.PENGAJUAN_ID)
            btnPelunasan.visibility = View.GONE
            btnKirimBukti.visibility = View.GONE
            layoutPelunasan.visibility = View.GONE

            layout_saran.visibility = View.VISIBLE
            btnSaran.visibility = View.VISIBLE
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onShowDialogtolaksaran() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Abaikan Saran ${pengajuan.produk.jenis_pembuatan}?")

        dialog.setPositiveButton("Konfirmasi") { dialog, which ->
            presenter.pengajuanuserselesaicash(Constant.PENGAJUAN_ID)
            dialog.dismiss()
            finish()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
//            dikonfirmasiadapter.removePengajuanmenunggujasa(position)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onShowDialogtolakkesepakatan() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_tolakkesepakatan, null)

        view.btnkirimtolak.setOnClickListener {

            val pesan = view.edtdeskripsitolak.text
            if (pesan.isNullOrEmpty()){
                showMessage("Lengkapi data")
            }else {
                presenter.tolakkesepakatan(pengajuan.id!!, pesan.toString())

                dialog.dismiss()
                finish()
            }
        }
        dialog.setContentView(view)
        dialog.show()

    }

    override fun onResultUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil Mengirim Bukti", Toast.LENGTH_SHORT).show()
    }

    override fun onResultUpdatekonfirbertemu(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Konfirmasi Berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onResultBayarditempat(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onResultTampilprodukrek(responseTambahrekTampilrekening: ResponseTambahrekTampilrekening) {
        val dataTambahrek: List<DataTambahrek> = responseTambahrekTampilrekening.dataTambahrek

        onShowDialogRek(dataTambahrek)
    }

    override fun onResultSaran(responseSaranInsert: ResponseSaranInsert) {
        showMessage(responseSaranInsert.msg)
        finish()
    }

    override fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage(responsePengajuanUpdate.msg)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvBukti.setImageURI(uriImage)
        }
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


    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan

        GlideHelper.setImage(this, Constant.IP_IMAGE + pengajuan.produk.gambar, imvpengajuanA)
        txv_Namatoko.text = pengajuan.produk.model
        txtDeskripsi1.text = pengajuan.deskripsi
            .format(Integer.valueOf(pengajuan.harga) * 30 / 100).toString()
//        jenispembuatan.text = pengajuan.categori_pesanan
        txtPhone1.text = pengajuan.produk.phone
        txtstatus.text = pengajuan.status
//        modeldetailnotif.text = pengajuan.produk.jenis_pembuatan
        hargaproduk.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.produk.harga))
            .format(Integer.valueOf(pengajuan.harga) * 70 / 100).toString()
        txtHarga2.text = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(Integer.valueOf(pengajuan.harga))
        txttanggalpemesanan.text = pengajuan.created_at
        txttanggalselesai.text = pengajuan.updated_at
        txtnamasaya.text = pengajuan.user.username
        txtalamatsaya.text = pengajuan.alamat
        txtcategorisaya.text = pengajuan.categori_pesanan
        txtpanjang.text = pengajuan.panjang
        txtlebar.text = pengajuan.lebar
        txtDP.text = pengajuan.status_dp
//        modeljenis.text = pengajuan.produk.model
        txtDeskripsikesepakatan.text = pengajuan.deskripsi_kesepakatan

        when (pengajuan.status) {
            "Menunggu" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status2.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                view1.visibility = View.VISIBLE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                txtHarga2.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.GONE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE
            }

            "Dikonfirmasi" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status2.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                txtHarga2.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.GONE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Bertemu" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_status2.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                txtHarga2.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.GONE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Diterima" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                btnKirimBukti.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutterimatolakuser.visibility = View.VISIBLE
                view1.visibility = View.VISIBLE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }
            "Ditolak" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                btnKirimBukti.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                view1.visibility = View.VISIBLE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                btnCetak.visibility = View.GONE

            }

            "DP" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Sudah Lunas" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Dikerjakan" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Dikerjakan*" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                view1.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Pelunasan" -> {
                btnKirimBukti.visibility = View.GONE
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnPelunasan.visibility = View.VISIBLE
                txvtunggukonfirmasi.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.VISIBLE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Sudah Lunas*" -> {
                btnKirimBukti.visibility = View.GONE
//                layoutpelunasan.visibility = View.VISIBLE
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                layout_saran.visibility = View.GONE
                btnSaran.visibility = View.GONE
                btnPelunasan.visibility = View.VISIBLE
                txvtunggukonfirmasi.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.GONE
                layout_tanggalselesai.visibility = View.GONE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutpilihbayar.visibility = View.GONE
                layoutberisaran.visibility = View.VISIBLE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE
                btnCetak.visibility = View.GONE

            }

            "Selesai" -> {
                imvpengajuanA.visibility = View.VISIBLE
                layout_deskripsi.visibility = View.VISIBLE
                btnPelunasan.visibility = View.GONE
                layout_saran.visibility = View.VISIBLE
                btnSaran.visibility = View.GONE
                btnKirimBukti.visibility = View.GONE
                txvtunggukonfirmasi.visibility = View.GONE
                txtsaran1.visibility = View.GONE
                view1.visibility = View.GONE
                layout_saran.visibility = View.GONE
                layout_tanggalpemesanan.visibility = View.VISIBLE
                layout_tanggalselesai.visibility = View.VISIBLE
                layoutterimatolakuser.visibility = View.GONE
                layoutkonfirmbertemu.visibility = View.GONE
                layoutPelunasan.visibility = View.GONE
                pemberitahuanpelunasan22.visibility = View.GONE
                layoutberisaran.visibility = View.GONE
                layoutdeskripsikesepakatan.visibility = View.VISIBLE
                laypemberitauankesepakatan.visibility = View.GONE

            }
        }
    }

    private fun savePDF() {
        val mDoc = com.itextpdf.text.Document()
        val mFileName = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis())

        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"

        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val data = pengajuan.produk.model.toString().trim()
            mDoc.addAuthor("KB CODER")
            mDoc.add(Paragraph(data))
            mDoc.close()
            Toast.makeText(this, "$mFileName.pdf\n is create to \n$mFilePath", Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            STORAGE_CADE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}