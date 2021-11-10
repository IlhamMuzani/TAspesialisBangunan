package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.notifikasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.DataSaran
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import kotlinx.android.synthetic.main.content_notifsaran.*

class NotifikasiActivity : AppCompatActivity(), NotifikasiContract.View {

    lateinit var presenter: NotifikasiPresenter
    lateinit var notifPengajuanAdapter: NotifPengajuanAdapter
    lateinit var notifSaranAdapter: NotifSaranAdapter
    lateinit var prefsManager: PrefsManager
    lateinit var pengajuan: DataPengajuan
    lateinit var saran: DataSaran

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)
        prefsManager = PrefsManager(this)
        presenter = NotifikasiPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getNotifPengajuan(prefsManager.prefsId)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Notifikasi"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun initListener() {

        notifPengajuanAdapter = NotifPengajuanAdapter(this, arrayListOf())
        {
            dataPengajuan: DataPengajuan, position: Int, type: String ->
            pengajuan = dataPengajuan
            when(type) {
                "konfirmasi" -> showDialogKonfirmasi(pengajuan, position)
                "tolak" -> showDialogTolak(pengajuan, position)
            }
        }

        rcvNotifPengajuan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notifPengajuanAdapter
        }

//        notifSaranAdapter = NotifSaranAdapter(this, arrayListOf())
//
//        rcvNotifPengajuan.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = notifSaranAdapter
//        }
//
//        btn_saranmasuk.setOnClickListener {
//            presenter.mysaran(prefsManager.prefsId)
//        }
//
//        btn_pengajuanmasuk.setOnClickListener {
//            presenter.mypengajuan(prefsManager.prefsId)
//        }

        swipeNotifpengajuan.setOnRefreshListener {
            presenter.mypengajuan(prefsManager.prefsId)
        }

    }

    override fun onLoadingNotifikasi(loading: Boolean) {

        when (loading) {
            true -> swipeNotifpengajuan.isRefreshing = true
            false -> swipeNotifpengajuan.isRefreshing = false
        }
    }

    override fun onResultNotifPengajuan(responsePengajuanList1: ResponsePengajuanList1) {
        val dataPengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        notifPengajuanAdapter.setData( dataPengajuan )
    }

    override fun onResultNotifSaran(responseSaranList: ResponseSaranList) {
        val dataSaran: List<DataSaran> = responseSaranList.dataSaran
        notifSaranAdapter.setData( dataSaran )
    }

    override fun showDialogKonfirmasi(dataPengajuan: DataPengajuan, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Yakin menerima pengajuan?" )

        dialog.setPositiveButton("Ya") { dialog, which ->
            presenter.insertPengajuanterima(dataPengajuan.kd_pengajuan!!)
            notifPengajuanAdapter.removePengajuan( position )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showDialogTolak(dataPengajuan: DataPengajuan, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Yakin tolak pengajuan?" )

        dialog.setPositiveButton("Ya") { dialog, which ->
            presenter.insertPengajuantolak( dataPengajuan.kd_pengajuan!!)
            notifPengajuanAdapter.removePengajuan( position )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResultPengajuanUpdate(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage(responsePengajuanUpdate.msg)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}