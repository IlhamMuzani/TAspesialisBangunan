package com.ilham.taspesialisbangunan.ui.home.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.NotifikasiJasaActivity
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material.MaterialuserFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.produk.ProdukuserFragment
import com.ilham.taspesialisjasabangunan.ui.produkuser.ProdukMaterialJasaActivity

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: HomePresenter

    lateinit var btnviewpager: ViewPager
    lateinit var btntabs: TabLayout

    lateinit var layoutPelanggan: LinearLayout
    lateinit var layoutJasa: LinearLayout

    lateinit var CrvUsaha: CardView
    lateinit var Notif: CardView


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

    override fun initFragment(view: View) {

        btnviewpager = view.findViewById(R.id.btn_viepager)
        btntabs = view.findViewById(R.id.btn_tabs)
        layoutPelanggan = view.findViewById(R.id.layout_pelanggan)
        layoutJasa = view.findViewById(R.id.layout_jasa)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(ProdukuserFragment(), "Product")
        adapter.addFragment(MaterialuserFragment(), "Materials")
        btnviewpager.adapter = adapter
        btntabs.setupWithViewPager(btnviewpager)

        CrvUsaha = view.findViewById(R.id.crv_Usahajasa)
        Notif = view.findViewById(R.id.crv_notifikasi)

    }

    override fun onStart() {
        super.onStart()
        presenter.userDetail(prefsManager.prefsId)

    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading(loading: Boolean) {

    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.user.status == "pelanggan") {
            layoutPelanggan.visibility = View.VISIBLE
            layoutJasa.visibility = View.GONE
        } else {
            layoutPelanggan.visibility = View.GONE
            layoutJasa.visibility = View.VISIBLE

            CrvUsaha.setOnClickListener {
                startActivity(Intent(requireActivity(), ProdukMaterialJasaActivity::class.java))
            }

            Notif.setOnClickListener {
                startActivity(Intent(requireActivity(), NotifikasiJasaActivity::class.java))
            }
        }
    }
}