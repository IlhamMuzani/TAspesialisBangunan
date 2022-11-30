package com.ilham.taspesialisbangunan.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserFragment
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.Kategori.KategoriFragment
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukFragment
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ViewPagerAdapterProduk
import com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: HomePresenter
    lateinit var sLoading: SweetAlertDialog

    lateinit var layoutPelanggan: LinearLayout
    lateinit var btnviewpager: ViewPager
    lateinit var btntabs: TabLayout

    lateinit var layoutJasa: LinearLayout
    lateinit var btnviewpagerjasa: ViewPager
    lateinit var btntabsjasa: TabLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        presenter = HomePresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        //User
        btnviewpager = view.findViewById(R.id.btn_viepager)
        btntabs = view.findViewById(R.id.btn_tabs)
        layoutPelanggan = view.findViewById(R.id.layout_pelanggan)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(ProdukuserFragment(), "Sedia Layanan Bangunan")
        btnviewpager.adapter = adapter
        btntabs.setupWithViewPager(btnviewpager)

        btntabs.getTabAt(0)!!.setIcon(R.drawable.jasa)

        //Jasa
        btnviewpagerjasa = view.findViewById(R.id.btn_viepagerjasa)
        btntabsjasa = view.findViewById(R.id.btn_tabsjasa)
        layoutJasa = view.findViewById(R.id.layout_jasa)

        val adapterjasa = ViewPagerAdapterProduk(requireActivity().supportFragmentManager)
//        adapterjasa.addFragment(ProdukFragment(), "Toko Saya")
        adapterjasa.addFragment(ProdukFragment(), "Produk")
//        adapterjasa.addFragment(KategoriFragment(), "Kategori")
        btnviewpagerjasa.adapter = adapterjasa
        btntabsjasa.setupWithViewPager(btnviewpagerjasa)
        
    }

    override fun onStart() {
        super.onStart()
        presenter.userDetail(prefsManager.prefsId)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.user.status == "pelanggan") {
            layoutPelanggan.visibility = View.VISIBLE
            layoutJasa.visibility = View.GONE
        } else {
            layoutPelanggan.visibility = View.GONE
            layoutJasa.visibility = View.VISIBLE

        }
    }
}