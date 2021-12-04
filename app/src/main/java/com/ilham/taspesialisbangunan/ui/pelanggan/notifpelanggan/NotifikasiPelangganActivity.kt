package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2.dp.DPFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1.menunggu.MenungguFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step3.selesai.SelesaiFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_notifikasi_pelanggan.*

class NotifikasiPelangganActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi_pelanggan)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MenungguFragment(), "Step 1")
        adapter.addFragment(DPFragment(), "Step 2")
//        adapter.addFragment(DiterimaFragment(), "Step 2")
//        adapter.addFragment(DiprosesFragment(), "Proses")
        adapter.addFragment(SelesaiFragment(), "Selesai")
        btn_viepagerNotif.adapter = adapter
        btn_tabsNotif.setupWithViewPager(btn_viepagerNotif)
    }
}