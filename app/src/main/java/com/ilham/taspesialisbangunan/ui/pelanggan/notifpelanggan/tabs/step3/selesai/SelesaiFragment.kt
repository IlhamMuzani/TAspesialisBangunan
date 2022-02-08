package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step3.selesai

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
        presenter.getPengajuanselesai(prefsManager.prefsId.toLong())
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvSelesai = view.findViewById(R.id.rcvSelesai)
        swipeSelesai = view.findViewById(R.id.swipeSelesai)

        selesaiAdapter = SelesaiAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvSelesai.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selesaiAdapter
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

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}