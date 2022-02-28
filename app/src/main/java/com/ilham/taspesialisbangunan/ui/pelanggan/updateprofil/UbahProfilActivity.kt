package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_ubah_profil.*
import kotlinx.android.synthetic.main.toolbar.*

class UbahProfilActivity : AppCompatActivity(), UbahProfilContract.View {

    lateinit var presenter: UbahProfilPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_profil)
        presenter = UbahProfilPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailProfil( Constant.USERPELANGGAN_ID.toString() )
    }

    override fun initActivity() {
        tv_nama.text="Update Profile"
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_ubah.setOnClickListener {
            val username = edt_ubahusername.text
            val email = edt_ubahemaiil.text
            val alamat = edt_ubahalamat.text
            val phone = edt_ubahphone.text

            if ( username.isNullOrEmpty() || email.isNullOrEmpty() || alamat.isNullOrEmpty() || phone.isNullOrEmpty()) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateProfil(Constant.USERPELANGGAN_ID, username.toString(), email.toString(), alamat.toString(), phone.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progressubah.visibility = View.VISIBLE
                btn_ubah.visibility = View.GONE
            }
            false -> {
                progressubah.visibility = View.GONE
                btn_ubah.visibility = View.VISIBLE
            }
        }
    }

    override fun onResultDetailProfil(responseUserdetail: ResponseUserdetail) {
        val pelanggan = responseUserdetail.user

        edt_ubahusername.setText( pelanggan.username )
        edt_ubahemaiil.setText( pelanggan.email )
        edt_ubahalamat.setText( pelanggan.alamat )
        edt_ubahphone.setText( pelanggan.phone )

    }

    override fun onResultUpdateProfil(responsePelangganUpdate: ResponsePelangganUpdate) {
        presenter.setPrefs(prefsManager, responsePelangganUpdate.user!!)
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