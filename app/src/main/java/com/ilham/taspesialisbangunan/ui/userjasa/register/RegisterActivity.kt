package com.ilham.taspesialisbangunan.ui.userjasa.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.alamat.DataAlamat
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.network.ApiConfig
import com.ilham.taspesialisbangunan.ui.pelanggan.phoneferifi.PhoneVerifiActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukMapsActivity
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_pelanggan.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*
import kotlinx.android.synthetic.main.toolbarjasa.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    lateinit var fcm:String
    lateinit var presenter: RegisterPresenter
    lateinit var dataAlamat: DataAlamat

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


    // Firebase Phone
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private var code: String? = null
    lateinit var phone: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)
        tv_bgjasa.text = "Register Jasa"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")


        getfcm()

        callback()

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            registerjasauser()
        }

        BtnLocation2.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

        btnpilihlokasi2.setOnClickListener {
            startActivity(Intent(this, ProdukMapsActivity::class.java))
        }

    }

    private fun getfcm(){
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener
    {
        task ->
        if (!task.isSuccessful) {
            Log.w("Respon", "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val token = task.result
        fcm = token.toString()
        // Log and toast
        Log.d("respon scm:", token.toString())
    })
}

    override fun onStart() {
        super.onStart()
        spinnerkecamatanregister()
        if (Constant.LATITUDE.isNotEmpty()) {
            edtLocation2.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun initActivity() {
    }

    override fun initListener() {
    }

    override fun onLoading(loading: Boolean) {
    }

    override fun onResultSearchalamatkecamatan(responseALamatList: ResponseALamatList) {
        spinnerkelurahanregister(responseALamatList)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun registerjasauser(){

        if (edt_username.text!!.isEmpty()) {
            edt_username.error = "Kolom Nama Tidak Boleh Kosong"
            edt_username.requestFocus()
            return
        } else if (edt_namatoko.text!!.isEmpty()) {
            edt_namatoko.error = "Kolom Nama Toko Tidak Boleh Kosong"
            edt_namatoko.requestFocus()
//            return
//        } else if (edt_email.text!!.isEmpty()) {
//            edt_email.error = "Kolom Email Tidak Boleh Kosong"
//            edt_email.requestFocus()
//            return
        } else if (edt_alamatjasa.text!!.isEmpty()) {
            edt_alamatjasa.error = "Kolom Detail Alamat Tidak Boleh Kosong"
            edt_alamatjasa.requestFocus()
            return
        } else if (edt_phone.text!!.isEmpty()) {
            edt_phone.error = "Kolom Nomor Telpone Tidak Boleh Kosong"
            edt_phone.requestFocus()
            return
        } else if (edt_password.text!!.isEmpty()) {
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        } else {
            (Constant.KECAMATAN_ID == 0)
        }

        sLoading.setTitleText("Loading").show()
        phone = edt_phone.text.toString().trim()
        ApiConfig.endpoint.register_jasa(edt_username.text.toString(), edt_namatoko.text.toString(),Constant.KECAMATAN_NAME, Constant.KELURAHAN_NAME, edt_alamatjasa.text.toString(), Constant.LATITUDE,
            Constant.LONGITUDE, phone,
            edt_password.text.toString(), fcm).enqueue(object : Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                sLoading.dismiss()
                btn_register.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    val respon = response.body()!!
                    if(respon.status){
                        val user = respon.user
                        Constant.USERJASA_ID = user!!.id!!
                        startPhoneNumberVerification("+62$phone")
                    } else {
                        Toast.makeText(this@RegisterActivity, respon.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun callback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                code = credential.smsCode
            }

            override fun onVerificationFailed(e: FirebaseException) {
                sLoading.dismiss()
                Toast.makeText(applicationContext, e.message!!, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                sLoading.dismiss()
                storedVerificationId = verificationId
                resendToken = token
                Toast.makeText(applicationContext, "Pendaftaran berhasil, silakan lakukan verifikasi untuk melanjutkan", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this@RegisterActivity, PhoneVerifiActivity::class.java)
                intent.putExtra("phone", phone)
                intent.putExtra("verificationId", storedVerificationId)
                startActivity(intent)
            }
        }
    }

    fun startPhoneNumberVerification(phone: String) {
        sLoading.setTitleText("Loading...").show()
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun spinnerkecamatanregister() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kecamatan")
        arrayString.add("Kecamatan Margadana")
        arrayString.add("Kecamatan Tegal Barat")
        arrayString.add("Kecamatan Tegal Selatan")
        arrayString.add("Kecamatan Tegal Timur ")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_kecamatan.adapter = adapter
        val selection = adapter.getPosition(Constant.KECAMATAN_NAME)
        edt_kecamatan.setSelection(selection)
        edt_kecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.KECAMATAN_ID = 0
                        Constant.KECAMATAN_NAME = "Pilih Kecamatan"

                    }
                    1 -> {
                        Constant.KECAMATAN_ID = 1
                        Constant.KECAMATAN_NAME = "Kecamatan Margadana"
                        presenter.searchAlamatkecamatan(Constant.KECAMATAN_NAME)

                    }
                    2 -> {
                        Constant.KECAMATAN_ID = 2
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Barat"
                        presenter.searchAlamatkecamatan(Constant.KECAMATAN_NAME)

                    }
                    3 -> {
                        Constant.KECAMATAN_ID = 3
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Selatan"
                        presenter.searchAlamatkecamatan(Constant.KECAMATAN_NAME)

                    }
                    else -> {
                        Constant.KECAMATAN_ID = 4
                        Constant.KECAMATAN_NAME = "Kecamatan Tegal Timur"
                        presenter.searchAlamatkecamatan(Constant.KECAMATAN_NAME)

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerkelurahanregister(responseALamatList: ResponseALamatList) {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Kelurahan")
        for (alamat in responseALamatList.dataAlamat){arrayString.add(alamat.kelurahan!!)}

        val adapter = ArrayAdapter(this, R.layout.item_spinneruser, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_kelurahan.adapter = adapter
        val selection = adapter.getPosition(Constant.KELURAHAN_NAME)
        edt_kelurahan.setSelection(selection)
        edt_kelurahan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    Constant.KELURAHAN_NAME = "Pilih Kelurahan"
                    Constant.LATITUDE = ""
                    Constant.LONGITUDE = ""
                } else {
                    val namaKelurahan = responseALamatList.dataAlamat[position - 1].kelurahan
                    Constant.KELURAHAN_NAME = namaKelurahan.toString()
                    Constant.LATITUDE = responseALamatList.dataAlamat[position-1].latitude.toString()
                    Constant.LONGITUDE = responseALamatList.dataAlamat[position-1].longitude.toString()
                }
                edtLocation2.setText("${Constant.LATITUDE}, ${Constant.LONGITUDE}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}
