package com.ilham.taspesialisbangunan.ui.pelanggan.lupapassword

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.login.LoginuserPresenter
import com.ilham.taspesialisbangunan.ui.pelanggan.register.RegisterPelangganActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.register.phoneferifi.PhoneVerifiActivity
import kotlinx.android.synthetic.main.activity_loginuser.*
import kotlinx.android.synthetic.main.activity_lupapassword.*
import kotlinx.android.synthetic.main.activity_register_pelanggan.*
import java.util.concurrent.TimeUnit

class LupapasswordActivity : AppCompatActivity(), LupapasswordContract.View {

    lateinit var presenter: LupapasswordPresenter
    lateinit var fcm:String

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
        setContentView(R.layout.activity_lupapassword)
        presenter = LupapasswordPresenter(this)

    }

    override fun initActivity() {

        callback()

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        btn_lupapassword.setOnClickListener {
            phone = edt_phonelupapassword.text.toString().trim()
            if (edt_phonelupapassword.text!!.isEmpty()){
                edt_phonelupapassword.error = "Masukkan Nomor Telepon"
                edt_phonelupapassword.requestFocus()
            }else
                presenter.lupapassword_pelanggan(edt_phonelupapassword.text.toString(), "pelanggan")
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            val user = responseUser.user
            Constant.USERPELANGGAN_ID = user!!.id!!
            Constant.FORGET = true
            startPhoneNumberVerification("+62$phone")
        }else {
            showMessage(responseUser.msg)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
                Toast.makeText(applicationContext, "Pendaftaran berhasil, silakan lakukan verifikasi untuk melanjutkan", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this@LupapasswordActivity, PhoneVerifiActivity::class.java)
                intent.putExtra("phone", "+62$phone")
                intent.putExtra("verificationId", storedVerificationId)
                startActivity(intent)
            }
        }
    }


}