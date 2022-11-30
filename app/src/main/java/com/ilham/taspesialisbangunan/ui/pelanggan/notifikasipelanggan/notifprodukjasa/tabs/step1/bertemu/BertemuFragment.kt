package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.dikonfirmasi

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
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.MenungguAdapter

class DikonfirmasiFragment : Fragment(), DikonfirmasiContract.View {

    lateinit var presenter: DikonfirmasiPresenter
    lateinit var dikonfirmasiAdapter: MenungguAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDikonfirmasi: RecyclerView
    lateinit var swipeDikonfirmasi: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dikonfirmasi, container, false)

        presenter = DikonfirmasiPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefsIsLogin) {
            presenter.getPengajuandikonfirmasi(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDikonfirmasi = view.findViewById(R.id.rcvDikonfirmasi)
        swipeDikonfirmasi = view.findViewById(R.id.swipeDikonfirmasi)

        dikonfirmasiAdapter = MenungguAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
//            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

            when (type) {
                "Delete" -> showDialogDelete( dataPengajuan, position )
            }

        }

        rcvDikonfirmasi.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dikonfirmasiAdapter
        }

        swipeDikonfirmasi.setOnRefreshListener {
            if (prefsManager.prefsIsLogin) {
                presenter.getPengajuandikonfirmasi(prefsManager.prefsId.toLong())
            }
        }
    }

    override fun onLoadingPengajuandikonfirmasi(loading: Boolean) {
        when (loading) {
            true -> swipeDikonfirmasi.isRefreshing = true
            false -> swipeDikonfirmasi.isRefreshing = false
        }
    }

    override fun onResultPengajuandikonfirmasi(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        dikonfirmasiAdapter.setData(pengajuan)
    }

    override fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage( responsePengajuanUpdate.msg )
    }

    override fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataPengajuan.bukti}?")

        dialog.setPositiveButton ("Hapus") { dialog, which ->
            presenter.deletePengajuanDikonfirmasi(Constant.PENGAJUAN_ID)
            dikonfirmasiAdapter.removePengajuanmenunggu(position)
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