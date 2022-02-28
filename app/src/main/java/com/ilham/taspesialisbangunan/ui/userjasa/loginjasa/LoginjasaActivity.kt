package com.ilham.taspesialisbangunan.ui.userjasa.loginjasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.userjasa.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_loginjasa.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class LoginjasaActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginjasa)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun initActivity() {
        tv_bgjasa.text ="Login Jasa"

    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btnLoginjasa.setOnClickListener {
            presenter.doLogin(edtEmail.text.toString(), edtPassword.text.toString())

            if (edtEmail.text!!.isEmpty()){
                edtEmail.error = "Kolom Email Tidak Boleh Kosong"
                edtEmail.requestFocus()
            } else if (edtPassword.text!!.isEmpty()){
                edtPassword.error = "Kolom Password Tidak Boleh Kosong"
                edtPassword.requestFocus()
            }

        }
        btn_daftarakun.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        presenter.setPrefs(prefsManager, responseUser.user!!)
        startActivity(Intent(this, UserActivity::class.java))
    }


    override fun onLoading(loading: Boolean){
        when(loading){
            true -> {
                progressbarjasa.visibility = View.VISIBLE
                btnLoginjasa.visibility = View.GONE
            }
            false -> {
                progressbarjasa.visibility = View.GONE
                btnLoginjasa.visibility = View.VISIBLE
            }
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