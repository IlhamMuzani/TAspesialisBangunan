package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.selesai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.menunggu.MenunggujasaAdapter

class SelesaijasaFragment : Fragment(), SelesaijasaContract.View {

    lateinit var presenter: SelesaijasaPresenter
    lateinit var selesaijasaAdapter: SelesaijasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvSelesaijasa: RecyclerView
    lateinit var swipeSelesaijasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selesaijasa, container, false)

        presenter = SelesaijasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanSelesai(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvSelesaijasa = view.findViewById(R.id.rcvSelesaijasa)
        swipeSelesaijasa = view.findViewById(R.id.swipeSelesaijasa)

        selesaijasaAdapter = SelesaijasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvSelesaijasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selesaijasaAdapter
        }

        swipeSelesaijasa.setOnRefreshListener {
            presenter.getPengajuanSelesai(prefsManager.prefsId)
        }
    }

    override fun onLoadingPengajuanSelesai(loading: Boolean) {
        when (loading) {
            true -> swipeSelesaijasa.isRefreshing = true
            false -> swipeSelesaijasa.isRefreshing = false
        }
    }

    override fun onResultPengajuanSelesai(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        selesaijasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}