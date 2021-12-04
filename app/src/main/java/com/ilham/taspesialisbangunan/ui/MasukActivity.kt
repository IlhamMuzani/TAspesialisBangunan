package com.ilham.taspesialisbangunan.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.login.LoginuserActivity
import com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa.LoginjasaActivity
import kotlinx.android.synthetic.main.activity_masuk.*

class MasukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        btn()

    }

    fun btn (){
        btnloginuserpelanggan.setOnClickListener {
            onBackPressed()
            startActivity(Intent(this, LoginuserActivity::class.java))
        }

        btnloginuserjasa.setOnClickListener {
            startActivity(Intent(this, LoginjasaActivity::class.java))
        }
    }
}