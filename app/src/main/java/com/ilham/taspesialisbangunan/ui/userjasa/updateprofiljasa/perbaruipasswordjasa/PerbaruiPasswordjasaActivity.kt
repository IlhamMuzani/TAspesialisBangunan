package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.perbaruipassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import kotlinx.android.synthetic.main.activity_perbaruipassword.*

class PerbaruiPasswordActivity : AppCompatActivity(), PerbaruiPasswordContract.View {

    lateinit var presenter: PerbaruiPasswordPresenter
    lateinit var fcm:String

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perbaruipassword)
        presenter = PerbaruiPasswordPresenter(this)
    }

    override fun initActivity() {

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        btnPasswordbaru2.setOnClickListener {
            if (edtPasswordbaru1.text!!.isEmpty()){
                edtPasswordbaru1.error = "Masukkan Password"
                edtPasswordbaru1.requestFocus()
            }else if (edtkonfirmasipassword.text!!.isEmpty()){
                    edtkonfirmasipassword.error = "Konfirmasi Password"
                    edtkonfirmasipassword.requestFocus()
                }else
                presenter.Perbaruipassword(Constant.USERPELANGGAN_ID, edtPasswordbaru1.text.toString(), edtkonfirmasipassword.text.toString())
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