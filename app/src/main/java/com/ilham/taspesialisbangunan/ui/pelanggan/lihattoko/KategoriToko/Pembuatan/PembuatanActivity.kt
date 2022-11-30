package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.PembuatanKanopi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.ProdukuserAdapter
import kotlinx.android.synthetic.main.activity_pembuatankanopi.*
import kotlinx.android.synthetic.main.toolbar.*

class PembuatanKanopiActivity : AppCompatActivity(), PembuatanKanopiContract.View {

    lateinit var presenter: PembuatanKanopiPresenter
    lateinit var pembuatanAdapter: ProdukuserAdapter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembuatankanopi)
        presenter = PembuatanKanopiPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukpembuatan(Constant.USERJASA_ID , Constant.JENISPEMBUATAN_NAME)
    }

    override fun initActivity() {
        tv_nama.text ="Detail Produk"
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        pembuatanAdapter = ProdukuserAdapter(this, arrayListOf()){
                dataProduk: DataProduk, position: Int, type: String ->
            Constant.PRODUK_ID = dataProduk.id!!

            produk = dataProduk

        }

        rcvPembuatankanopi.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pembuatanAdapter
        }

        swipePembuatan.setOnRefreshListener {
            presenter.getProdukpembuatan(Constant.USERJASA_ID, Constant.JENISPEMBUATAN_NAME)
        }
    }

    override fun onLoadingProduktoko(loading: Boolean) {
        when (loading) {
            true -> swipePembuatan.isRefreshing = true
            false -> swipePembuatan.isRefreshing = false
        }
    }

    override fun onResultProduktoko(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
        pembuatanAdapter.setData(dataProduk)
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}