package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diterima.DiterimajasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diproses.DiprosesjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.dp.DPjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.menunggu.MenunggujasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.selesai.SelesaijasaFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_notifikasi_jasa.*

class NotifikasiJasaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi_jasa)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MenunggujasaFragment(), "Menunggu")
        adapter.addFragment(DiterimajasaFragment(), "Diterima")
        adapter.addFragment(DPjasaFragment(), "DP")
        adapter.addFragment(DiprosesjasaFragment(), "Proses")
        adapter.addFragment(SelesaijasaFragment(), "Selesai")
        btn_viepagerNotifjasa.adapter = adapter
        btn_tabsNotifjasa.setupWithViewPager(btn_viepagerNotifjasa)
    }
}