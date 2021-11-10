package com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.loginjasa.ResponseLogin
import com.ilham.taspesialisbangunan.ui.USER.homeuser.UserActivity
import kotlinx.android.synthetic.main.activity_loginjasa.*
import kotlinx.android.synthetic.main.activity_loginuser.progress

class LoginjasaActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginjasa)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Masuk"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun initListener() {
        btnLoginjasa.setOnClickListener {
            presenter.doLogin( edtEmail.text.toString(), edtPassword.text.toString())
        }
        btn_daftarakun.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnloginuserpelanggan.setOnClickListener{
            startActivity(Intent(this, UserActivity::class.java))
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        presenter.setPrefs(prefsManager, responseLogin.jasauser!!)
        finish()
    }


    override fun onLoading(loading: Boolean){
        when(loading){
            true -> {
                progress.visibility = View.VISIBLE
                btnLoginjasa.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
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