package com.ilham.taspesialisbangunan.ui.USERJASA.loginjasa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.register.ResponseModel
import com.ilham.taspesialisbangunan.network.ApiConfig
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btn_register.setOnClickListener {
        registerjasauser()
        }

//        btn_google.setOnClickListener {
//            dataDUmmy()
//        }
    }

//    fun dataDUmmy() {
//        edt_username.setText("ilhammzni")
//        edt_email.setText("ilhammuzani5@gmail.com")
//        edt_alamat.setText("Tegal")
//        edt_phone.setText("089087546765")
//        edt_password.setText("ilham123")
//    }

    fun registerjasauser(){
        if (edt_username.text.isEmpty()) {
            edt_username.error = "Kolom Nama Tidak Boleh Kosong"
            edt_username.requestFocus()
            return
        } else if (edt_email.text.isEmpty()) {
            edt_email.error = "Kolom Email Tidak Boleh Kosong"
            edt_email.requestFocus()
            return
        } else if (edt_alamat.text.isEmpty()) {
            edt_alamat.error = "Kolom Email Tidak Boleh Kosong"
            edt_alamat.requestFocus()
            return
        } else if (edt_phone.text.isEmpty()) {
            edt_phone.error = "Kolom Nomor Telpone Tidak Boleh Kosong"
            edt_phone.requestFocus()
            return
        } else if (edt_password.text.isEmpty()) {
            edt_password.error = "Kolom Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.endpoint.registerjasauser(edt_username.text.toString(), edt_email.text.toString(), edt_alamat.text.toString(), edt_phone.text.toString(),
        edt_password.text.toString()).enqueue(object : Callback<ResponseModel>{
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                pb.visibility = View.VISIBLE
                val respon = response.body()!!

                if (respon.succes == 1) {
                    val intent = Intent(this@RegisterActivity, LoginjasaActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Succes:"+respon.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error:"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                pb.visibility = View.VISIBLE
                Toast.makeText(this@RegisterActivity, "Error:"+t.message, Toast.LENGTH_SHORT).show()
            }
        })

        }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
