package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.bertemu

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
import kotlinx.android.synthetic.main.fragment_bertemujasa.*

class BertemujasaFragment : Fragment(), BertemujasaContract.View {

    lateinit var presenter: BertemujasaPresenter
    lateinit var BertemujasaAdapter: MenunggujasaAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvbertemujasa: RecyclerView
    lateinit var swipebertemujasa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bertemujasa, container, false)

        presenter = BertemujasaPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getPengajuanbertemu(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvbertemujasa = view.findViewById(R.id.rcvBertemujasa)
        swipebertemujasa = view.findViewById(R.id.swipeBertemujasa)

        BertemujasaAdapter = MenunggujasaAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

        }

        rcvbertemujasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BertemujasaAdapter
        }

        swipebertemujasa.setOnRefreshListener {
            presenter.getPengajuanbertemu(prefsManager.prefsId)
        }
    }

    override fun onLoadingPengajuanbertemu(loading: Boolean) {
        when (loading) {
            true -> swipeBertemujasa.isRefreshing = true
            false -> swipeBertemujasa.isRefreshing = false
        }
    }

    override fun onResultPengajuanbertemu(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        BertemujasaAdapter.setData(pengajuan)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}