package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.Aduanjasa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.aduanjasa.ResponseAduanInsert
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity(), ReportContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: ReportPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        presenter = ReportPresenter(this)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Laporkan Jasa"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {

        imvImagesAduan.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btn_laporkan.setOnClickListener {
            val deskripsi = edtdeskripsiA.text

            if ( deskripsi.isNullOrEmpty() || uriImage == null ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertAduanjasa(FileUtils.getFile(this, uriImage), deskripsi.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressaduan.visibility = View.VISIBLE
                btn_laporkan.visibility = View.GONE
            }
            false -> {
                progressaduan.visibility = View.GONE
                btn_laporkan.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseAduanInsert: ResponseAduanInsert) {
        showMessage(responseAduanInsert.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvImagesAduan.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}