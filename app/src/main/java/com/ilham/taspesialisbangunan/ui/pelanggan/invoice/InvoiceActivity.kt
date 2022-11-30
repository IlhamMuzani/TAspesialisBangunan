package com.ilham.taspesialisbangunan.ui.pelanggan.passwordbaru

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import kotlinx.android.synthetic.main.activity_passwordbaru.*
import kotlinx.android.synthetic.main.activity_passwordbaru.btnPasswordbaru
import kotlinx.android.synthetic.main.activity_perbaruipassword.*
import kotlinx.android.synthetic.main.activity_register_pelanggan.*
import java.util.concurrent.TimeUnit

class PasswordbaruActivity : AppCompatActivity(), PasswordbaruContract.View {

    lateinit var presenter: PasswordbaruPresenter

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordbaru)
        presenter = PasswordbaruPresenter(this)

    }

    override fun initActivity() {

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        btnPasswordbaru.setOnClickListener {
            if (edtPasswordbaru.text!!.isEmpty()){
                edtPasswordbaru.error = "Masukkan Password"
                edtPasswordbaru.requestFocus()
            }else
                presenter.passwordbaru_pelanggan(Constant.USERPELANGGAN_ID, edtPasswordbaru.text.toString())
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUserUpdate: ResponseUserUpdate) {
        showMessage(responseUserUpdate.msg)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}