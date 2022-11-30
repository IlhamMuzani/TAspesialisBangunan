package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.bertemu

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

class BertemuFragment : Fragment(), BertemuContract.View {

    lateinit var presenter: BertemuPresenter
    lateinit var dikonfirmasiAdapter: MenungguAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvBertemu: RecyclerView
    lateinit var swipeBertemu: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bertemu, container, false)

        presenter = BertemuPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefsIsLogin) {
            presenter.getPengajuanbertemu(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvBertemu = view.findViewById(R.id.rcvBertemu)
        swipeBertemu = view.findViewById(R.id.swipeBertemu)

        dikonfirmasiAdapter = MenungguAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
//            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

            when (type) {
                "Delete" -> showDialogDelete( dataPengajuan, position )
            }

        }

        rcvBertemu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dikonfirmasiAdapter
        }

        swipeBertemu.setOnRefreshListener {
            if (prefsManager.prefsIsLogin) {
                presenter.getPengajuanbertemu(prefsManager.prefsId.toLong())
            }
        }
    }

    override fun onLoadingPengajuanbertemu(loading: Boolean) {
        when (loading) {
            true -> swipeBertemu.isRefreshing = true
            false -> swipeBertemu.isRefreshing = false
        }
    }

    override fun onResultPengajuanbertemu(responsePengajuanList1: ResponsePengajuanList1) {
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
            presenter.deletePengajuanbertemu(Constant.PENGAJUAN_ID)
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