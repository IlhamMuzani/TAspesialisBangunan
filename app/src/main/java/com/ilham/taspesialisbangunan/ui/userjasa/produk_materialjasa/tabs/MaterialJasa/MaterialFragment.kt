package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk.ProdukMCreateActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update.ProdukUpdateActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper

class MaterialFragment : Fragment(), MaterialContract.View, OnMapReadyCallback {

    lateinit var presenter: MaterialPresenter
    lateinit var materialAdapter: MaterialAdapter
    lateinit var dataproduk: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var rcvProdukM: RecyclerView
    lateinit var swipeM: SwipeRefreshLayout
    lateinit var Fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_materialjasa, container, false)

        presenter = MaterialPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragmentM(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukMat(prefsManager.prefsId.toLong())
    }

    override fun initFragmentM(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        MapsHelper.permissionMap(requireContext(),requireActivity())

        rcvProdukM = view.findViewById(R.id.rcvMaterial)
        swipeM = view.findViewById(R.id.swipeMJ)
        Fab = view.findViewById(R.id.fabMaterial)

        materialAdapter = MaterialAdapter(requireActivity(), arrayListOf()) {
                dataProduk: DataProduk, position: Int, type: String ->

            dataproduk = dataProduk

            when (type) {
                "Update" -> startActivity(Intent(requireActivity(), ProdukUpdateActivity::class.java))
                "Delete" -> showDialogDeleteM( dataProduk, position )

            }
        }

        rcvProdukM.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialAdapter
        }

        swipeM.setOnRefreshListener {
            presenter.getProdukMat(prefsManager.prefsId.toLong())
        }

        Fab.setOnClickListener { view ->
            startActivity(Intent(requireActivity(), ProdukMCreateActivity::class.java))
        }
    }

    override fun onLoadingProdukM(loading: Boolean) {
        when (loading) {
            true -> swipeM.isRefreshing = true
            false -> swipeM.isRefreshing = false
        }
    }

    override fun onResultProdukM(responseProdukList: ResponseProdukList) {
        val produk: List<DataProduk> = responseProdukList.dataProduk
        materialAdapter.setData(produk)
    }

    override fun onResultDeleteM(responseProdukUpdate: ResponseProdukUpdate) {
        showMessage( responseProdukUpdate. msg )
    }

    override fun showDialogDeleteM(dataProduk: DataProduk, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Hapus ${dataProduk.nama_toko}?" )

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deleteProduk( Constant.PRODUK_ID )
            materialAdapter.removeProduk( position )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (dataproduk.latitude!!.toDouble(), dataproduk.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( dataproduk.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}
