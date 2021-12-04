package com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.diterima.DiterimajasaFragment
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.diproses.DiprosesjasaFragment
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.dp.DPjasaFragment
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.menunggu.MenunggujasaFragment
import com.ilham.taspesialisbangunan.ui.USERJASA.notifjasa.tabjasa.selesai.SelesaijasaFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.ViewPagerAdapter
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