package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.create

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
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.ui.USERJASA.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_material_create.*

class MaterialCreateActivity : AppCompatActivity(), MaterialCreateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var prefsManager: PrefsManager
    lateinit var presenter: MaterialCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_create)
        presenter = MaterialCreatePresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtlocationmaterial.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE =""
        Constant.LONGITUDE =""
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK)
            uriImage = data!!.data
        imvImageMaterial.setImageURI(uriImage)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Material Baru"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        BtnLocationM.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        imvImageMaterial.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)){
                GalleryHelper.openGallery(this)
            }
        }

        BTN_savematerial.setOnClickListener {
            val nameToko = edtNameTokoMaterial.text
            val jenis_material = edtJenismaterial.text
            val alamat = edtAlamatMaterial.text
            val phone = edtphoneMaterial.text
            val harga = edthargamaterial.text
            val location = edtlocationmaterial.text
            val deskripsi = edtdeskripsimaterial.text

            if ( nameToko.isNullOrEmpty() || jenis_material.isNullOrEmpty() || alamat.isNullOrEmpty() || phone.isNullOrEmpty() || harga.isNullOrEmpty() ||
                location.isNullOrEmpty() || deskripsi.isNullOrEmpty() || uriImage == null ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertMaterial(prefsManager.prefsId, nameToko.toString(), jenis_material.toString(), alamat.toString(),  phone.toString(),  harga.toString(),
                    Constant.LATITUDE, Constant.LONGITUDE, FileUtils.getFile(this, uriImage), deskripsi.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressmaterial.visibility = View.VISIBLE
                BTN_savematerial.visibility = View.GONE
            }
            false -> {
                progressmaterial.visibility = View.GONE
                BTN_savematerial.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseMaterialUpdate: ResponseMaterialUpdate) {
        showMessage(responseMaterialUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}