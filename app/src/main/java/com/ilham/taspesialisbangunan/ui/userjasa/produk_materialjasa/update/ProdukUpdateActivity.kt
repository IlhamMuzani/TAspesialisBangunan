package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_produk_create.*

class ProdukUpdateActivity : AppCompatActivity(), ProdukUpdateContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1

    lateinit var presenter: ProdukUpdatePresenter


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


    override fun initActivity() {
        supportActionBar!!.title = "Edit Produk Jasa"
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

            if ( nameToko.isNullOrEmpty() || jenis_pembuatan.isNullOrEmpty() || alamat.isNullOrEmpty() || phone.isNullOrEmpty() || harga.isNullOrEmpty() ||
                location.isNullOrEmpty() || deskripsi.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateProduk(Constant.PRODUK_ID, nameToko.toString(), jenis_pembuatan.toString(), alamat.toString(), phone.toString(), harga.toString(),
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

    override fun onResultDetail(responseProdukDetail: ResponseProdukDetail) {

        val produk = responseProdukDetail.dataProduk

        edtNameToko.setText( produk.nama_toko )
        edtJenispembuatan.setText( produk.jenis_pembuatan )
        edtAlamateddres.setText( produk.alamat )
        edtPhone.setText( produk.phone )
        edtHarga.setText( produk.harga )
        edtLocation.setText( "${produk.latitude}, ${produk.longitude}" )
        Constant.LATITUDE = produk.latitude!!
        Constant.LONGITUDE = produk.longitude!!

        GlideHelper.setImage(this,"http://192.168.43.224/api_spesialisJB/public/"+ produk.gambar!!, imvImages)

        edtDeskripsi.setText( produk.deskripsi )
    }

    override fun onResultUpdate(responseProdukUpdate: ResponseProdukUpdate) {
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