package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diterima

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

class DiterimajasaFragment : Fragment(), DiterimajasaContract.View {

    lateinit var presenter: DiterimajasaPresenter
    lateinit var diterimajasaAdapter: DiterimajasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDiterimajasa: RecyclerView
    lateinit var swipeDiterimajasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_diterimajasa, container, false)

        presenter = DiterimajasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanditerimajasa(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDiterimajasa = view.findViewById(R.id.rcvDiterimajasa)
        swipeDiterimajasa = view.findViewById(R.id.swipeDiterimajasa)

        diterimajasaAdapter = DiterimajasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvDiterimajasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = diterimajasaAdapter
        }
    }

    override fun onLoadingPengajuanditerima(loading: Boolean) {
        when (loading) {
            true -> swipeDiterimajasa.isRefreshing = true
            false -> swipeDiterimajasa.isRefreshing = false
        }
    }

    override fun onResultPengajuanditerima(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        diterimajasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}