package com.ilham.taspesialisbangunan.ui.USER.loginuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.loginuser.ResponseLoginuser
import com.ilham.taspesialisbangunan.ui.USER.homeuser.UserActivity
import com.ilham.taspesialisbangunan.ui.USERJASA.home.JasaActivity
import kotlinx.android.synthetic.main.activity_loginuser.*
import kotlinx.android.synthetic.main.activity_loginuser.btnloginuserjasa
import kotlinx.android.synthetic.main.activity_loginuser.progress

class LoginuserActivity : AppCompatActivity(), LoginuserContract.View {

    lateinit var presenter: LoginuserPresenter
    lateinit var prefsManageruser: PrefsManageruser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginuser)
        presenter = LoginuserPresenter(this)
        prefsManageruser = PrefsManageruser(this)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Masuk"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {
        btnLoginuser.setOnClickListener {
            presenter.doLogin(edtEmailuser.text.toString(), edtPassworduser.text.toString())
        }

        btn_daftarakunuser.setOnClickListener{
            startActivity(Intent(this, RegisterPelangganActivity::class.java))
        }

        btnloginuserjasa.setOnClickListener{
            startActivity(Intent(this, JasaActivity::class.java))
        }
    }

    override fun onResult(responseLoginuser: ResponseLoginuser) {
        presenter.setPrefs(prefsManageruser, responseLoginuser.userpelanggan!!)
        finish()
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progress.visibility = View.VISIBLE
                btnLoginuser.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
                btnLoginuser.visibility = View.VISIBLE
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