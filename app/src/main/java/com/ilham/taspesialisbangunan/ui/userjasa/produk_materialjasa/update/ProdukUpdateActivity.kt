package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

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
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.harga.DataHarga
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_produk_create.*
//import kotlinx.android.synthetic.main.activity_produk_create.edtHarga
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProdukUpdateActivity : AppCompatActivity(), ProdukUpdateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ProdukUpdatePresenter
    lateinit var produk: DataProduk


    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_create)
        presenter = ProdukUpdatePresenter(this)
        presenter.getDetail( Constant.PRODUK_ID )
    }

    override fun onStart() {
        super.onStart()
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
       tv_bgjasa.text="Ubah Produk"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }
        BtnLocation.setOnClickListener {
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
//            } else if (Constant.HARGA_ID == 0) {
//                showMessage("Pilih Harga")
            } else if (edtDeskripsi.text!!.isEmpty()) {
                edtDeskripsi.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtDeskripsi.requestFocus()
            } else if (Constant.KECAMATAN_ID == 0) {
                showMessage("Pilih Kecamatan")
                //pilih kelurahan belum
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

                if (Constant.HARGA_ID == 0|| Constant.HARGA_ID == 1) {
                    Constant.HARGA_NAME = edtHargalainya.text.toString()
                }

                presenter.updateProduk(Constant.PRODUK_ID, Constant.CATEGORY_NAME, Constant.JENISPEMBUATAN_NAME, edtmodel.text.toString(), Constant.UKURAN_NAME, Constant.KECAMATAN_NAME, Constant.KELURAHANPRODUK_NAME, edtdetailalamat.text.toString(), edtPhone.text.toString(), Constant.HARGA_NAME,
                    Constant.LATITUDE, Constant.LONGITUDE, FileUtils.getFile(this, uriImage), edtDeskripsi.text.toString())
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
//            } else if (Constant.HARGA_ID == 0) {
//                showMessage("Pilih Harga")
            } else if (edtDeskripsi.text!!.isEmpty()) {
                edtDeskripsi.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtDeskripsi.requestFocus()
            } else if (Constant.KECAMATAN_ID == 0) {
                showMessage("Pilih Kecamatan")
                //pilih kelurahan belum
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

                if (Constant.HARGA_ID == 0|| Constant.HARGA_ID == 1) {
                    Constant.HARGA_NAME = edtHargalainya.text.toString()
                }
                presenter.updatejasasaja(Constant.PRODUK_ID, Constant.CATEGORY_NAME, Constant.JENISPEMBUATAN_NAME, edtmodel.text.toString(), Constant.KECAMATAN_NAME, Constant.KELURAHANPRODUK_NAME, edtdetailalamat.text.toString(), edtPhone.text.toString(), Constant.HARGA_NAME,
                    Constant.LATITUDE, Constant.LONGITUDE, FileUtils.getFile(this, uriImage), edtDeskripsi.text.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }


    override fun onResultDetail(responseProdukDetail: ResponseProdukDetail) {

        produk = responseProdukDetail.dataProduk

        if (Constant.CATEGORY_NAME == "Produk Jasa dan Material"){

            btnSavejasasaja.visibility = View.GONE
            btnSave.visibility = View.VISIBLE
            layoutukuran.visibility = View.VISIBLE

            if (Constant.HARGA_ID == 1){
                layouthargalainya.visibility = View.VISIBLE
            }else{
            }
        }else {
            btnSavejasasaja.visibility = View.VISIBLE
            btnSave.visibility = View.GONE
            layoutukuran.visibility = View.GONE
        }

        spinnerukuranupdate()
        edtPhone.setText( produk.phone )
        edtmodel.setText( produk.model )
        edtLocation.setText( "${produk.latitude}, ${produk.longitude}" )
        Constant.LATITUDE = produk.latitude!!
        Constant.LONGITUDE = produk.longitude!!
        GlideHelper.setImage(this,Constant.IP_IMAGE + produk.gambar!!, imvImages)
        edtDeskripsi.setText( produk.deskripsi )
        edtdetailalamat.setText( produk.alamat )
        edtHargalainya.setText( produk.harga )
        spinnercategoryupdate()
        spinneralamatkecamatanupdate()
        spinnerjenispembuatan()

    }

    override fun onResultUpdate(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage(responseProdukUpdate.msg)
        finish()
    }

    override fun onResultSearchProdukupdate(responseProdukList: ResponseProdukList) {
        spinnerjenispembuatanupdate(responseProdukList)
    }

    override fun onResultSearchkecamatanupdate(responseALamatList: ResponseALamatList) {
        spinnerkelurahanproduk(responseALamatList)
    }

    override fun onResultSearchukuranjasadanmaterial(responseHargalist: ResponseHargaList) {
        spinnerHargaprodukupdate(responseHargalist)

    }

    override fun onResultSearchukuranjasasaja(responseHargalist: ResponseHargaList) {
        spinnerHargaprodukupdate(responseHargalist)
    }

    override fun onResultSearchHargaprodukjasasaja(responseHargalist: ResponseHargaList) {
        spinnerHargaprodukupdate(responseHargalist)
    }

    override fun onResultSearchjasadanmaterial(responseHargalist: ResponseHargaList) {
        spinnerHargaprodukupdate(responseHargalist)
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

    fun spinnercategoryupdate() {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kategori")
        arrayString.add("Produk Jasa")
        arrayString.add("Produk Jasa dan Material")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtcategory.adapter = adapter
        val selection = adapter.getPosition(produk.category)
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
//                        layoutlebar.visibility = View.VISIBLE
//                        layoutbahan.visibility = View.VISIBLE
//                        layoutberat.visibility = View.VISIBLE
                        btnSave.visibility = View.VISIBLE
                        btnSavejasasaja.visibility = View.GONE
                    }
                    1 -> {
                        Constant.CATEGORY_ID = 1
                        Constant.CATEGORY_NAME = "Produk Jasa"
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
//                        layoutlebar.visibility = View.GONE
//                        layoutbahan.visibility = View.GONE
//                        layoutberat.visibility = View.GONE
                        btnSave.visibility = View.GONE
                        btnSavejasasaja.visibility = View.VISIBLE
                        layoutukuran.visibility = View.GONE

                    }
                    2-> {
                        Constant.CATEGORY_ID = 2
                        Constant.CATEGORY_NAME = "Produk Jasa dan Material"
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
//                        btnSavejasasaja.visibility = View.GONE
//                        layoutlebar.visibility = View.VISIBLE
//                        layoutbahan.visibility = View.VISIBLE
//                        layoutberat.visibility = View.VISIBLE
                        btnSave.visibility = View.VISIBLE
                        btnSavejasasaja.visibility = View.GONE
                        layoutukuran.visibility = View.VISIBLE

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerjenispembuatanupdate(responseProdukList: ResponseProdukList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Jenis")
        arrayString.add("Pilih Jenis Lainya")
        for (produk in responseProdukList.dataProduk){arrayString.add(produk.jenis_pembuatan!!)}

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtJenispembuatan.adapter = adapter
        val selection = adapter.getPosition(produk.jenis_pembuatan)
        edtJenispembuatan.setSelection(selection)
        edtJenispembuatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
//                        layoutjenislainya.visibility = View.GONE
                        Constant.JENISPEMBUATAN_ID = 0
                        Constant.JENISPEMBUATAN_NAME = ""
                    }
//                    1 -> {
//                        layoutjenislainya.visibility = View.VISIBLE
//                        Constant.JENISPEMBUATAN_ID = 1
//                        Constant.JENISPEMBUATAN_NAME = edtJenispembuatanlainya.text.toString()
//                    }
                    else -> {
                        val namaJenis = responseProdukList.dataProduk[position - 2].jenis_pembuatan
                        Constant.JENISPEMBUATAN_ID = position
                        Constant.JENISPEMBUATAN_NAME = namaJenis.toString()
//                        layoutjenislainya.visibility = View.GONE
                    }
                }

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
        val selection = adapter.getPosition(produk.jenis_pembuatan)
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
                            presenter.searchhargaprodukjasasajaupdate("Pembuatan Kanopi")
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
                            presenter.searchhargajasadanmaterialupdatekanopi("Pembuatan Kanopi")
                        }
                    }
                    2 -> {
                        Constant.JENISPEMBUATAN_ID = 2
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Pagar"
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
                            presenter.searchhargaprodukjasasajaupdate("Pembuatan Pagar")
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
                            presenter.searchhargajasadanmaterialupdatepagar("Pembuatan Pagar")
                        }
                    }
                    3 -> {
                        Constant.JENISPEMBUATAN_ID = 3
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tralis"
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
                            presenter.searchhargaprodukjasasajaupdate("Pembuatan Tralis")
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
                            presenter.searchhargajasadanmaterialupdatetralis("Pembuatan Tralis")
                        }
                    }
                    4 -> {
                        Constant.JENISPEMBUATAN_ID = 4
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tangga"
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
                            presenter.searchhargaprodukjasasajaupdate("Pembuatan Tangga")
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
                            presenter.searchhargajasadanmaterialupdatetangga("Pembuatan Tangga")
                        }
                    }
                    5 -> {
                        Constant.JENISPEMBUATAN_ID = 5
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Halaman"
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
                            presenter.searchhargaprodukjasasajaupdate("Pembuatan Halaman")
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
                            presenter.searchhargajasadanmaterialupdatehalaman("Pembuatan Halaman")
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun spinneralamatkecamatanupdate() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kecamatan")
        arrayString.add("Kecamatan Margadana")
        arrayString.add("Kecamatan Tegal Barat")
        arrayString.add("Kecamatan Tegal Selatan")
        arrayString.add("Kecamatan Tegal Timur")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtAlamateddres.adapter = adapter
        val selection = adapter.getPosition(produk.kecamatan)
        edtAlamateddres.setSelection(selection)
        edtAlamateddres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.KECAMATAN_ID = 0
                        Constant.KECAMATAN_NAME = "Pilih Kecamatan"
                    }
                    1 -> {
                        Constant.KECAMATAN_ID = 1
                        Constant.KECAMATAN_NAME = "Kecamatan Margadana"
                        presenter.searchkecamatanprodukupdate(Constant.KECAMATAN_NAME)
                    }
                    2 -> {
                        Constant.KECAMATAN_ID = 2
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Barat"
                        presenter.searchkecamatanprodukupdate(Constant.KECAMATAN_NAME)

                    }
                    3 -> {
                        Constant.KECAMATAN_ID = 3
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Selatan"
                        presenter.searchkecamatanprodukupdate(Constant.KECAMATAN_NAME)
                    }
                    4 -> {
                        Constant.KECAMATAN_ID = 4
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Timur"
                        presenter.searchkecamatanprodukupdate(Constant.KECAMATAN_NAME)

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
        for (kelurahan in responseALamatList.dataAlamat){arrayString.add(kelurahan.kelurahan!!)}

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtKelurahanedress.adapter = adapter
        val selection = adapter.getPosition(produk.kelurahan)
        edtKelurahanedress.setSelection(selection)
        edtKelurahanedress.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.KELURAHANPRODUK_NAME = "Pilih Kelurahan"
                } else {
                    val namaKelurahan = responseALamatList.dataAlamat[position - 1].kelurahan
                    Constant.KELURAHANPRODUK_NAME = namaKelurahan.toString()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerukuranupdate() {

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
        val selection = adapter.getPosition(produk.ukuran)
        edtUkuran.setSelection(selection)
        edtUkuran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.UKURAN_ID = 0
                        Constant.UKURAN_NAME = "Pilih Ukuran"
                        presenter.getDetail(Constant.USERJASA_ID)

                    }
                    1 -> {
                        Constant.UKURAN_ID = 1
                        Constant.UKURAN_NAME = "1 m"
                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("1 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 1 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 1 m")
                            } else {

                            }
                        }
                    }
                    2 -> {
                        Constant.UKURAN_ID = 2
                        Constant.UKURAN_NAME = "2 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("2 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 2 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 2 m")
                            } else {

                            }
                        }
                    }
                    3 -> {
                        Constant.UKURAN_ID = 3
                        Constant.UKURAN_NAME = "3 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("3 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 3 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 3 m")
                            } else {

                            }
                        }
                    }
                    4 -> {
                        Constant.UKURAN_ID = 4
                        Constant.UKURAN_NAME = "4 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("4 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 4 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 4 m")
                            } else {

                            }
                        }
                    }
                    5 -> {
                        Constant.UKURAN_ID = 5
                        Constant.UKURAN_NAME = "5 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("5 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 5 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 5 m")
                            } else {

                            }
                        }
                    }
                    6 -> {
                        Constant.UKURAN_ID = 6
                        Constant.UKURAN_NAME = "6 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("6 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 6 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 6 m")
                            } else {

                            }
                        }
                    }
                    7 -> {
                        Constant.UKURAN_ID = 7
                        Constant.UKURAN_NAME = "7 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("7 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 7 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 7 m")
                            } else {

                            }
                        }
                    }
                    8 -> {
                        Constant.UKURAN_ID = 8
                        Constant.UKURAN_NAME = "8 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("8 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 8 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 8 m")
                            } else {

                            }
                        }
                    }
                    9 -> {
                        Constant.UKURAN_ID = 9
                        Constant.UKURAN_NAME = "9 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("9 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 9 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 9 m")
                            } else {

                            }
                        }
                    }

                    else -> {
                        Constant.UKURAN_ID = 10
                        Constant.UKURAN_NAME = "10 m"

                        if (Constant.CATEGORY_ID == 1) {
                            presenter.searchukuranjasasajaupdate("10 m")
                        } else {
                            if (Constant.JENISPEMBUATAN_ID == 1) {
                                presenter.searchukuranjasadanmaterialkanopiupdate(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 2) {
                                presenter.searchukuranjasadanmaterialpagarupdate(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 3) {
                                presenter.searchukuranjasadanmaterialtralisupdate(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 4) {
                                presenter.searchukuranjasadanmaterialtanggaupdate(" 10 m")
                            } else if (Constant.JENISPEMBUATAN_ID == 5) {
                                presenter.searchukuranjasadanmaterialhalamanupdate(" 10 m")
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

    fun spinnerHargaprodukupdate(responseHargaList: ResponseHargaList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Harga")
        arrayString.add("Pilih Harga Lainnya")
        for (harga in responseHargaList.dataHarga){arrayString.add(
            NumberFormat.getCurrencyInstance(
                Locale("in", "ID")
            ).format(Integer.valueOf(harga.harga!!)))}



        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtHargaproduk.adapter = adapter
        val selection = adapter.getPosition(produk.harga)
        edtHargaproduk.setSelection(selection)
        edtHargaproduk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
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


}