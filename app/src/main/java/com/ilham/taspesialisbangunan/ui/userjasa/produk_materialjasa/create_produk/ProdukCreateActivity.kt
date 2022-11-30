package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.alamat.DataAlamat
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.DataBahanproduk
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.DataHarga
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProdukCreateActivity : AppCompatActivity(), ProdukCreateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var prefsManager: PrefsManager
    lateinit var presenter: ProdukCreatePresenter
    lateinit var produk: DataProduk
    lateinit var dataAlamat: DataAlamat
    lateinit var dataHarga: DataHarga
    lateinit var dataBahanproduk: DataBahanproduk
    lateinit var jasa: DataUser

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_create)
        presenter = ProdukCreatePresenter(this)
        prefsManager = PrefsManager(this)
        presenter.getDetailuserjasa(prefsManager.prefsId)

    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocation.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text = "Tambah Jasa"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(
            this,
            com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE
        )
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(
            this,
            com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(
            this,
            com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(
            this,
            com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE
        ).setTitleText("Perhatian!")

        spinnercategory()
        spinnerjenispembuatan()
        spinnerukurancreate()
    }

    override fun initListener() {


        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        BtnLocation.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        btnpilihlokasi.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        imvImages.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btnSave.setOnClickListener {
            if (Constant.CATEGORY_ID == 0) {
                showMessage("Pilih Kategori Produk")
            } else if (Constant.JENISPEMBUATAN_ID == 0) {
                showMessage("Pilih Jenis Pembuatan")
            } else if (edtmodel.text!!.isEmpty()) {
                edtmodel.error = "Kolom Model Tidak Boleh Kosong"
                edtmodel.requestFocus()
            } else if (Constant.UKURAN_ID == 0) {
                showMessage("Pilih Ukuran")
            } else if (Constant.HARGA_ID == 0) {
                showMessage("Pilih Harga")
            } else if (uriImage == null) {
                showMessage("Masukan Foto")
            } else if (edtDeskripsi.text!!.isEmpty()) {
                edtDeskripsi.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtDeskripsi.requestFocus()
            } else if (Constant.KECAMATAN_ID == 0) {
                showMessage("Pilih Kecamatan")
                //pilih kelurahan belum
//            } else if (Constant.KELURAHANPRODUK_ID == 0) {
//                showMessage("Pilih Kelurahan")
            } else if (edtdetailalamat.text!!.isEmpty()) {
                edtdetailalamat.error = "Maksukan Rt Rw"
                edtdetailalamat.requestFocus()
            } else if (edtLocation.text!!.isEmpty()) {
                edtLocation.error = "Maksukan Rt Rw"
                edtLocation.requestFocus()
            } else if (edtPhone.text!!.isEmpty()) {
                edtPhone.error = "Kolom Telpone Tidak Boleh Kosong"
                edtPhone.requestFocus()
            } else {

                if (Constant.HARGA_ID == 1) {
                    Constant.HARGA_NAME = edtHargalainya.text.toString()
                }

                presenter.insertProduk(
                    prefsManager.prefsId,
                    Constant.CATEGORY_NAME,
                    Constant.JENISPEMBUATAN_NAME,
                    edtmodel.text.toString(),
                    Constant.UKURAN_NAME,
                    Constant.KECAMATAN_NAME,
                    Constant.KELURAHANPRODUK_NAME,
                    edtdetailalamat.text.toString(),
                    edtPhone.text.toString(),
                    Constant.HARGA_NAME,
                    Constant.LATITUDE,
                    Constant.LONGITUDE,
                    FileUtils.getFile(this, uriImage),
                    edtDeskripsi.text.toString()
                )
            }
        }

        btnSavejasasaja.setOnClickListener {
            if (Constant.CATEGORY_ID == 0) {
                showMessage("Pilih Kategori Produk")
            } else if (Constant.JENISPEMBUATAN_ID == 0) {
                showMessage("Pilih Jenis Pembuatan")
            } else if (edtmodel.text!!.isEmpty()) {
                edtmodel.error = "Kolom Model Tidak Boleh Kosong"
                edtmodel.requestFocus()
            } else if (Constant.HARGA_ID == 0) {
                showMessage("Pilih Harga")
            } else if (uriImage == null) {
                showMessage("Masukan Foto")
            } else if (edtDeskripsi.text!!.isEmpty()) {
                edtDeskripsi.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtDeskripsi.requestFocus()
            } else if (Constant.KECAMATAN_ID == 0) {
                showMessage("Pilih Kecamatan")
//                kelurahan belum
//            } else if (Constant.KELURAHANPRODUK_ID == 0) {
//                showMessage("Pilih Kelurahan")
            } else if (edtdetailalamat.text!!.isEmpty()) {
                edtdetailalamat.error = "Maksukan Rt Rw"
                edtdetailalamat.requestFocus()
            } else if (edtLocation.text!!.isEmpty()) {
                edtLocation.error = "Maksukan Rt Rw"
                edtLocation.requestFocus()
            } else if (edtPhone.text!!.isEmpty()) {
                edtPhone.error = "Kolom Telpone Tidak Boleh Kosong"
                edtPhone.requestFocus()
            } else {

            if (Constant.HARGA_ID == 1) {
                Constant.HARGA_NAME = edtHargalainya.text.toString()
            }
                presenter.insertProdukjasasaja(
                    prefsManager.prefsId,
                    Constant.CATEGORY_NAME,
                    Constant.JENISPEMBUATAN_NAME,
                    edtmodel.text.toString(),
                    Constant.KECAMATAN_NAME,
                    Constant.KELURAHANPRODUK_NAME,
                    edtdetailalamat.text.toString(),
                    edtPhone.text.toString(),
                    Constant.HARGA_NAME,
                    Constant.LATITUDE,
                    Constant.LONGITUDE,
                    FileUtils.getFile(this, uriImage),
                    edtDeskripsi.text.toString()
                )
            }
        }

    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage(responseProdukUpdate.msg)
        finish()
    }

    override fun onResultprodukjasasaja(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage(responseProdukUpdate.msg)
        finish()
    }

    override fun onResultDetailuserjasa(responseUserdetail: ResponseUserdetail) {
        jasa = responseUserdetail.user

        edtdetailalamat.setText(jasa.alamat)
        spinneralamatkecamatan()
        edtLocation.setText("${jasa.latitude}, ${jasa.longitude}")
        edtPhone.setText(jasa.phone)

        if (Constant.CATEGORY_NAME == "Produk Jasa"){
            btnSavejasasaja.visibility = View.VISIBLE
            btnSave.visibility = View.GONE
        }else{
            btnSavejasasaja.visibility = View.GONE
            btnSave.visibility = View.VISIBLE

        }
    }

    override fun onResultSearchBahanproduk(responseBahanprodukList: ResponseBahanprodukList) {
        spinnerjenispembuatan(responseBahanprodukList)
    }

    override fun onResultSearchKecamatan(responseALamatList: ResponseALamatList) {
        spinnerkelurahanproduk(responseALamatList)
    }

    override fun onResultSearchukuranjasadanmaterial(responseHargalist: ResponseHargaList) {
        spinnerHargaproduk(responseHargalist)
    }

    override fun onResultSearchukuranjasasaja(responseHargalist: ResponseHargaList) {
        spinnerHargaproduk(responseHargalist)
    }

    override fun onResultSearchHargaprodukjasasaja(responseHargalist: ResponseHargaList) {
        spinnerHargaproduk(responseHargalist)
    }

    override fun onResultSearchjasadanmaterial(responseHargalist: ResponseHargaList) {
        spinnerHargaproduk(responseHargalist)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvImages.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun spinnercategory() {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kategori")
        arrayString.add("Produk Jasa")
        arrayString.add("Produk Jasa dan Material")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtcategory.adapter = adapter
        val selection = adapter.getPosition(Constant.CATEGORY_NAME)
        edtcategory.setSelection(selection)
        edtcategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.CATEGORY_ID = 0
                        Constant.CATEGORY_NAME = "Pilih Kategori"
                        hargakanopi.visibility = View.GONE
                        hargapagar.visibility = View.GONE
                        hargatralis.visibility = View.GONE
                        hargatangga.visibility = View.GONE
                        hargahalaman.visibility = View.GONE
                        hargakanopim.visibility = View.GONE
                        hargapagarm.visibility = View.GONE
                        hargatralism.visibility = View.GONE
                        hargatanggam.visibility = View.GONE
                        hargahalamanm.visibility = View.GONE
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        layoutukuran.visibility = View.VISIBLE
                    }
                    1 -> {
                        Constant.CATEGORY_ID = 1
                        Constant.CATEGORY_NAME = "Produk Jasa"
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        spinnerjenispembuatan()
                        hargakanopi.visibility = View.VISIBLE
                        hargapagar.visibility = View.VISIBLE
                        hargatralis.visibility = View.VISIBLE
                        hargatangga.visibility = View.VISIBLE
                        hargahalaman.visibility = View.VISIBLE
                        hargakanopim.visibility = View.GONE
                        hargapagarm.visibility = View.GONE
                        hargatralism.visibility = View.GONE
                        hargatanggam.visibility = View.GONE
                        hargahalamanm.visibility = View.GONE
                        btnSave.visibility = View.GONE
                        btnSavejasasaja.visibility = View.VISIBLE
                        layoutukuran.visibility = View.GONE
                    }
                    2 -> {
                        Constant.CATEGORY_ID = 2
                        Constant.CATEGORY_NAME = "Produk Jasa dan Material"
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        spinnerjenispembuatan()
                        hargakanopi.visibility = View.GONE
                        hargapagar.visibility = View.GONE
                        hargatralis.visibility = View.GONE
                        hargatangga.visibility = View.GONE
                        hargahalaman.visibility = View.GONE
                        hargakanopim.visibility = View.VISIBLE
                        hargapagarm.visibility = View.VISIBLE
                        hargatralism.visibility = View.VISIBLE
                        hargatanggam.visibility = View.VISIBLE
                        hargahalamanm.visibility = View.VISIBLE
                        btnSavejasasaja.visibility = View.GONE
                        btnSave.visibility = View.VISIBLE
                        layoutukuran.visibility = View.VISIBLE

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerjenispembuatan(responseBahanprodukList: ResponseBahanprodukList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Jenis")
//        arrayString.add("Pilih Jenis Lainya")
        for (produk in responseBahanprodukList.bahanproduk) {
            arrayString.add(produk.jenis!!)
        }

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtJenispembuatan.adapter = adapter
        val selection = adapter.getPosition(Constant.JENISPEMBUATAN_NAME)
        edtJenispembuatan.setSelection(selection)
        edtJenispembuatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.JENISPEMBUATAN_ID = 0
                        Constant.JENISPEMBUATAN_NAME = "Pilih Jenis"
                    }

                    else -> {
                        val namaJenis = responseBahanprodukList.bahanproduk[position - 2].jenis
                        Constant.JENISPEMBUATAN_ID = position
                        Constant.JENISPEMBUATAN_NAME = namaJenis.toString()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun spinneralamatkecamatan() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kecamatan")
        arrayString.add("Kecamatan Margadana")
        arrayString.add("Kecamatan Tegal Barat")
        arrayString.add("Kecamatan Tegal Selatan")
        arrayString.add("Kecamatan Tegal Timur ")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtAlamateddres.adapter = adapter
        val selection = adapter.getPosition(jasa.kecamatan)
        edtAlamateddres.setSelection(selection)
        edtAlamateddres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.KECAMATAN_ID = 0
                        Constant.KECAMATAN_NAME = "Pilih Kecamatan"
                        presenter.searchkecamatan(Constant.KECAMATAN_NAME)
                    }
                    1 -> {
                        Constant.KECAMATAN_ID = 1
                        Constant.KECAMATAN_NAME = "Kecamatan Margadana"
                        presenter.searchkecamatan(Constant.KECAMATAN_NAME)
                    }
                    2 -> {
                        Constant.KECAMATAN_ID = 2
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Barat"
                        presenter.searchkecamatan(Constant.KECAMATAN_NAME)
                    }
                    3 -> {
                        Constant.KECAMATAN_ID = 3
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Selatan"
                        presenter.searchkecamatan(Constant.KECAMATAN_NAME)
                    }
                    4 -> {
                        Constant.KECAMATAN_ID = 4
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Timur"
                        presenter.searchkecamatan(Constant.KECAMATAN_NAME)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerkelurahanproduk(responseALamatList: ResponseALamatList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kelurahan")
        for (kelurahan in responseALamatList.dataAlamat) {
            arrayString.add(kelurahan.kelurahan!!)
        }

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtKelurahanedress.adapter = adapter
        val selection = adapter.getPosition(jasa.kelurahan)
        edtKelurahanedress.setSelection(selection)
        edtKelurahanedress.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.KELURAHANPRODUK_NAME = "Pilih Kelurahan"
                    Constant.LATITUDE = ""
                    Constant.LONGITUDE = ""
                } else {
                    val namaKelurahan = responseALamatList.dataAlamat[position - 1].kelurahan
                    Constant.KELURAHANPRODUK_NAME = namaKelurahan.toString()
                    Constant.LATITUDE =
                        responseALamatList.dataAlamat[position - 1].latitude.toString()
                    Constant.LONGITUDE =
                        responseALamatList.dataAlamat[position - 1].longitude.toString()
                }
                edtLocation.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun spinnerjenispembuatan() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Jenis Pembuatan")
        arrayString.add("Pembuatan Kanopi")
        arrayString.add("Pembuatan Pagar")
        arrayString.add("Pembuatan Tralis")
        arrayString.add("Pembuatan Tangga")
        arrayString.add("Pembuatan Halaman")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtJenispembuatan.adapter = adapter
        val selection = adapter.getPosition(Constant.JENISPEMBUATAN_NAME)
        edtJenispembuatan.setSelection(selection)
        edtJenispembuatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.JENISPEMBUATAN_ID = 0
                        Constant.JENISPEMBUATAN_NAME = "Pilih Jenis Pembuatan"
                        hargakanopi.visibility = View.GONE
                        hargapagar.visibility = View.GONE
                        hargatralis.visibility = View.GONE
                        hargatangga.visibility = View.GONE
                        hargahalaman.visibility = View.GONE
                        hargakanopim.visibility = View.GONE
                        hargapagarm.visibility = View.GONE
                        hargatralism.visibility = View.GONE
                        hargatanggam.visibility = View.GONE
                        hargahalamanm.visibility = View.GONE
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                    }
                    1 -> {
                        Constant.JENISPEMBUATAN_ID = 1
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Kanopi"
                        if (Constant.CATEGORY_ID == 1){
                            hargakanopi.visibility = View.VISIBLE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargaprodukjasasaja("Pembuatan Kanopi")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                        } else {
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.VISIBLE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargajasadanmaterial("Pembuatan Kanopi")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                            if (Constant.UKURAN_ID == 1){
                                presenter.searchukuranjasadanmaterialkanopi("1 m")
                            } else if (Constant.UKURAN_ID == 2){
                                presenter.searchukuranjasadanmaterialkanopi("2 m")
                            } else if (Constant.UKURAN_ID == 3){
                                presenter.searchukuranjasadanmaterialkanopi("3 m")
                            } else if (Constant.UKURAN_ID == 4){
                                presenter.searchukuranjasadanmaterialkanopi("4 m")
                            } else if (Constant.UKURAN_ID == 5){
                                presenter.searchukuranjasadanmaterialkanopi("5 m")
                            } else if (Constant.UKURAN_ID == 6){
                                presenter.searchukuranjasadanmaterialkanopi("6 m")
                            } else if (Constant.UKURAN_ID == 7){
                                presenter.searchukuranjasadanmaterialkanopi("7 m")
                            } else if (Constant.UKURAN_ID == 8){
                                presenter.searchukuranjasadanmaterialkanopi("8 m")
                            } else if (Constant.UKURAN_ID == 9){
                                presenter.searchukuranjasadanmaterialkanopi("9 m")
                            } else if (Constant.UKURAN_ID == 10){
                                presenter.searchukuranjasadanmaterialkanopi("10 m")
                            }else{
                            }
                        }

                    }
                    2 -> {
                        Constant.JENISPEMBUATAN_ID = 2
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Pagar"
                        presenter.searchhargaprodukjasasaja("Pembuatan Pagar")
                        if (Constant.CATEGORY_ID == 1){
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.VISIBLE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargaprodukjasasaja("Pembuatan Pagar")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        } else {
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.VISIBLE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargajasadanmaterialtepagar("Pembuatan Pagar")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                            if (Constant.UKURAN_ID == 1){
                                presenter.searchukuranjasadanmaterialpagar("1 m")
                            } else if (Constant.UKURAN_ID == 2){
                                presenter.searchukuranjasadanmaterialpagar("2 m")
                            } else if (Constant.UKURAN_ID == 3){
                                presenter.searchukuranjasadanmaterialpagar("3 m")
                            } else if (Constant.UKURAN_ID == 4){
                                presenter.searchukuranjasadanmaterialpagar("4 m")
                            } else if (Constant.UKURAN_ID == 5){
                                presenter.searchukuranjasadanmaterialpagar("5 m")
                            } else if (Constant.UKURAN_ID == 6){
                                presenter.searchukuranjasadanmaterialpagar("6 m")
                            } else if (Constant.UKURAN_ID == 7){
                                presenter.searchukuranjasadanmaterialpagar("7 m")
                            } else if (Constant.UKURAN_ID == 8){
                                presenter.searchukuranjasadanmaterialpagar("8 m")
                            } else if (Constant.UKURAN_ID == 9){
                                presenter.searchukuranjasadanmaterialpagar("9 m")
                            } else if (Constant.UKURAN_ID == 10){
                                presenter.searchukuranjasadanmaterialpagar("10 m")
                            }else{

                            }
                        }
                    }
                    3 -> {
                        Constant.JENISPEMBUATAN_ID = 3
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tralis"
                        presenter.searchhargaprodukjasasaja("Pembuatan Tralis")
                        if (Constant.CATEGORY_ID == 1){
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.VISIBLE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargaprodukjasasaja("Pembuatan Tralis")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        } else {
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.VISIBLE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargajasadanmaterialtralis("Pembuatan Tralis")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                            if (Constant.UKURAN_ID == 1){
                                presenter.searchukuranjasadanmaterialtralis("1 m")
                            } else if (Constant.UKURAN_ID == 2){
                                presenter.searchukuranjasadanmaterialtralis("2 m")
                            } else if (Constant.UKURAN_ID == 3){
                                presenter.searchukuranjasadanmaterialtralis("3 m")
                            } else if (Constant.UKURAN_ID == 4){
                                presenter.searchukuranjasadanmaterialtralis("4 m")
                            } else if (Constant.UKURAN_ID == 5){
                                presenter.searchukuranjasadanmaterialtralis("5 m")
                            } else if (Constant.UKURAN_ID == 6){
                                presenter.searchukuranjasadanmaterialtralis("6 m")
                            } else if (Constant.UKURAN_ID == 7){
                                presenter.searchukuranjasadanmaterialtralis("7 m")
                            } else if (Constant.UKURAN_ID == 8){
                                presenter.searchukuranjasadanmaterialtralis("8 m")
                            } else if (Constant.UKURAN_ID == 9){
                                presenter.searchukuranjasadanmaterialtralis("9 m")
                            } else if (Constant.UKURAN_ID == 10){
                                presenter.searchukuranjasadanmaterialtralis("10 m")
                            }else{
                            }
                        }

                    }
                    4 -> {
                        Constant.JENISPEMBUATAN_ID = 4
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tangga"
                        presenter.searchhargaprodukjasasaja("Pembuatan Tangga")
                        if (Constant.CATEGORY_ID == 1){
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.VISIBLE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargaprodukjasasaja("Pembuatan Tangga")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        } else {
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.VISIBLE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargajasadanmaterialtangga("Pembuatan Tangga")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                            if (Constant.UKURAN_ID == 1){
                                presenter.searchukuranjasadanmaterialtangga("1 m")
                            } else if (Constant.UKURAN_ID == 2){
                                presenter.searchukuranjasadanmaterialtangga("2 m")
                            } else if (Constant.UKURAN_ID == 3){
                                presenter.searchukuranjasadanmaterialtangga("3 m")
                            } else if (Constant.UKURAN_ID == 4){
                                presenter.searchukuranjasadanmaterialtangga("4 m")
                            } else if (Constant.UKURAN_ID == 5){
                                presenter.searchukuranjasadanmaterialtangga("5 m")
                            } else if (Constant.UKURAN_ID == 6){
                                presenter.searchukuranjasadanmaterialtangga("6 m")
                            } else if (Constant.UKURAN_ID == 7){
                                presenter.searchukuranjasadanmaterialtangga("7 m")
                            } else if (Constant.UKURAN_ID == 8){
                                presenter.searchukuranjasadanmaterialtangga("8 m")
                            } else if (Constant.UKURAN_ID == 9){
                                presenter.searchukuranjasadanmaterialtangga("9 m")
                            } else if (Constant.UKURAN_ID == 10){
                                presenter.searchukuranjasadanmaterialtangga("10 m")
                            }else{
                            }
                        }
                    }
                    5 -> {
                        Constant.JENISPEMBUATAN_ID = 5
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Halaman"
                        presenter.searchhargaprodukjasasaja("Pembuatan Halaman")
                        if (Constant.CATEGORY_ID == 1){
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.VISIBLE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.GONE
                            presenter.searchhargaprodukjasasaja("Pembuatan Halaman")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        } else {
                            hargakanopi.visibility = View.GONE
                            hargapagar.visibility = View.GONE
                            hargatralis.visibility = View.GONE
                            hargatangga.visibility = View.GONE
                            hargahalaman.visibility = View.GONE
                            hargakanopim.visibility = View.GONE
                            hargapagarm.visibility = View.GONE
                            hargatralism.visibility = View.GONE
                            hargatanggam.visibility = View.GONE
                            hargahalamanm.visibility = View.VISIBLE
                            presenter.searchhargajasadanmaterialtehalaman("Pembuatan Halaman")
                            presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())

                            if (Constant.UKURAN_ID == 1){
                                presenter.searchukuranjasadanmaterialhalaman("1 m")
                            } else if (Constant.UKURAN_ID == 2){
                                presenter.searchukuranjasadanmaterialhalaman("2 m")
                            } else if (Constant.UKURAN_ID == 3){
                                presenter.searchukuranjasadanmaterialhalaman("3 m")
                            } else if (Constant.UKURAN_ID == 4){
                                presenter.searchukuranjasadanmaterialhalaman("4 m")
                            } else if (Constant.UKURAN_ID == 5){
                                presenter.searchukuranjasadanmaterialhalaman("5 m")
                            } else if (Constant.UKURAN_ID == 6){
                                presenter.searchukuranjasadanmaterialhalaman("6 m")
                            } else if (Constant.UKURAN_ID == 7){
                                presenter.searchukuranjasadanmaterialhalaman("7 m")
                            } else if (Constant.UKURAN_ID == 8){
                                presenter.searchukuranjasadanmaterialhalaman("8 m")
                            } else if (Constant.UKURAN_ID == 9){
                                presenter.searchukuranjasadanmaterialhalaman("9 m")
                            } else if (Constant.UKURAN_ID == 10){
                                presenter.searchukuranjasadanmaterialhalaman("10 m")
                            }else{
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerHargaproduk(responseHargaList: ResponseHargaList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Harga")
        arrayString.add("Pilih Harga Lainya")
        for (harga in responseHargaList.dataHarga){arrayString.add(
            NumberFormat.getCurrencyInstance(
                Locale("in", "ID")
            ).format(Integer.valueOf(harga.harga!!)))}

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtHargaproduk.adapter = adapter
        val selection = adapter.getPosition(Constant.HARGA_NAME)
        edtHargaproduk.setSelection(selection)
        edtHargaproduk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        layouthargalainya.visibility = View.GONE
                        Constant.HARGA_ID = 0
                        Constant.HARGA_NAME = "Pilih Harga"
                    }
                    1 -> {
                        layouthargalainya.visibility = View.VISIBLE
                        Constant.HARGA_ID = 1
                        Constant.HARGA_NAME = edtHargalainya.text.toString()
                    }
                    else -> {
                        val namaharga = responseHargaList.dataHarga[position - 2].harga
                        Constant.HARGA_ID = position
                        Constant.HARGA_NAME = namaharga.toString()
                        layouthargalainya.visibility = View.GONE
                    }

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun spinnerukurancreate() {

        val arrayString = java.util.ArrayList<String>()

            arrayString.add("Pilih Ukuran")
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


        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtUkuran.adapter = adapter
        val selection = adapter.getPosition(Constant.UKURAN_NAME)
        edtUkuran.setSelection(selection)
        edtUkuran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.UKURAN_ID = 0
                        Constant.UKURAN_NAME = "Pilih Ukuran"
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                    }
                    1 -> {
                        presenter.getDetailuserjasa(Constant.USERJASA_ID.toString())
                        Constant.UKURAN_ID = 1
                        Constant.UKURAN_NAME = "1 m"
                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasaja("1 m")
                        } else {

                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 1 m")
                            } else {

                            }
                        }
                    }
                    2 -> {
                        Constant.UKURAN_ID = 2
                        Constant.UKURAN_NAME = "2 m"
                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasaja("2 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman("  m")
                            } else {

                            }
                        }
                    }
                    3 -> {
                        Constant.UKURAN_ID = 3
                        Constant.UKURAN_NAME = "3 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("3 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 3 m")
                            } else {
                            }
                        }
                    }
                    4 -> {
                        Constant.UKURAN_ID = 4
                        Constant.UKURAN_NAME = "4 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("4 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 4 m")
                            } else {
                            }
                        }
                    }
                    5 -> {
                        Constant.UKURAN_ID = 5
                        Constant.UKURAN_NAME = "5 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("5 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 5 m")
                            } else {
                            }
                        }
                    }
                    6 -> {
                        Constant.UKURAN_ID = 6
                        Constant.UKURAN_NAME = "6 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("6 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 6 m")
                            } else {
                            }
                        }
                    }
                    7 -> {
                        Constant.UKURAN_ID = 7
                        Constant.UKURAN_NAME = "7 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("7 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 7 m")
                            } else {
                            }
                        }
                    }
                    8 -> {
                        Constant.UKURAN_ID = 8
                        Constant.UKURAN_NAME = "8 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("8 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 8 m")
                            } else {
                            }
                        }
                    }
                    9 -> {
                        Constant.UKURAN_ID = 9
                        Constant.UKURAN_NAME = "9 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("9 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 9 m")
                            } else {
                            }
                        }
                    }
                    else -> {
                        Constant.UKURAN_ID = 10
                        Constant.UKURAN_NAME = "10 m"

                        if (Constant.CATEGORY_ID == 1){
                            presenter.searchukuranjasasaja("10 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopi(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagar(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralis(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtangga(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalaman(" 10 m")
                            } else {
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
}