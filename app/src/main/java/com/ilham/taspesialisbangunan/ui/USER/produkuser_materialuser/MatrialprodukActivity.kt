package com.ilham.taspesialisjasabangunan.ui.produkuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.ui.USER.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.USER.produkuser_materialuser.tabs.material.MaterialuserFragment
import com.ilham.taspesialisbangunan.ui.USER.produkuser_materialuser.tabs.produk.ProdukuserFragment
import kotlinx.android.synthetic.main.activity_produkuser.*


class MatrialprodukActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produkuser)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProdukuserFragment(), "Produk")
        adapter.addFragment(MaterialuserFragment(), "Material")
        btn_viepager.adapter = adapter
        btn_tabs.setupWithViewPager(btn_viepager)
    }
}