package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.TambahRekActivity
import kotlinx.android.synthetic.main.activity_akun.*
import kotlinx.android.synthetic.main.activity_akun.btnBackjasa

class AkunActivity : AppCompatActivity(), AkunContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun)
        prefsManager = PrefsManager(this)
        presenter = AkunPresenter(this)
        presenter.doLogin(prefsManager)
    }

    override fun initActivity() {
        supportActionBar!!.hide()
    }

    override fun initListener() {

        btn_tambahrekjasa.setOnClickListener{
            startActivity(Intent(this, TambahRekActivity::class.java))
        }

        btnBackjasa.setOnClickListener {
            finish()
        }
        txvLogout.setOnClickListener {
            presenter.doLogout(prefsManager)
        }
    }

    override fun onResultLogin(prefsManager: PrefsManager) {
        txvEmailjasa.text = prefsManager.prefsEmail
        txvNamejasa.text = prefsManager.prefsUsername
        txvAlamatjasa.text = prefsManager.prefsAlamat
        txvPhonejasa.text = prefsManager.prefsPhone
    }

    override fun onResultLogout() {
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}