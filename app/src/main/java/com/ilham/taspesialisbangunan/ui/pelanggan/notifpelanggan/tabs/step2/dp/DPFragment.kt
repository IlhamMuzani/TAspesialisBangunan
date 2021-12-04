package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.dp

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
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.DPAdapter

class DPFragment : Fragment(), DPContract.View {

    lateinit var presenter: DPPresenter
    lateinit var dpAdapter: DPAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDP: RecyclerView
    lateinit var swipeDP: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_dp, container, false)

        presenter = DPPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuandp(prefsManager.prefsId.toLong())
    }

    override fun initFragment(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDP = view.findViewById(R.id.rcvDp)
        swipeDP = view.findViewById(R.id.swipeDp)

        dpAdapter = DPAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.kd_pengajuan!!

            datapengajuan = dataPengajuan

        }

        rcvDP.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dpAdapter
        }
    }

    override fun onLoadingPengajuanDP(loading: Boolean) {
        when (loading) {
            true -> swipeDP.isRefreshing = true
            false -> swipeDP.isRefreshing = false
        }
    }

    override fun onResultPengajuanDP(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        dpAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}