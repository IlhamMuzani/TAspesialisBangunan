package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk.ProdukCreateActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update.ProdukUpdateActivity
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper

class ProdukFragment : Fragment(), ProdukContract.View, OnMapReadyCallback {

    lateinit var presenter: ProdukPresenter
    lateinit var produkAdapter: ProdukAdapter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var rcvProdukjasa: RecyclerView
    lateinit var swipejasa: SwipeRefreshLayout
//    lateinit var Fab: FloatingActionButton
    lateinit var Fab: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_produkjasa, container, false)

        presenter = ProdukPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        if(prefsManager.prefsIsLogin) {
            presenter.getProduk(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()
        MapsHelper.permissionMap(requireContext(),requireActivity())

        rcvProdukjasa = view.findViewById(R.id.rcvProduk)
        swipejasa = view.findViewById(R.id.swipe)
        Fab = view.findViewById(R.id.fabjasa)

        produkAdapter = ProdukAdapter(requireActivity(), arrayListOf()) {
                dataProduk: DataProduk, position: Int, type: String ->

            produk = dataProduk

            when (type) {
                "Update" -> startActivity(Intent(requireActivity(), ProdukUpdateActivity::class.java))
                "Delete" -> showDialogDelete( dataProduk, position )
            }
        }

        rcvProdukjasa.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produkAdapter
        }

        swipejasa.setOnRefreshListener {
            presenter.getProduk(prefsManager.prefsId.toLong())
        }

        Fab.setOnClickListener { view ->
            startActivity(Intent(requireActivity(), ProdukCreateActivity::class.java))
        }
    }

    override fun onLoadingProduk(loading: Boolean) {
        when (loading) {
            true -> swipejasa.isRefreshing = true
            false -> swipejasa.isRefreshing = false
        }
    }

    override fun onResultProduk(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
        produkAdapter.setData(dataProduk)
    }

    override fun onResultDelete(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage( responseProdukUpdate. msg )
    }

    override fun showDialogDelete(dataProduk: DataProduk, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Hapus ${produk.nama_toko}?" )

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deleteProduk( Constant.PRODUK_ID )
            produkAdapter.removeProduk( position )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produk.latitude!!.toDouble(), produk.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produk.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}
