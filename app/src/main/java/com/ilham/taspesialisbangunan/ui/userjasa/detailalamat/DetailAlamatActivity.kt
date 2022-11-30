package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

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
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_ubah_profil.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class UbahProfiljasaActivity : AppCompatActivity(), UbahProfiljasaContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: UbahProfiljasaPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var jasa: DataUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_profiljasa)
        presenter = UbahProfiljasaPresenter(this)
        prefsManager = PrefsManager(this)
        presenter.getDetailProfiljasa( prefsManager.prefsId )

    }

    override fun onStart() {
        super.onStart()
//        presenter.getDetailProfiljasa( Constant.USERJASA_ID.toString() )
    }

    override fun initActivity() {
        tv_bgjasa.text="Ubah Profil"
    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btn_ubahjasa.setOnClickListener {
            val username = edt_ubahusernamejasa.text
            val phone = edt_ubahphonejasa.text
            val rtrw = edt_rtrw.text

            if ( username.isNullOrEmpty() || phone.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateProfiljasa(Constant.USERJASA_ID, username.toString(), Constant.KECAMATAN_NAME, Constant.KELURAHAN_NAME, rtrw.toString(), phone.toString(), FileUtils.getFile(this, uriImage))
            }
        }

        fotoprofilejasa.setOnClickListener {
            if (GalleryHelper.permissionGallery(this,this,pickImage)){
                GalleryHelper.openGallery(this)
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressubahjasa.visibility = View.VISIBLE
                btn_ubahjasa.visibility = View.GONE
            }
            false -> {
                progressubahjasa.visibility = View.GONE
                btn_ubahjasa.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail) {
        jasa = responseUserdetail.user

        if (jasa.gambar.isNullOrEmpty()){
//            foto kosong
            }else {
                GlideHelper.setImage(this, Constant.IP_IMAGE + jasa.gambar!!, fotoprofilejasa)
        }

        edt_ubahusernamejasa.setText( jasa.username )
        spinnerkecamatanupdate()
        edt_ubahphonejasa.setText( jasa.phone )
        edt_rtrw.setText( jasa.alamat )
    }

    override fun onResultUpdateProfiljasa(responseUserUpdate: ResponseUserUpdate) {
//        presenter.setPrefs(prefsManager, responseJasaUpdate.user!!)
////        startActivity(Intent(this, UserActivity::class.java))
//        finish()

        showMessage(responseUserUpdate.msg)
        finish()
    }

    override fun onResultSearchalamatkecamatanupdate(responseALamatList: ResponseALamatList) {
        spinnerkelurahanupdate(responseALamatList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            fotoprofilejasa.setImageURI(uriImage)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun spinnerkecamatanupdate() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kecamatan")
        arrayString.add("Kecamatan Margadana")
        arrayString.add("Kecamatan Tegal Barat")
        arrayString.add("Kecamatan Tegal Selatan")
        arrayString.add("Kecamatan Tegal Timur ")

        val adapter = ArrayAdapter(this, R.layout.item_spinneruser, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_ubahalamatjasa.adapter = adapter
        val selection = adapter.getPosition(jasa.kecamatan)
        edt_ubahalamatjasa.setSelection(selection)
        edt_ubahalamatjasa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.KECAMATAN_ID = 0
                        Constant.KECAMATAN_NAME = "Pilih Kecamatan"
                    }
                    1 -> {
                        Constant.KECAMATAN_ID = 1
                        Constant.KECAMATAN_NAME = "Kecamatan Margadana"
                        presenter.searchAlamatkecamatanupdate(Constant.KECAMATAN_NAME)
                    }
                    2 -> {
                        Constant.KECAMATAN_ID = 2
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Barat"
                        presenter.searchAlamatkecamatanupdate(Constant.KECAMATAN_NAME)
                    }
                    3 -> {
                        Constant.KECAMATAN_ID = 3
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Selatan"
                        presenter.searchAlamatkecamatanupdate(Constant.KECAMATAN_NAME)
                    }
                    4 -> {
                        Constant.KECAMATAN_ID = 4
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Timur"
                        presenter.searchAlamatkecamatanupdate(Constant.KECAMATAN_NAME)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    fun spinnerkelurahanupdate(responseALamatList: ResponseALamatList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kelurahan")
        for (alamat in responseALamatList.dataAlamat){arrayString.add(alamat.kelurahan!!)}

        val adapter = ArrayAdapter(this, R.layout.item_spinneruser, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_ubahalamatkelurahan.adapter = adapter
        val selection = adapter.getPosition(jasa.kelurahan)
        edt_ubahalamatkelurahan.setSelection(selection)
        edt_ubahalamatkelurahan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.KECAMATAN_ID = 0
                    Constant.KELURAHAN_NAME = "Pilih Kelurahan"
                } else {
                    val namaKelurahan = responseALamatList.dataAlamat[position - 1].kelurahan
                    Constant.KELURAHAN_ID = position
                    Constant.KELURAHAN_NAME = namaKelurahan.toString()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


}