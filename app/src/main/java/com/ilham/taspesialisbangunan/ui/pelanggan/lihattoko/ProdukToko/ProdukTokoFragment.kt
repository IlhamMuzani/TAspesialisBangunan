package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.ProdukToko

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.MenungguAdapter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.KategoriToko.PembuatanKanopi.PembuatanKanopiPresenter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserAdapter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserPresenter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap.ProdukMapdetailActivity
import im.delight.android.location.SimpleLocation

class ProdukTokoFragment : Fragment(), ProdukTokoContract.View, OnMapReadyCallback {


    lateinit var presenter: ProdukTokoPresenter
    lateinit var produkuserAdapter: ProdukuserAdapter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var rcvProduk1: RecyclerView
    lateinit var swipeproduk1: SwipeRefreshLayout
    lateinit var EdtSearch1: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_produktoko, container, false)

        presenter = ProdukTokoPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduk1(Constant.USERJASA_ID)

    }

    override fun initFragment(view: View) {

        rcvProduk1 = view.findViewById(R.id.rcvProduk1)
        swipeproduk1 = view.findViewById(R.id.swipeProduct1)
        EdtSearch1 = view.findViewById(R.id.edtSearch1)

        produkuserAdapter = ProdukuserAdapter(
            requireActivity(),
            arrayListOf()
        ) { dataProduk: DataProduk, position: Int, type: String ->
            Constant.PRODUK_ID = dataProduk.id!!

            produk = dataProduk

        }

        rcvProduk1.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produkuserAdapter
        }

        swipeproduk1.setOnRefreshListener {
            presenter.getProduk1(produk.kd_user!!.toLong())
        }


        EdtSearch1.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                presenter.searchProduk1(EdtSearch1.text.toString())

                true
            } else {
                false
            }
        }

    }

    override fun onLoadingProduk1(loading: Boolean) {
        when (loading) {
            true -> swipeproduk1.isRefreshing = true
            false -> swipeproduk1.isRefreshing = false
        }
    }

    override fun onResultProduk1(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
        produkuserAdapter.setData(dataProduk)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(produk.latitude!!.toDouble(), produk.longitude!!.toDouble())
        googleMap.addMarker(MarkerOptions().position(latLng).title(produk.nama_toko))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage1(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}