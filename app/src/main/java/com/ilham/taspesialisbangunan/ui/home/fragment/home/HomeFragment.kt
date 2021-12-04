package com.ilham.taspesialisbangunan.ui.home.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material.MaterialuserFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.produk.ProdukuserFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: HomePresenter

    lateinit var btnviewpager: ViewPager
    lateinit var btntabs: TabLayout

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


        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(ProdukuserFragment(), "Produk")
        adapter.addFragment(MaterialuserFragment(), "Material")
        btnviewpager.adapter = adapter
        btntabs.setupWithViewPager(btnviewpager)

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
        if (responseUserdetail.user.status == "pelanggan"){
            layout_pelanggan.visibility = View.VISIBLE
            layout_jasa.visibility = View.GONE
        } else {
            layout_pelanggan.visibility = View.GONE
            layout_jasa.visibility = View.VISIBLE
        }
    }
}