package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide.init
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
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap.ProdukMapdetailActivity
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_produk_create.*

class ProdukuserFragment : Fragment(), ProdukuserContract.View, OnMapReadyCallback {

    lateinit var simpleLocation: SimpleLocation
    lateinit var strLokasi: String
    lateinit var presenter: ProdukuserPresenter
    lateinit var produkuserAdapter: ProdukuserAdapter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var rcvProduk: RecyclerView
    lateinit var swipeproduk: SwipeRefreshLayout
    lateinit var EdtSearch: EditText
    lateinit var SPinnerPilihproduk: Spinner
    lateinit var imvLihatLocation: ImageView

    var strlatitude = 0.0
    var strlongitude = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_produk, container, false)

        presenter = ProdukuserPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view

        simpleLocation = SimpleLocation(requireContext())
        if (!simpleLocation.hasLocationEnabled()){
            SimpleLocation.openSettings(requireContext())
        }

        strlatitude = simpleLocation.latitude
        strlongitude = simpleLocation.longitude

        strLokasi ="${strlatitude},${strlongitude}"


    }

    override fun onStart() {
        super.onStart()
        presenter.getProduk()
        spinnercategory()
    }

    override fun onResume() {
        super.onResume()
        spinnercategory()

    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.JENISPEMBUATAN_NAME = "Pembuatan"
    }

    override fun initFragment(view: View) {

        SPinnerPilihproduk = view.findViewById(R.id.categorijenisproduk)
        rcvProduk = view.findViewById(R.id.rcvProduk)
        swipeproduk = view.findViewById(R.id.swipeProduct)
        EdtSearch = view.findViewById(R.id.edtSearch)
        imvLihatLocation = view.findViewById(R.id.imvlocation)

        produkuserAdapter = ProdukuserAdapter(requireActivity(), arrayListOf()){
                dataProduk: DataProduk, position: Int, type: String ->
            Constant.PRODUK_ID = dataProduk.id!!

            produk = dataProduk

        }

        rcvProduk.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produkuserAdapter
        }

        swipeproduk.setOnRefreshListener {
            presenter.getProduk()
        }


        EdtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                presenter.searchProduk(EdtSearch.text.toString() )

                true
            } else {
                false
            }
        }

        imvLihatLocation.setOnClickListener {
            startActivity(Intent(requireContext(), ProdukMapdetailActivity::class.java))
        }

    }

    override fun onLoadingProduk(loading: Boolean) {
        when (loading) {
            true -> swipeproduk.isRefreshing = true
            false -> swipeproduk.isRefreshing = false
        }
    }

    override fun onResultProduk(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
        produkuserAdapter.setData(dataProduk)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (produk.latitude!!.toDouble(), produk.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produk.user.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    fun spinnercategory() {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kategori")
        arrayString.add("Pembuatan Pagar")
        arrayString.add("Pembuatan Kanopi")
        arrayString.add("Pembuatan Tangga")
        arrayString.add("Pembuatan Tralis")
        arrayString.add("Pembuatan Halaman")

        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SPinnerPilihproduk.adapter = adapter
        val selection = adapter.getPosition(Constant.JENISPEMBUATAN_NAME)
        SPinnerPilihproduk.setSelection(selection)
        SPinnerPilihproduk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.JENISPEMBUATAN_ID = 0
                        Constant.JENISPEMBUATAN_NAME = "Pilih Kategori"
                        presenter.searchProduk("Pembuatan" )
                    }
                    1 -> {
                        Constant.JENISPEMBUATAN_ID = 1
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Pagar"
                        presenter.searchProduk("Pembuatan Pagar" )
                    }
                    2 -> {
                        Constant.JENISPEMBUATAN_ID = 2
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Kanopi"
                        presenter.searchProduk("Pembuatan Kanopi" )
                    }
                    3 -> {
                        Constant.JENISPEMBUATAN_ID = 3
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tangga"
                        presenter.searchProduk("Pembuatan Tangga" )
                    }
                    4 -> {
                        Constant.JENISPEMBUATAN_ID = 4
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Tralis"
                        presenter.searchProduk("Pembuatan Tralis" )
                    }
                    5 -> {
                        Constant.JENISPEMBUATAN_ID = 5
                        Constant.JENISPEMBUATAN_NAME = "Pembuatan Halaman"
                        presenter.searchProduk("Pembuatan Halaman" )
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}