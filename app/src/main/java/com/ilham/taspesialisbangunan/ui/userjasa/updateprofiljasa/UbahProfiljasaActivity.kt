package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseJasaUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import kotlinx.android.synthetic.main.activity_ubah_profil.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class UbahProfiljasaActivity : AppCompatActivity(), UbahProfiljasaContract.View {

    lateinit var presenter: UbahProfiljasaPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_profiljasa)
        presenter = UbahProfiljasaPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailProfiljasa( Constant.USERJASA_ID.toString() )
    }

    override fun initActivity() {
        tv_bgjasa.text="Update Profile"
    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btn_ubahjasa.setOnClickListener {
            val username = edt_ubahusernamejasa.text
            val email = edt_ubahemailjasa.text
            val alamat = edt_ubahalamatjasa.text
            val phone = edt_ubahphonejasa.text

            if ( username.isNullOrEmpty() || email.isNullOrEmpty() || alamat.isNullOrEmpty() || phone.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateProfiljasa(Constant.USERJASA_ID, username.toString(), email.toString(), alamat.toString(), phone.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressubahjasa.visibility = View.VISIBLE
                btn_ubahjasa.visibility = View.GONE
            }
            false -> {
                progressubahjasa.visibility = View.GONE
                btn_ubahjasa.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultDetailProfiljasa(responseUserdetail: ResponseUserdetail) {
        val jasa = responseUserdetail.user

        edt_ubahusernamejasa.setText( jasa.username )
        edt_ubahemailjasa.setText( jasa.email )
        edt_ubahalamatjasa.setText( jasa.alamat )
        edt_ubahphonejasa.setText( jasa.phone )
    }

    override fun onResultUpdateProfiljasa(responseJasaUpdate: ResponseJasaUpdate) {
        presenter.setPrefs(prefsManager, responseJasaUpdate.user!!)
//        startActivity(Intent(this, UserActivity::class.java))
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}