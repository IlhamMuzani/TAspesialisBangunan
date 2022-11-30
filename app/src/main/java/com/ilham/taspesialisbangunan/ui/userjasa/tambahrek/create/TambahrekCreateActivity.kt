package com.ilham.taspesialisbangunan.ui.userjasa.tambahrek.create

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import kotlinx.android.synthetic.main.activity_produk_create.*
import kotlinx.android.synthetic.main.activity_tambahrek_create.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class TambahrekCreateActivity : AppCompatActivity(), TambahrekCreateContract.View {


    lateinit var prefsManager: PrefsManager
    lateinit var presenter: TambahrekCreatePresenter

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


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

    @SuppressLint("SetTextI18n")
    override fun initActivity() {
        tv_bgjasa.text="Tambah Rekening"

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        spinnerrekening()

        ivKembalijasa.setOnClickListener {
            onBackPressed()
        }

        BTN_savetambahrek.setOnClickListener {
            if (Constant.BANK_ID == 0){
                showMessage("Pilih Kategori Bank")
            }else if (edtNorek.text!!.isEmpty()){
                edtNorek.error = "Nomer Rekening Tidak Boleh Kosong"
                edtNorek.requestFocus()
            } else if (edtAtasnama.text!!.isEmpty()){
                edtAtasnama.error = "Masukan Nama Lengkap"
                edtAtasnama.requestFocus()
            } else {
                presenter.insertTambahrek(prefsManager.prefsId, Constant.BANK_NAME, edtNorek.text.toString(), edtAtasnama.text.toString())
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
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

    fun spinnerrekening() {

        val arrayString = java.util.ArrayList<String>()
        arrayString.add("Pilih Bank")
        arrayString.add("Bank Mandiri")
        arrayString.add("Bank BRI")
        arrayString.add("Bank BNI")
        arrayString.add("Bank BCA")

        val adapter = ArrayAdapter(this, R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edtJenisBank.adapter = adapter
        val selection = adapter.getPosition(Constant.BANK_NAME)
        edtJenisBank.setSelection(selection)
        edtJenisBank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.BANK_ID = 0
                        Constant.BANK_NAME = "Pilih Bank"
                    }
                    1 -> {
                        Constant.BANK_ID = 1
                        Constant.BANK_NAME = "Bank Mandiri"
                    }
                    2 -> {
                        Constant.BANK_ID = 2
                        Constant.BANK_NAME = "Bank BRI"
                    }
                    3 -> {
                        Constant.BANK_ID = 3
                        Constant.BANK_NAME = "Bank BNI"
                    }
                    4 -> {
                        Constant.BANK_ID = 4
                        Constant.BANK_NAME = "Bank BCA"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


}