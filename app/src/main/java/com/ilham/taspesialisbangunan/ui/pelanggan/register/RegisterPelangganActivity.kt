package com.ilham.taspesialisbangunan.ui.pelanggan.register

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.network.ApiConfig
import com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi.PhoneVerifiActivity
import kotlinx.android.synthetic.main.activity_loginuser.*
import kotlinx.android.synthetic.main.activity_pengajuan.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_pelanggan.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RegisterPelangganActivity : AppCompatActivity() {

    lateinit var fcm:String
    private var uriImage: Uri? = null
    private var pickImage = 1

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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pelanggan)
        tv_nama.text="Register User"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        getfcm()

        callback()

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_registeruser.setOnClickListener {
            registeruser()
        }
    }

    private fun getfcm() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener
        { task ->
            if (!task.isSuccessful) {
                Log.w("Respon", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            fcm = token.toString()
            // Log and toast
            Log.d("respon scm:", token.toString())
        })
    }

    override fun onStart() {
        super.onStart()
    }

    fun registeruser() {
        if (edt_usernameuser.text!!.isEmpty()) {
            edt_usernameuser.error = "Kolom Nama Tidak Boleh Kosong"
            edt_usernameuser.requestFocus()
            return
//        } else if (edt_emailuser.text!!.isEmpty()) {
//            edt_emailuser.error = "Kolom Email Tidak Boleh Kosong"
//            edt_emailuser.requestFocus()
//            return
        } else if (edt_alamatuser.text!!.isEmpty()) {
            edt_alamatuser.error = "Kolom Email Tidak Boleh Kosong"
            edt_alamatuser.requestFocus()
            return
        } else if (edt_phoneuser.text!!.isEmpty()) {
            edt_phoneuser.error = "Kolom Nomor Telpone Tidak Boleh Kosong"
            edt_phoneuser.requestFocus()
            return
        } else if (edt_passworduser.text!!.isEmpty()) {
            edt_passworduser.error = "Kolom Password Tidak Boleh Kosong"
            edt_passworduser.requestFocus()
            return

        }
        sLoading.setTitleText("Loading").show()
        phone = edt_phoneuser.text.toString().trim()
        ApiConfig.endpoint.register_pelanggan(edt_usernameuser.text.toString(), edt_alamatuser.text.toString(),
            phone, edt_passworduser.text.toString(), fcm).enqueue(object :Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                sLoading.dismiss()
                btn_registeruser.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    val respon = response.body()!!
                    if(respon.status){
                        val user = respon.user
                        Constant.USERPELANGGAN_ID = user!!.id!!
                        startPhoneNumberVerification("+62$phone")
                    } else {
                        Toast.makeText(this@RegisterPelangganActivity, respon.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@RegisterPelangganActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
        })
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
                Toast.makeText(applicationContext, "Pendaftaran berhasil, silakan lakukan verifikasi untuk melanjutkan", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this@RegisterPelangganActivity, PhoneVerifiActivity::class.java)
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