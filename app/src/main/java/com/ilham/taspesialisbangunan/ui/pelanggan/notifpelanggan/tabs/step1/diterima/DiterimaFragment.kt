package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.diterima

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1

class DiterimaFragment : Fragment(), DiterimaContract.View {

    lateinit var presenter: DiterimaPresenter
    lateinit var diterimaAdapter: DiterimaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDiterima: RecyclerView
    lateinit var swipeDiterima: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diterima, container, false)

        presenter = DiterimaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanditerima(prefsManager.prefsId.toLong())
    }

    override fun initFragment(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDiterima = view.findViewById(R.id.rcvDiterima)
        swipeDiterima = view.findViewById(R.id.swipeDiterima)

        diterimaAdapter = DiterimaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.kd_pengajuan!!

            datapengajuan = dataPengajuan

        }

        rcvDiterima.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = diterimaAdapter
        }
    }

    override fun onLoadingPengajuanditerima(loading: Boolean) {
        when (loading) {
            true -> swipeDiterima.isRefreshing = true
            false -> swipeDiterima.isRefreshing = false
        }
    }

    override fun onResultPengajuanditerima(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        diterimaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}