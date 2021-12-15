package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import kotlinx.android.synthetic.main.activity_tambahrek_create.*

class TambahrekCreateActivity : AppCompatActivity(), TambahrekCreateContract.View {


    lateinit var prefsManager: PrefsManager
    lateinit var presenter: TambahrekCreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahrek_create)
        presenter = TambahrekCreatePresenter(this)
        prefsManager = PrefsManager(this)
    }

//    override fun onStart() {
//        super.onStart()
//        }
//    }


    override fun initActivity() {
        supportActionBar!!.title = "Tambah No Rekening"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {

        BTN_savetambahrek.setOnClickListener {
            val jenis_bank = edtJenisBank.text
            val norek = edtNorek.text
            val nama = edtAtasnama.text

            if ( jenis_bank.isNullOrEmpty() || norek.isNullOrEmpty() || nama.isNullOrEmpty() ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.insertTambahrek(prefsManager.prefsId, jenis_bank.toString(), norek.toString(), nama.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true -> {
                progresstambahrek.visibility = View.VISIBLE
                BTN_savetambahrek.visibility = View.GONE
            }
            false -> {
                progresstambahrek.visibility = View.GONE
                BTN_savetambahrek.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseTambahrekUpdate: ResponseTambahrekUpdate) {
        showMessage(responseTambahrekUpdate.msg)
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