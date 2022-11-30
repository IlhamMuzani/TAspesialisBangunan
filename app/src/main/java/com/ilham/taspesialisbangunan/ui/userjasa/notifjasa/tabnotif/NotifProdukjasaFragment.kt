package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.bertemu.BertemujasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dikonfirmasi.DikonfirmasijasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.diproses.DiprosesjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.diterima.DiterimajasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dp.DPjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.menunggu.MenunggujasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.pelunasan.PelunasanjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.selesai.SelesaijasaFragment
import com.nex3z.notificationbadge.NotificationBadge

class NotifProdukjasaFragment : Fragment(), NotifProdukJasaContract.View {

    lateinit var prefsManager: PrefsManager

    lateinit var viewpager : ViewPager
    lateinit var btn_tabs : TabLayout
    lateinit var btnbadge : NotificationBadge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifprodukjasa, container, false)

        prefsManager = PrefsManager(requireActivity())


        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        viewpager = view.findViewById(R.id.btn_viepagerNotifproduk)
        btn_tabs = view.findViewById(R.id.btn_tabsnotifproduk)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)

        adapter.addFragment(MenunggujasaFragment(), "")
        adapter.addFragment(DikonfirmasijasaFragment(), "")
        adapter.addFragment(BertemujasaFragment(), "")
        adapter.addFragment(DiterimajasaFragment(), "")
        adapter.addFragment(DPjasaFragment(), "")
        adapter.addFragment(DiprosesjasaFragment(), "")
        adapter.addFragment(PelunasanjasaFragment(), "")
        adapter.addFragment(SelesaijasaFragment(), "")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)

        btn_tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_jam)
        btn_tabs.getTabAt(1)!!.setIcon(R.drawable.dikonfirmasi2)
        btn_tabs.getTabAt(2)!!.setIcon(R.drawable.diterima2)
        btn_tabs.getTabAt(3)!!.setIcon(R.drawable.diterimaa)
        btn_tabs.getTabAt(4)!!.setIcon(R.drawable.dp1)
        btn_tabs.getTabAt(5)!!.setIcon(R.drawable.diproses2)
        btn_tabs.getTabAt(6)!!.setIcon(R.drawable.pelunasan)
        btn_tabs.getTabAt(7)!!.setIcon(R.drawable.done)

    }
}