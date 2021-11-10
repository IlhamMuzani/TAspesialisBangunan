package com.ilham.taspesialisbangunan.ui.USER.pengajuan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_pengajuan.*
import kotlinx.android.synthetic.main.activity_pengajuan.imvPengajuan

class PengajuanActivity : AppCompatActivity(), PengajuanContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: PengajuanPresenter
    lateinit var prefsManageruser: PrefsManageruser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengajuan)
        presenter = PengajuanPresenter(this)
        prefsManageruser = PrefsManageruser(this)
    }



    override fun initActivity() {
        supportActionBar!!.title = "Pengajuan"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {

        imvPengajuan.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btn_pengajuann.setOnClickListener {
            val deskripsi = edtdeskripsipengajuann.text

            if ( deskripsi.isNullOrEmpty() || uriImage == null ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertPengajuan(
                    Constant.PRODUK_ID.toString(),
                    prefsManageruser.prefsId,
                    FileUtils.getFile(this, uriImage),
                    deskripsi.toString(),
                    "Menunggu"
                )
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progresspengajuan.visibility = View.VISIBLE
                btn_pengajuann.visibility = View.GONE
            }
            false -> {
                progresspengajuan.visibility = View.GONE
                btn_pengajuann.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert) {
        showMessage(responsePengajuanInsert.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvPengajuan.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}