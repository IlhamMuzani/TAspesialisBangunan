package com.ilham.taspesialisbangunan.ui.pelanggan.pengajuan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_pengajuan.*
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class PengajuanActivity : AppCompatActivity(), PengajuanContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var dataproduk: DataProduk
    lateinit var presenter: PengajuanPresenter
    lateinit var prefsManager: PrefsManager


    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengajuan)
        presenter = PengajuanPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()){
            edtLocationpengajuan.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
        presenter.getProdukdetailpengajuan(Constant.PRODUK_ID)

    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_nama.text = "Pengajuan"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        spinnercategorypesanan()

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        imvPengajuanmulai.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        edtLocationpengajuan.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        btn_pengajuann.setOnClickListener {
            if (edtalamatjasa.text!!.isEmpty()){
                edtalamatjasa.error = "Kolom Alamat Tidak Boleh Kosong"
                edtalamatjasa.requestFocus()
            } else if(Constant.LATITUDE.isEmpty()) {
                showMessage("Pilih Lokasi")
            } else if (edtphone1.text!!.isEmpty()){
                edtphone1.error = "Kolom Telepon Tidak Boleh Kosong"
                edtphone1.requestFocus()
            } else if (uriImage == null) {
                showMessage("Masukan Foto")
            } else if(edtdeskripsipengajuann.text!!.isEmpty()){
                edtdeskripsipengajuann.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtdeskripsipengajuann.requestFocus()
            } else {
                presenter.insertPengajuan(Constant.PRODUK_ID.toString(), prefsManager.prefsId, FileUtils.getFile(this, uriImage), edtdeskripsipengajuann.text.toString(),
                    "Menunggu", edtalamatjasa.text.toString(),"Produk Jasa", edtphone1.text.toString(),  Constant.LATITUDE, Constant.LONGITUDE, "produkjasa"
                )
            }
        }

        btn_pengajuanjasadanmaterial.setOnClickListener {
            if (Constant.CATEGORIPESANAN_ID == 0){
                showMessage("Pilih Kategori Produk")
            }else if (edtalamatjasa.text!!.isEmpty()){
                edtalamatjasa.error = "Kolom Alamat Tidak Boleh Kosong"
                edtalamatjasa.requestFocus()
            } else if (Constant.LATITUDE.isEmpty()) {
                showMessage("Pilih Lokasi")
            } else if (edtphone1.text!!.isEmpty()){
                edtphone1.error = "Kolom Telepon Tidak Boleh Kosong"
                edtphone1.requestFocus()
            } else if (uriImage == null) {
            showMessage("Masukan Foto")
            }else if (edtdeskripsipengajuann.text!!.isEmpty()){
                edtdeskripsipengajuann.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtdeskripsipengajuann.requestFocus()
            } else {
                presenter.insertPengajuanjasadanmaterial(Constant.PRODUK_ID.toString(), prefsManager.prefsId, FileUtils.getFile(this, uriImage), edtdeskripsipengajuann.text.toString(),
                    "Menunggu",  edtalamatjasa.text.toString(), Constant.CATEGORIPESANAN_NAME, edtphone1.text.toString(), Constant.LATITUDE, Constant.LONGITUDE, "produkjasa"
                )
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when(loading){
            true -> {
                sLoading.setTitleText(message).show()
                btn_pengajuann.visibility = View.GONE
                btn_pengajuanjasadanmaterial.visibility = View.GONE
            }
            false -> {
                sLoading.dismiss()
                btn_pengajuann.visibility = View.VISIBLE
                btn_pengajuanjasadanmaterial.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert) {
        showMessage(responsePengajuanInsert.msg)
        finish()
    }

    override fun onResultProdukdetailpengajuan(responseProdukDetail: ResponseProdukDetail) {
        dataproduk = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + dataproduk.gambar!!, imvImageprodukpengajuan)
        txvNameTokopengajuan.text = dataproduk.user.nama_toko
        txvCategoryypengajuan.text = dataproduk.category
        txvJenispembuatanpengajuan.text = dataproduk.model
        txvhargapengajuan.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataproduk.harga))
        when (dataproduk.category) {
            "Produk Jasa" -> {
                layoutcategoriproduk.visibility = View.GONE
                btn_pengajuanjasadanmaterial.visibility = View.GONE
            }
            "Produk Jasa dan Material" -> {
                btn_pengajuann.visibility = View.GONE
            }
        }

    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvPengajuanmulai.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun spinnercategorypesanan() {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kategori")
        arrayString.add("Produk Jasa")
        arrayString.add("Produk Jasa dan Material")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_categoriproduk.adapter = adapter
        val selection = adapter.getPosition(Constant.CATEGORIPESANAN_NAME)
        edt_categoriproduk.setSelection(selection)
        edt_categoriproduk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.CATEGORIPESANAN_ID = 0
                        Constant.CATEGORIPESANAN_NAME = "Pilih Pesanan"
                    }
                    1 -> {
                        Constant.CATEGORIPESANAN_ID = 1
                        Constant.CATEGORIPESANAN_NAME = "Produk Jasa"
                    }
                    2 -> {
                        Constant.CATEGORIPESANAN_ID = 2
                        Constant.CATEGORIPESANAN_NAME = "Produk Jasa dan Material"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
}
