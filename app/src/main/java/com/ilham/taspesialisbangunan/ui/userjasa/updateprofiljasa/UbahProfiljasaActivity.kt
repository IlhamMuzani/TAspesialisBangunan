package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi.PhoneVerifiActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_pengajuan.*
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_ubah_profil.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import java.util.concurrent.TimeUnit

class UbahProfiljasaActivity : AppCompatActivity(), UbahProfiljasaContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: UbahProfiljasaPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var jasa: DataUser

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog

    // Firebase Phone
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var code: String? = null
    lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_profiljasa)
        presenter = UbahProfiljasaPresenter(this)
        prefsManager = PrefsManager(this)
        presenter.getDetailProfiljasa( prefsManager.prefsId )

    }

    override fun onStart() {
        super.onStart()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocation3.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
//        presenter.getDetailProfiljasa( Constant.USERJASA_ID.toString() )
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text="Ubah Profil"

        callback()

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")


    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        BtnLocation3.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        btnpilihlokasi3.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        btn_ubahjasa.setOnClickListener {
            phone = edt_ubahphonejasa.text.trim().toString()
            if (edt_ubahusernamejasa.text!!.isEmpty()){
                edt_ubahusernamejasa.error = "Masukan Nama"
                edt_ubahusernamejasa.requestFocus()
            }else if (edt_namatokoupdate.text!!.isEmpty()){
                edt_namatokoupdate.error = "Masukan Nama Toko"
                edt_namatokoupdate.requestFocus()
            }else if (edt_namatokoupdate.text!!.isEmpty()){
                edt_namatokoupdate.error = "Masukan Nama Toko"
                edt_namatokoupdate.requestFocus()
            }else if (Constant.KECAMATAN_ID == 0){
                showMessage("Pilih Kecamatan")
            }else if (edt_rtrw.text!!.isEmpty()){
                edt_rtrw.error = "Masukan Detail Alamat"
                edt_rtrw.requestFocus()
            } else if (Constant.LATITUDE.isEmpty()) {
                showMessage("Pilih Lokasi")
            }else if (edt_rtrw.text!!.isEmpty()){
                edt_ubahphonejasa.error = "Kolom Telepon Tidak Boleh Kosong"
                edt_ubahphonejasa.requestFocus()
            }else if (edtDeskripsiuser.text!!.isEmpty()){
                edtDeskripsiuser.error = "Kolom Deskripsi Tidak Boleh Kosong"
                edtDeskripsiuser.requestFocus()
            } else {
                presenter.updateProfiljasa(Constant.USERPELANGGAN_ID, edt_ubahusernamejasa.text.toString(), edt_namatokoupdate.text.toString(), Constant.KECAMATAN_NAME, Constant.KELURAHAN_NAME, edt_rtrw.text.toString(), Constant.LATITUDE, Constant.LONGITUDE, jasa.phone.toString(), edt_ubahphonejasa.text.toString(), edtDeskripsiuser.text.toString(), FileUtils.getFile(this, uriImage))
            }
        }

        fotoprofilejasa.setOnClickListener {
            if (GalleryHelper.permissionGallery(this,this,pickImage)){
                GalleryHelper.openGallery(this)
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when(loading){
            true -> {
                sLoading.setTitleText(message).show()
                btn_ubahjasa.visibility = View.GONE
            }
            false -> {
                sLoading.dismiss()
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

        edtLocation3.setText( "${jasa.latitude}, ${jasa.longitude}" )
        edt_namatokoupdate.setText( jasa.nama_toko)
        edtDeskripsiuser.setText( jasa.deskripsi)
        edt_ubahusernamejasa.setText( jasa.username )
        spinnerkecamatanupdate()
        edt_ubahphonejasa.setText( jasa.phone )
        edt_rtrw.setText( jasa.alamat )
    }

    override fun onResultUpdateProfiljasa(responseUser: ResponseUser) {
        val status: Boolean = responseUser.status
        val msg: String = responseUser.msg!!

        if (status){
            if (msg == "Lakukan verifikasi OTP untuk memperbarui nomor telepon"){
                val user = responseUser.user!!
                Constant.UPDATE = true
                Constant.USERPELANGGAN_ID = user.id!!
                startPhoneNumberVerification("+62$phone")
            }else {
                showMessage(responseUser.msg)
                finish()
            }
        }else{
            showMessage(responseUser.msg)
        }
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
                    Constant.KELURAHAN_NAME = "Pilih Kelurahan"
                    Constant.LATITUDE = ""
                    Constant.LONGITUDE = ""
                } else {
                    val namaKelurahan = responseALamatList.dataAlamat[position - 1].kelurahan
                    Constant.KELURAHAN_NAME = namaKelurahan.toString()
                    Constant.LATITUDE = responseALamatList.dataAlamat[position-1].latitude.toString()
                    Constant.LONGITUDE = responseALamatList.dataAlamat[position-1].longitude.toString()
                }
                edtLocation3.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun callback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                code = credential.smsCode
            }

            override fun onVerificationFailed(e: FirebaseException) {
                sLoading.dismiss()
                Toast.makeText(applicationContext, e.message!!, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                sLoading.dismiss()
                storedVerificationId = verificationId
                resendToken = token
                Toast.makeText(applicationContext, "Kode Terikirim, Lakukan verifikasi untuk melanjutkan", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this@UbahProfiljasaActivity, PhoneVerifiActivity::class.java)
                intent.putExtra("phone", phone)
                intent.putExtra("verificationId", storedVerificationId)
                startActivity(intent)
            }
        }
    }

    fun startPhoneNumberVerification(phone: String) {
        sLoading.setTitleText("Loading...").show()
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}