package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.notifprodukjasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.menunggu.MenungguFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.dp.DPFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step3.selesai.SelesaiFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_notifikasi_pelanggan.*
import kotlinx.android.synthetic.main.activity_notifikasi_pelanggan.btn_tabsNotif
import kotlinx.android.synthetic.main.activity_notifikasi_pelanggan.btn_viepagerNotif
import kotlinx.android.synthetic.main.fragment_notifpjasafragment.*
import kotlinx.android.synthetic.main.toolbar.*
import org.w3c.dom.Text

class NotifPjasaFragment : Fragment(), NotifPjasaContract.View {

    lateinit var viewpager : ViewPager
    lateinit var btn_tabs : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifpjasafragment, container, false)

        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        viewpager = view.findViewById(R.id.btn_viepagerNotifP)
        btn_tabs = view.findViewById(R.id.btn_tabsNotifP)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(MenungguFragment(), "Tunggu")
        adapter.addFragment(DPFragment(), "Proses")
//        adapter.addFragment(DiterimaFragment(), "Step 2")
//        adapter.addFragment(DiprosesFragment(), "Proses")
        adapter.addFragment(SelesaiFragment(), "Selesai")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)
    }

}