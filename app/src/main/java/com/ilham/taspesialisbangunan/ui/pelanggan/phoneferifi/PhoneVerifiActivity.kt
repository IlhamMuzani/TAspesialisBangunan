package com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.fragment.Akun.AkunFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.login.LoginuserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.passwordbaru.PasswordbaruActivity
import com.ilham.taspesialisbangunan.ui.userjasa.loginjasa.LoginjasaActivity
import kotlinx.android.synthetic.main.activity_phoneverifi.*
import java.util.concurrent.TimeUnit

class PhoneVerifiActivity : AppCompatActivity(), PhoneverifiContract.View {


    lateinit var presenter : PhoneverifiPresenter

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var code: String? = null
    lateinit var phone: String

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phoneverifi)
        presenter = PhoneverifiPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        edtotp1.setText("")
        edtotp2.setText("")
        edtotp3.setText("")
        edtotp4.setText("")
        edtotp5.setText("")
        edtotp6.setText("")    }

    override fun onResume() {
        super.onResume()
        edtotp1.setText("")
        edtotp2.setText("")
        edtotp3.setText("")
        edtotp4.setText("")
        edtotp5.setText("")
        edtotp6.setText("")

    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.FORGET = false
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        setupInput()
        callback()

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")


        verificationId = intent.getStringExtra("verificationId")
        phone = intent.getStringExtra("phone")!!
        code = intent.getStringExtra("code")

    }

    override fun initListener() {

        kirimulangverifi.setOnClickListener {
            startPhoneNumberVerification(phone)
        }

        btn_verifi.setOnClickListener {

            val otp1 = edtotp1.text.toString().trim()
            val otp2 = edtotp2.text.toString().trim()
            val otp3 = edtotp3.text.toString().trim()
            val otp4 = edtotp4.text.toString().trim()
            val otp5 = edtotp5.text.toString().trim()
            val otp6 = edtotp6.text.toString().trim()
            if (otp1.isEmpty() || otp2.isEmpty()
                || otp3.isEmpty() || otp4.isEmpty()
                || otp5.isEmpty() || otp6.isEmpty()
            ) {
                Toast.makeText(this, "Masukan kode OTP dengan benar!", Toast.LENGTH_SHORT).show()
            } else {
                val code = otp1 + otp2 + otp3 + otp4 + otp5 + otp6
                if (verificationId != null) {
                    verifyPhoneNumberWithCode(verificationId, code)
                }
            }

        }

    }

    override fun onLoading(loading: Boolean, message: String?) {
        when(loading){
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }

    override fun setupInput() {
        edtotp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    edtotp2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        edtotp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    edtotp3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        edtotp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    edtotp4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        edtotp4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    edtotp5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        edtotp5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    edtotp6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    override fun callback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                code = credential.smsCode
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onLoading(false)
                Toast.makeText(applicationContext, e.message!!, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationIdnew: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                onLoading(false)
                verificationId = verificationIdnew
                resendToken = token
//                Toast.makeText(applicationContext, "Pendaftaran berhasil, silakan lakukan verifikasi untuk melanjutkan", Toast.LENGTH_SHORT).show()

//                val intent = Intent(applicationContext, PhoneVerifiActivity::class.java)
//                intent.putExtra("phone", "+62$phone")
//                intent.putExtra("verificationId", verificationId)
//                startActivity(intent)
            }
        }
    }

    override fun onResultDatauserupdate(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.status) {
            if (responseUserdetail.user.status == "pelanggan"){
//                startActivity(Intent(this, LoginuserActivity::class.java))
                finish()
            } else
                finish()
//                startActivity(Intent(this, LoginjasaActivity::class.java))
        }
        Toast.makeText(applicationContext, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
    }

    override fun onResultphonebaru(responseUserUpdate: ResponseUserUpdate) {
        finish()
    }

    override fun startPhoneNumberVerification(phone: String) {
        onLoading(true)
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    override fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (Constant.FORGET) {
                        Toast.makeText(this, "Verifikasi Berhasil", Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, PasswordbaruActivity::class.java))
                    } else {
                        if (Constant.UPDATE) {
                            presenter.phone_baru(Constant.USERPELANGGAN_ID, phone!!)
                        } else {
                            presenter.getverifi(Constant.USERPELANGGAN_ID)
                            presenter.getverifi(Constant.USERJASA_ID)
                        }
                    }
                } else {
                    onLoading(false)
                    Toast.makeText(this, "Gagal! Kode OTP salah!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}