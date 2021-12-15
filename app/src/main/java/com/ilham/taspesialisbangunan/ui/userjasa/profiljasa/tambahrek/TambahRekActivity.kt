package com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.create.TambahrekCreateActivity
import com.ilham.taspesialisbangunan.ui.userjasa.profiljasa.tambahrek.update.TambahrekUpdateActivity
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper
import kotlinx.android.synthetic.main.activity_tambah_rek.*
import kotlinx.android.synthetic.main.content_tambahrek.*

class TambahRekActivity : AppCompatActivity(), TambahrekContract.View {

    lateinit var presenter: TambahrekPresenter
    lateinit var tambahrekAdapter: TambahrekAdapter
    lateinit var tambahrek: DataTambahrek
    lateinit var prefsManager: PrefsManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_rek)
        presenter = TambahrekPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getTambahrek(prefsManager.prefsId)
    }

    override fun initActivity() {
        supportActionBar!!.title = "No Rekening"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        MapsHelper.permissionMap(this, this)
    }

    override fun initListener() {

        tambahrekAdapter = TambahrekAdapter(this, arrayListOf()){
                dataTambahrek: DataTambahrek, position: Int, type: String ->

            tambahrek = dataTambahrek

            when (type ){
                "update" -> startActivity(Intent(this, TambahrekUpdateActivity::class.java))
                "delete" -> showDialogDelete( dataTambahrek, position )
            }
        }

        rcvTambahrek.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tambahrekAdapter
        }

        swipeTambahrek.setOnRefreshListener {
            presenter.getTambahrek(prefsManager.prefsId)
        }

        fabTambahrek.setOnClickListener { view ->
            startActivity(Intent(this, TambahrekCreateActivity::class.java))
        }
    }

    override fun onLoadingTambahrek(loading: Boolean) {
        when(loading){
            true -> swipeTambahrek.isRefreshing = true
            false -> swipeTambahrek.isRefreshing = false
        }
    }

    override fun onResultTambahrek(responseTambahrekList: ResponseTambahrekList) {
        val dataTambahrek: List<DataTambahrek> = responseTambahrekList.dataTambahrek
        tambahrekAdapter.setData(dataTambahrek)
    }

    override fun onResultDelete(responseTambahrekUpdate: ResponseTambahrekUpdate) {
        showMessage( responseTambahrekUpdate.msg )
    }

    override fun showDialogDelete(dataTambahrek: DataTambahrek, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Hapus ${tambahrek.jenis_bank}?" )

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deleteTambahrek( Constant.TAMBAHREK_ID )
            tambahrekAdapter.removeTambahrek(position)
            dialog.dismiss()

        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
