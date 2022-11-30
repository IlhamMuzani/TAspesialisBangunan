package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.KategoriTokoFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.ProdukToko.ProdukTokoFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.Toko.TokoFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.Toko.TokoPresenter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail.ProdukdetailPresenter
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ViewPagerAdapterProduk
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_lihattoko.*
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.toolbar.*

class LihatTokoActivity : AppCompatActivity(), LihatTokoContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: LihatTokoPresenter
    lateinit var produk: DataProduk

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihattoko)
        presenter = LihatTokoPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getProdukdetail(Constant.PRODUK_ID)
    }

    @SuppressLint("SetTextI18n")
    override fun initActivity() {

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

    }

    override fun initListener() {

        val adapterjasa = ViewPagerAdapterProduk(this.supportFragmentManager)
        adapterjasa.addFragment(TokoFragment(), "Toko")
        adapterjasa.addFragment(ProdukTokoFragment(), "Produk")
        adapterjasa.addFragment(KategoriTokoFragment(), "Kategori")
        btn_viepagertoko.adapter = adapterjasa
        btn_tabstoko.setupWithViewPager(btn_viepagertoko)
    }

    override fun onLoadingLihatToko(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResultProdukdetail(responseProdukDetail: ResponseProdukDetail) {
        produk = responseProdukDetail.dataProduk

        idNamaToko.text = produk.user.nama_toko
        GlideHelper.setImage( applicationContext, Constant.IP_IMAGE + produk.user.gambar!!, fotoToko)    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}