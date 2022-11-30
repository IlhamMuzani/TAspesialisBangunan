package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.Toko

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.ProdukToko.ProdukTokoPresenter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserAdapter
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_lihattoko.*
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.activity_ubah_profiljasa.*

class TokoFragment : Fragment(), TokoContract.View, OnMapReadyCallback {

    lateinit var presenter: TokoPresenter
    lateinit var produk: DataProduk
    lateinit var jasa: DataUser
    lateinit var sLoading:SweetAlertDialog


    lateinit var progresToko: ProgressBar
    lateinit var nama: TextView
    lateinit var kecamatan: TextView
    lateinit var kelurahan: TextView
    lateinit var rt: TextView
    lateinit var phone: TextView
    lateinit var deskripsi: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_toko, container, false)
        presenter = TokoPresenter(this)

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
//        presenter.getNameToko(Constant.USERJASA_ID)
        presenter.getDetailProfiljasa(Constant.USERJASA_ID.toString())

    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        nama = view.findViewById(R.id.namadetailtoko)
//        kecamatan = view.findViewById(R.id.kecamatantoko)
        kelurahan = view.findViewById(R.id.kelurahantoko)
        rt = view.findViewById(R.id.RtrwToko)
        deskripsi = view.findViewById(R.id.deskripsiToko)
        phone = view.findViewById(R.id.phoneToko)
//        progresToko = view.findViewById(R.id.progrestoko)

    }

    override fun onLoadingToko(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultDetailljasa(responseUserdetail: ResponseUserdetail) {
        jasa = responseUserdetail.user

        nama.text = jasa.username
        kelurahan.text = jasa.kelurahan
        rt.text = jasa.alamat
        phone.text = jasa.phone
        deskripsi.text = jasa.deskripsi

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapdetailtoko) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

//    override fun onResultToko(responseProdukDetail: ResponseProdukDetail) {
//        produk = responseProdukDetail.dataProduk
//
//        nama.text = produk.user.username
////        kecamatan.text = produk.user.kecamatan
//        kelurahan.text = produk.user.kelurahan
//        rt.text = produk.user.alamat
//        phone.text = produk.user.phone
//        deskripsi.text = produk.user.deskripsi
//
////        val mapFragment = requireFragmentManager().findFragmentById(R.id.mapdetailtoko) as SupportMapFragment
////        mapFragment.getMapAsync( this )
//
//        val mapFragment = childFragmentManager.findFragmentById(R.id.mapdetailtoko) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//    }

    override fun showMessage1(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (jasa.latitude!!.toDouble(), jasa.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( jasa.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}