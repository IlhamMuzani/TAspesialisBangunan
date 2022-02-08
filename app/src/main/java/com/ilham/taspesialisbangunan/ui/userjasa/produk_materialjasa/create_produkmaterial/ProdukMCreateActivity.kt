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
import kotlinx.android.synthetic.main.activity_produk_create.btnSave
import kotlinx.android.synthetic.main.activity_produk_create.progress
import kotlinx.android.synthetic.main.activity_produkm_create.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class ProdukMCreateActivity : AppCompatActivity(), ProdukMCreateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var prefsManager: PrefsManager
    lateinit var presenter: ProdukMCreatePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkm_create)
        presenter = ProdukMCreatePresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocationMat.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun initActivity() {
        tv_bgjasa.text="Create Materials"
    }

    override fun initListener() {
        ivKembalijasa.setOnClickListener {
            onBackPressed()

        }
        edtLocationMat.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        imvImagesMat.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btnSaveMat.setOnClickListener {
            val nameToko = edtNameTokoMat.text
            val jenis_pembuatan = edtJenismaterialMat.text
            val alamat = edtAlamateddresMat.text
            val phone = edtPhoneMat.text
            val harga = edtHargaMat.text
            val location = edtLocationMat.text
            val deskripsi = edtDeskripsiMat.text

            if ( nameToko.isNullOrEmpty() || jenis_pembuatan.isNullOrEmpty() || alamat.isNullOrEmpty() ||  phone.isNullOrEmpty() || harga.isNullOrEmpty() ||
                location.isNullOrEmpty() || deskripsi.isNullOrEmpty() || uriImage == null ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertProdukMaterial(prefsManager.prefsId,nameToko.toString(), jenis_pembuatan.toString(), alamat.toString(), phone.toString(), harga.toString(),
                    Constant.LATITUDE, Constant.LONGITUDE, FileUtils.getFile(this, uriImage), deskripsi.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressMat.visibility = View.VISIBLE
                btnSaveMat.visibility = View.GONE
            }
            false -> {
                progressMat.visibility = View.GONE
                btnSaveMat.visibility = View.VISIBLE
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
            imvImagesMat.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}