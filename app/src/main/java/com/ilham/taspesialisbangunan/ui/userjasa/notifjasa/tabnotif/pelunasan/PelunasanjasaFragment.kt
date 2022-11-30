package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.pelunasan

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

class PelunasanjasaFragment : Fragment(), PelunasanjasaContract.View {

    lateinit var presenter: PelunasanjasaPresenter
    lateinit var pelunasanjasaAdapter: MenunggujasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvPelunasanjasa: RecyclerView
    lateinit var swipePelunasanjasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pelunasanjasa, container, false)

        presenter = PelunasanjasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanPelunasan(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvPelunasanjasa = view.findViewById(R.id.rcvPelunasanjasa)
        swipePelunasanjasa = view.findViewById(R.id.swipePelunasanjasa)

        pelunasanjasaAdapter = MenunggujasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvPelunasanjasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pelunasanjasaAdapter
        }

        swipePelunasanjasa.setOnRefreshListener {
            presenter.getPengajuanPelunasan(prefsManager.prefsId)
        }
    }

    override fun onLoadingPengajuanPelunasan(loading: Boolean) {
        when (loading) {
            true -> swipePelunasanjasa.isRefreshing = true
            false -> swipePelunasanjasa.isRefreshing = false
        }
    }

    override fun onResultPengajuanPelunasan(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        pelunasanjasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}