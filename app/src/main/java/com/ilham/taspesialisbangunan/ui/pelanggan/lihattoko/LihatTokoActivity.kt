package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.pelanggan.login.LoginuserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.KategoriToko.KategoriTokoFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.ProdukToko.ProdukTokoFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.Toko.TokoFragment
import com.ilham.taspesialisbangunan.ui.userjasa.loginjasa.LoginjasaActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.Kategori.KategoriFragment
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukFragment
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ViewPagerAdapterProduk
import kotlinx.android.synthetic.main.activity_lihattoko.*
import kotlinx.android.synthetic.main.activity_masuk.*

class LihatTokoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihattoko)

        val adapterjasa = ViewPagerAdapterProduk(this.supportFragmentManager)
        adapterjasa.addFragment(TokoFragment(), "Toko")
        adapterjasa.addFragment(ProdukTokoFragment(), "Produk")
        adapterjasa.addFragment(KategoriTokoFragment(), "Kategori")
        btn_viepagertoko.adapter = adapterjasa
        btn_tabstoko.setupWithViewPager(btn_viepagertoko)
    }
}