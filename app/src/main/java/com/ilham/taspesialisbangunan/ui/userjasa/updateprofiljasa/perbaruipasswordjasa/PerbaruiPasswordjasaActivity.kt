package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa.perbaruipasswordjasa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import kotlinx.android.synthetic.main.activity_perbaruipassword.*
import kotlinx.android.synthetic.main.activity_perbaruipassword.edtPasswordbaru1
import kotlinx.android.synthetic.main.activity_perbaruipasswordjasa.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class PerbaruiPasswordjasaActivity : AppCompatActivity(), PerbaruiPasswordjasaContract.View {

    lateinit var presenter: PerbaruiPasswordjasaPresenter
    lateinit var fcm:String

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perbaruipasswordjasa)
        presenter = PerbaruiPasswordjasaPresenter(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text ="Password Baru"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btnPasswordbaru2jasa.setOnClickListener {
            if (edtPasswordbaru1jasa.text!!.isEmpty()){
                edtPasswordbaru1jasa.error = "Masukkan Password"
                edtPasswordbaru1jasa.requestFocus()
            }else if (edtkonfirmasipasswordjasa.text!!.isEmpty()){
                    edtkonfirmasipasswordjasa.error = "Konfirmasi Password"
                    edtkonfirmasipasswordjasa.requestFocus()
                }else
                presenter.Perbaruipasswordjasa(Constant.USERJASA_ID, edtPasswordbaru1jasa.text.toString(), edtkonfirmasipasswordjasa.text.toString())
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