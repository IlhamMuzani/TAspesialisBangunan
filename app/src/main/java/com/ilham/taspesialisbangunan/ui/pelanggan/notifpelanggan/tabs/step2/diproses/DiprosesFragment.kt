package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.diproses

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

class DiprosesFragment : Fragment(), DiprosesContract.View {

    lateinit var presenter: DiprosesPresenter
    lateinit var diprosesAdapter: DiprosesAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDiproses: RecyclerView
    lateinit var swipeDiproses: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diproses, container, false)

        presenter = DiprosesPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuandiproses(prefsManager.prefsId.toLong())
    }

    override fun initFragment(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDiproses = view.findViewById(R.id.rcvDiproses)
        swipeDiproses = view.findViewById(R.id.swipeDiproses)

        diprosesAdapter = DiprosesAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.kd_pengajuan!!

            datapengajuan = dataPengajuan

        }

        rcvDiproses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = diprosesAdapter
        }
    }

    override fun onLoadingPengajuandiproses(loading: Boolean) {
        when (loading) {
            true -> swipeDiproses.isRefreshing = true
            false -> swipeDiproses.isRefreshing = false
        }
    }

    override fun onResultPengajuandiproses(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        diprosesAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}