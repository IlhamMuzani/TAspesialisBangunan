package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_produk_create.*

class ProdukCreateActivity : AppCompatActivity(), ProdukCreateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var prefsManager: PrefsManager
    lateinit var presenter: ProdukCreatePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_create)
        presenter = ProdukCreatePresenter(this)
        prefsManager = PrefsManager(this)
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

    override fun initActivity() {
        supportActionBar!!.title = "Produk Baru"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        BtnLocation.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        imvImages.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btnSave.setOnClickListener {
            val nameToko = edtNameToko.text
            val jenis_pembuatan = edtJenispembuatan.text
            val alamat = edtAlamateddres.text
            val phone = edtPhone.text
            val harga = edtHarga.text
            val location = edtLocation.text
            val deskripsi = edtDeskripsi.text

            if ( nameToko.isNullOrEmpty() || jenis_pembuatan.isNullOrEmpty() || alamat.isNullOrEmpty() ||  phone.isNullOrEmpty() || harga.isNullOrEmpty() ||
                location.isNullOrEmpty() || deskripsi.isNullOrEmpty() || uriImage == null ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertProduk(prefsManager.prefsId,nameToko.toString(), jenis_pembuatan.toString(), alamat.toString(), phone.toString(), harga.toString(),
                    Constant.LATITUDE, Constant.LONGITUDE, FileUtils.getFile(this, uriImage), deskripsi.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progress.visibility = View.VISIBLE
                btnSave.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
                btnSave.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage(responseProdukUpdate.msg)
        finish()
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
}