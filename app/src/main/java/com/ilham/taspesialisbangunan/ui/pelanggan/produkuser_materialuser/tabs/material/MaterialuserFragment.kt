package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
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
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList

class MaterialuserFragment : Fragment(), MaterialuserContract.View, OnMapReadyCallback {

    lateinit var presenter: MaterialuserPresenter
    lateinit var materialuserAdapter: MaterialuserAdapter
    lateinit var produkmaterial: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var rcvMaterial: RecyclerView
    lateinit var swipe: SwipeRefreshLayout
    lateinit var EdtSearchMaterial: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_material, container, false)

        presenter = MaterialuserPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterial()
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvMaterial = view.findViewById(R.id.rcvMaterialM)
        swipe = view.findViewById(R.id.swipeM)
        EdtSearchMaterial = view.findViewById(R.id.edtSearchMaterial)

        materialuserAdapter = MaterialuserAdapter(requireActivity(), arrayListOf()){
                dataProdukM: DataProduk, position: Int, type: String ->
            Constant.MATERIAL_ID = dataProdukM.id!!

            produkmaterial = dataProdukM

            when (type ){
            }
        }

        EdtSearchMaterial.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchMaterial(EdtSearchMaterial.text.toString() )
                true
            } else {
                false
            }
        }

        rcvMaterial.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialuserAdapter
        }

        swipe.setOnRefreshListener {
            presenter.getMaterial()
        }
    }

    override fun onLoadingMaterialUser(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultMaterialUser(responseProdukList: ResponseProdukList) {
        val dataProdukmaterial: List<DataProduk> = responseProdukList.dataProduk
        materialuserAdapter.setData(dataProdukmaterial)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produkmaterial.latitude!!.toDouble(), produkmaterial.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produkmaterial.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}