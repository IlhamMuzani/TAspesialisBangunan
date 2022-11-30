package com.ilham.taspesialisbangunan.ui.userjasa.loginjasa

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
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.lupapassword.LupapasswordActivity
import com.ilham.taspesialisbangunan.ui.userjasa.lupapasswordjasa.LupapasswordJasaActivity
import com.ilham.taspesialisbangunan.ui.userjasa.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_loginjasa.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class LoginjasaActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var fcm:String

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginjasa)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text ="Login Jasa"

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

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btnLoginjasa.setOnClickListener {
            presenter.doLogin(edtEmail.text.toString(), edtPassword.text.toString(), fcm)

            if (edtEmail.text!!.isEmpty()){
                edtEmail.error = "Masukkan Nomor Telepon"
                edtEmail.requestFocus()
            } else if (edtPassword.text!!.isEmpty()){
                edtPassword.error = "Masukkan Password"
                edtPassword.requestFocus()
            }

        }
        btn_daftarakun.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        edtlupapasswordjasa.setOnClickListener{
            startActivity(Intent(this, LupapasswordJasaActivity::class.java))
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            presenter.setPrefs(prefsManager, responseUser.user!!)
            startActivity(Intent(this, UserActivity::class.java))
        }else{
            showMessage(responseUser.msg)
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