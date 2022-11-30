package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dikonfirmasi

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
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.menunggu.MenunggujasaAdapter

class DikonfirmasijasaFragment : Fragment(), DikonfirmasijasaContract.View {

    lateinit var presenter: DikonfirmasijasaPresenter
    lateinit var dikonfirmasijasaAdapter: MenunggujasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvDikonfirmasijasa: RecyclerView
    lateinit var swipeDikonfirmasijasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dikonfirmasijasa, container, false)

        presenter = DikonfirmasijasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuandikonfirmasi(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvDikonfirmasijasa = view.findViewById(R.id.rcvDikonfirmasijasa)
        swipeDikonfirmasijasa = view.findViewById(R.id.swipeDikonfirmasijasa)

        dikonfirmasijasaAdapter = MenunggujasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvDikonfirmasijasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dikonfirmasijasaAdapter
        }

        swipeDikonfirmasijasa.setOnRefreshListener {
            presenter.getPengajuandikonfirmasi(prefsManager.prefsId)
        }
    }

    override fun onLoadingPengajuandikonfirmasi(loading: Boolean) {
        when (loading) {
            true -> swipeDikonfirmasijasa.isRefreshing = true
            false -> swipeDikonfirmasijasa.isRefreshing = false
        }
    }

    override fun onResultPengajuandikonfirmasi(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        dikonfirmasijasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}