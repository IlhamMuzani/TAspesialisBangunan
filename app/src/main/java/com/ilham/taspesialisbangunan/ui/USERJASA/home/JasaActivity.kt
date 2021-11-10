package com.ilham.taspesialisbangunan.ui.USERJASA.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa.LoginjasaActivity
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.NotifikasiJasaActivity
import com.ilham.taspesialisbangunan.ui.USERJASA.profiljasa.AkunActivity
import com.ilham.taspesialisjasabangunan.ui.produkuser.ProdukMaterialJasaActivity
import kotlinx.android.synthetic.main.activity_jasa.*

class JasaActivity : AppCompatActivity(), JasaContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: JasaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jasa)
        prefsManager = PrefsManager(this)
        presenter = JasaPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        when (prefsManager.prefsIsLogin) {
            true -> {
                crv_profil.visibility = View.VISIBLE
                btn_loginjasa.visibility = View.GONE
            }
            false -> {
                crv_profil.visibility = View.GONE
                btn_loginjasa.visibility = View.VISIBLE
            }
        }
    }

    override fun initListener() {

//        crv_transaksi.setOnClickListener {
//            if (prefsManager.prefsIsLogin) {
////                startActivity(Intent(this, AkunActivity::class.java))
//            }else{
//                showMessage( "Silakan Login Terlebih Dahulu" )
//            }
//        }

        crv_Usahajasa.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(this, ProdukMaterialJasaActivity::class.java))
            }else{
                showMessage( "Silakan Login Terlebih Dahulu" )
            }
        }

//        crv_Material.setOnClickListener {
//            if (prefsManager.prefsIsLogin) {
//                startActivity(Intent(this, MaterialActivity::class.java))
//            }else{
//                showMessage( "Silakan Login Terlebih Dahulu" )
//            }
//        }

        crv_notifikasi.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(this, NotifikasiJasaActivity::class.java))
            }else{
                showMessage( "Silakan Login Terlebih Dahulu" )
            }
        }

        crv_profil.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(this, AkunActivity::class.java))
            }else{
                showMessage( "Silakan Login Terlebih Dahulu" )
            }
        }

        btn_loginjasa.setOnClickListener{
            startActivity(Intent(this, LoginjasaActivity::class.java))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}