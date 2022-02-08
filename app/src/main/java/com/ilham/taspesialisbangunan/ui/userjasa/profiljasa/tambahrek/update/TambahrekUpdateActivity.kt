package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekDetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import kotlinx.android.synthetic.main.activity_tambahrek_create.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class TambahrekUpdateActivity : AppCompatActivity(), TambahrekUpdateContract.View {

    lateinit var presenter: TambahrekUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahrek_create)
        presenter = TambahrekUpdatePresenter(this)
        presenter.getDetailTambahrek( Constant.TAMBAHREK_ID )
    }


    override fun initActivity() {
        tv_bgjasa.text="Update No Rekening"
    }

    override fun initListener() {

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        BTN_savetambahrek.setOnClickListener {
            val jenis_bank = edtJenisBank.text
            val norek = edtNorek.text
            val nama = edtAtasnama.text

            if ( jenis_bank.isNullOrEmpty() || norek.isNullOrEmpty() || nama.isNullOrEmpty() ) {
                showMessage("Lengkapi Data Benar")
            } else {
                presenter.updateTambahrek(Constant.TAMBAHREK_ID, jenis_bank.toString(), norek.toString(), nama.toString())
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

    override fun onResultDetailTambahrek(responseTambahrekDetail: ResponseTambahrekDetail) {

        val tambahrek = responseTambahrekDetail.dataTambahrek

        edtJenisBank.setText( tambahrek.jenis_bank )
        edtNorek.setText( tambahrek.norek )
        edtAtasnama.setText( tambahrek.nama )

//        GlideHelper.setImage(this, "http://192.168.43.224/api_spesialisJB/public/"+ material.gambar!!, imvImageMaterial)
    }

    override fun onResultUpdateTambahrek(responseTambahrekUpdate: ResponseTambahrekUpdate) {
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