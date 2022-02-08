
package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material.materialdetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.ui.utils.FileUtils
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_material_detail1.*
import kotlinx.android.synthetic.main.activity_material_detail1.view.*
import kotlinx.android.synthetic.main.activity_pengajuan.view.*
import kotlinx.android.synthetic.main.activity_pengajuan.view.btn_pengajuann
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbarjasa.*

class MaterialDetailActivity : AppCompatActivity(), MaterialDetailContract.View, OnMapReadyCallback {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: MaterialDetailPresenter
    lateinit var materialdetail: DataProduk
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_detail1)

        presenter = MaterialDetailPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterialdetail(Constant.MATERIAL_ID)
    }

    override fun initActivity() {
       tv_nama.text="Detail Materials"
    }

    override fun initListener() {
        ivKembali.setOnClickListener {
            onBackPressed()
        }
//        btn_pengajuanmaterial.setOnClickListener{
//            Constant.MATERIAL_ID = materialdetail.kd_material!!
//            startActivity(Intent(this, PengajuanActivity::class.java))
//        }

    }

    override fun onLoadingMaterialdetail(loading: Boolean) {
        when (loading) {
            true -> progressdetailmat.visibility = View.VISIBLE
            false -> progressdetailmat.visibility = View.GONE
        }
    }

    override fun onResultMaterialdetail(responseProdukDetail: ResponseProdukDetail) {
        materialdetail = responseProdukDetail.dataProduk

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + materialdetail.gambar!!, imvgambardetailmat)
        namadetailmat.text = materialdetail.nama_toko
        jenispembuatandetailmat.text = materialdetail.jenis_pembuatan
        alamatdetailmat.text = materialdetail.alamat
        phonedetailmat.text = materialdetail.phone
        hargadetailmat.text = materialdetail.harga
        deskripsidetailmat.text = materialdetail.deskripsi

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapdetailmat) as SupportMapFragment
        mapFragment.getMapAsync( this )

    }

    override fun onResultPengajuan(responsePengajuanInsert: ResponsePengajuanInsert) {
        showMessage("Pengajuan terkirim")
    }

    override fun showDialogPengajuan(dataProduk: DataProduk) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.activity_pengajuan, null)
        view.imvPengajuan.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }
        view.btnpengajuannmat.setOnClickListener {
            val pengajuan = view.edtdeskripsipengajuann.text
            if (pengajuan.isNullOrEmpty() || uriImage == null ) {
                showMessage("Deskripsi harus diisi")
            } else {
                presenter.insertPengajuan(
                    dataProduk.kd_user!!,
                    prefsManager.prefsId,
                    FileUtils.getFile(this, uriImage),
                    pengajuan.toString(),
                    "Menunggu"
                )
            }
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()

    }


    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (materialdetail.latitude!!.toDouble(), materialdetail.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( materialdetail.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            val view = layoutInflater.inflate(R.layout.activity_pengajuan, null)
            view.imvPengajuan.setImageURI( uriImage )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}