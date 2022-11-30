package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi.PhoneVerifiActivity
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_pengajuan.*
import kotlinx.android.synthetic.main.activity_ubah_profil.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit

class UbahProfilActivity : AppCompatActivity(), UbahProfilContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: UbahProfilPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var pelanggan: DataUser


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
        setContentView(R.layout.activity_ubah_profil)
        presenter = UbahProfilPresenter(this)
        prefsManager = PrefsManager(this)
        presenter.getDetailProfil( prefsManager.prefsId )


    }

    override fun onStart() {
        super.onStart()
//        presenter.getDetailProfil( Constant.USERPELANGGAN_ID.toString() )
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_nama.text="Ubah Profil"

        callback()

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_ubah.setOnClickListener {
            phone = edt_ubahphone.text.trim().toString()
            if (edt_ubahusername.text!!.isEmpty()){
                edt_ubahusername.error = "Masukan Nama"
                edt_ubahusername.requestFocus()
            }else if (edt_ubahalamat.text!!.isEmpty()){
                edt_ubahalamat.error = "Masukan Alamat"
                edt_ubahalamat.requestFocus()
            } else if (edt_ubahphone.text!!.isEmpty()){
                edt_ubahphone.error = "Masukan Nomor Telepon"
                edt_ubahphone.requestFocus()
            } else {
                presenter.updateProfil(Constant.USERPELANGGAN_ID, edt_ubahusername.text.toString(), edt_ubahalamat.text.toString(), pelanggan.phone.toString(), edt_ubahphone.text.toString(), FileUtils.getFile(this, uriImage))
            }
        }

        fotoprofile.setOnClickListener {
            if (GalleryHelper.permissionGallery(this,this,pickImage)){
                GalleryHelper.openGallery(this)
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when(loading){
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultDetailProfil(responseUserdetail: ResponseUserdetail) {
        pelanggan = responseUserdetail.user

        if (pelanggan.gambar.isNullOrEmpty()){
//            foto kosong
        }else{
            GlideHelper.setImage(this, Constant.IP_IMAGE + pelanggan.gambar, fotoprofile)
        }

        edt_ubahusername.setText( pelanggan.username )
        edt_ubahalamat.setText( pelanggan.alamat )
        edt_ubahphone.setText( pelanggan.phone )

    }

    override fun onResultUpdateProfil(responsePelangganUpdate: ResponsePelangganUpdate) {
        val status: Boolean = responsePelangganUpdate.status
        val msg: String = responsePelangganUpdate.msg!!

        if (status){
            if (msg == "Lakukan verifikasi OTP untuk memperbarui nomor telepon"){
                val user = responsePelangganUpdate.user!!
                Constant.UPDATE = true
                Constant.USERPELANGGAN_ID = user.id!!
                startPhoneNumberVerification("+62$phone")
            }else {
                showMessage(responsePelangganUpdate.msg)
                finish()
            }
        }else{
            showMessage(responsePelangganUpdate.msg)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            fotoprofile.setImageURI(uriImage)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
                val intent = Intent(this@UbahProfilActivity, PhoneVerifiActivity::class.java)
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