package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.produkjasa.tabjasa.dp

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

class DPjasaFragment : Fragment(), DPjasaContract.View {

    lateinit var presenter: DPjasaPresenter
    lateinit var dpjasaAdapter: MenunggujasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDpjasa: RecyclerView
    lateinit var swipeDpjasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dp, container, false)

        presenter = DPjasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanDP(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDpjasa = view.findViewById(R.id.rcvDpjasa)
        swipeDpjasa = view.findViewById(R.id.swipeDpjasa)

        dpjasaAdapter = MenunggujasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvDpjasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dpjasaAdapter
        }

        swipeDpjasa.setOnRefreshListener {
            presenter.getPengajuanDP(prefsManager.prefsId)
        }
    }

    override fun onLoadingPengajuanDP(loading: Boolean) {
        when (loading) {
            true -> swipeDpjasa.isRefreshing = true
            false -> swipeDpjasa.isRefreshing = false
        }
    }

    override fun onResultPengajuanDP(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        dpjasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}