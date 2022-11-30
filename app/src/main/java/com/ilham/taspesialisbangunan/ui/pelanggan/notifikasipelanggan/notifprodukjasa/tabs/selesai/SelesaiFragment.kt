package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate

class SelesaiFragment : Fragment(), SelesaiContract.View {

   lateinit var presenter: SelesaiPresenter
    lateinit var selesaiAdapter: SelesaiAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvSelesai: RecyclerView
    lateinit var swipeSelesai: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selesai, container, false)

        presenter = SelesaiPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefsIsLogin) {
            presenter.getPengajuanselesai(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvSelesai = view.findViewById(R.id.rcvSelesai)
        swipeSelesai = view.findViewById(R.id.swipeSelesai)

        selesaiAdapter = SelesaiAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
//            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

            when (type) {
                "Delete" -> showDialogHistori( dataPengajuan, position )
            }
        }

        rcvSelesai.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selesaiAdapter
        }

        swipeSelesai.setOnRefreshListener {
            presenter.getPengajuanselesai(prefsManager.prefsId.toLong())
        }
    }

    override fun onLoadingPengajuanselesai(loading: Boolean) {
        when (loading) {
            true -> swipeSelesai.isRefreshing = true
            false -> swipeSelesai.isRefreshing = false
        }
    }

    override fun onResultPengajuanselesai(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        selesaiAdapter.setData(pengajuan)
    }

    override fun onResultSelesaikeHistori(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage( responsePengajuanUpdate.msg )
    }

    override fun showDialogHistori(dataPengajuan: DataPengajuan, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus?")

        dialog.setPositiveButton ("Hapus") { dialog, which ->
            presenter.pengajuanselesaikehistori(Constant.PENGAJUAN_ID)
            selesaiAdapter.removePengajuanselesai(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}