package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.ui.USERJASA.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_material_create.*

class MaterialUpdateActivity : AppCompatActivity(), MaterialUpdateContarct.View {

    private var uriImage: Uri? = null
    private var pickImage = 1

    lateinit var presenter: MaterialUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_create)
        presenter = MaterialUpdatePresenter(this)
        presenter.getDetail( Constant.MATERIAL_ID )
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
        supportActionBar!!.title = "Update Material"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        BtnLocationM.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        imvImageMaterial.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
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
                location.isNullOrEmpty() || deskripsi.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateMaterial(Constant.MATERIAL_ID, nameToko.toString(), jenis_material.toString(), alamat.toString(), phone.toString(), harga.toString(),
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

    override fun onResultDetail(responseMaterialDetail: ResponseMaterialDetail) {

        val material = responseMaterialDetail.dataMaterial

        edtNameTokoMaterial.setText( material.nama_toko )
        edtJenismaterial.setText( material.jenis_material )
        edtAlamatMaterial.setText( material.alamat )
        edtphoneMaterial.setText( material.phone )
        edthargamaterial.setText( material.harga )
        edtlocationmaterial.setText( "${material.latitude}, ${material.longitude}" )
        Constant.LATITUDE = material.latitude!!
        Constant.LONGITUDE = material.longitude!!


        GlideHelper.setImage(this, "http://192.168.43.224/api_spesialisJB/public/"+ material.gambar!!, imvImageMaterial)

        edtdeskripsimaterial.setText( material.deskripsi )
    }

    override fun onResultUpdate(responseMaterialUpdate: ResponseMaterialUpdate) {
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