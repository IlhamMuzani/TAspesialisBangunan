package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.notifprodukjasa.NotifPjasaFragment
import kotlinx.android.synthetic.main.activity_notifikasi_pelanggan.*
import kotlinx.android.synthetic.main.toolbar.*

class NotifikasiPelangganActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi_pelanggan)

        val adapter = ViewPagerAdapterNotif(supportFragmentManager)
        adapter.addFragment(NotifPjasaFragment(), "Notifikasi Jasa")
        adapter.addFragment(NotifPjasaFragment(), "Notifikasi Material")
//        adapter.addFragment(DiterimaFragment(), "Step 2")
//        adapter.addFragment(DiprosesFragment(), "Proses")
//        adapter.addFragment(SelesaiFragment(), "Selesai")
        btn_viepagerNotif.adapter = adapter
        btn_tabsNotif.setupWithViewPager(btn_viepagerNotif)
        tv_nama.text = "Notifikasi"

        setData()
    }

    fun setData() {
        ivKembali.setOnClickListener {
            onBackPressed()
        }
    }
}