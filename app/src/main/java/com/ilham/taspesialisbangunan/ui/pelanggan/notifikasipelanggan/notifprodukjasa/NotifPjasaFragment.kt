package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.pelunasan.PelunasanFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.menunggu.MenungguFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step2.dp.DPFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai.SelesaiFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.bertemu.BertemuFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.dikonfirmasi.DikonfirmasiFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.diterima.DiterimaFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step2.diproses.DiprosesFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter

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
        adapter.addFragment(MenungguFragment(), "Menunggu")
        adapter.addFragment(DikonfirmasiFragment(), "Dikonfirmasi")
        adapter.addFragment(BertemuFragment(), "Bertemu")
        adapter.addFragment(DiterimaFragment(), "Diterima")
        adapter.addFragment(DPFragment(), "Bayar")
        adapter.addFragment(DiprosesFragment(), "Dikerjakan")
        adapter.addFragment(PelunasanFragment(), "Pelunasan")
        adapter.addFragment(SelesaiFragment(), "Selesai")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)

        btn_tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_jam)
        btn_tabs.getTabAt(1)!!.setIcon(R.drawable.dikonfirmasi2)
        btn_tabs.getTabAt(2)!!.setIcon(R.drawable.bertemu)
        btn_tabs.getTabAt(3)!!.setIcon(R.drawable.diterimaa)
        btn_tabs.getTabAt(4)!!.setIcon(R.drawable.dp1)
        btn_tabs.getTabAt(5)!!.setIcon(R.drawable.diproses2)
        btn_tabs.getTabAt(6)!!.setIcon(R.drawable.pelunasan)
        btn_tabs.getTabAt(7)!!.setIcon(R.drawable.done)
    }

}