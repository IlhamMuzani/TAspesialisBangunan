package com.ilham.taspesialisbangunan.ui.pelanggan.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.lupapassword.LupapasswordActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.register.RegisterPelangganActivity
import kotlinx.android.synthetic.main.activity_loginuser.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginuserActivity : AppCompatActivity(), LoginuserContract.View {

    lateinit var presenter: LoginuserPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var fcm:String

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginuser)
        presenter = LoginuserPresenter(this)
        prefsManager = PrefsManager(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_nama.text = "Login User"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
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

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnLoginuser.setOnClickListener {
            if (edtEmailuser.text!!.isEmpty()) {
                edtEmailuser.error = "Masukkan Nomor Telepon"
                edtEmailuser.requestFocus()
            } else if (edtPassworduser.text!!.isEmpty()) {
                edtPassworduser.error = "Kolom Password Tidak Boleh Kosong"
                edtPassworduser.requestFocus()
            } else {
                presenter.doLogin(
                    edtEmailuser.text.toString(),
                    edtPassworduser.text.toString(),
                    fcm
                )
            }
        }

        btn_daftarakunuser.setOnClickListener{
            startActivity(Intent(this, RegisterPelangganActivity::class.java))
        }

        txtlupapassword.setOnClickListener{
            startActivity(Intent(this, LupapasswordActivity::class.java))
        }

    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.user!!.verifikasi == "1") {
            presenter.setPrefs(prefsManager, responseUser.user!!)
            startActivity(Intent(this, UserActivity::class.java))
        }else {
            startActivity(Intent(this, RegisterPelangganActivity::class.java))
    }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}