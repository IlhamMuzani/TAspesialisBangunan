package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.materialuser

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.ui.USER.homeuser.UserActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.ilham.taspesialisjasabangunan.ui.produkuser.MatrialprodukActivity
import kotlinx.android.synthetic.main.activity_materialuser.*
import kotlinx.android.synthetic.main.content_materialuser.*
import kotlinx.android.synthetic.main.dialog_materialdetail.view.*
import java.text.NumberFormat
import java.util.*

class MaterialuserActivity : AppCompatActivity(), MaterialuserContract.View, OnMapReadyCallback{

    lateinit var presenter: MaterialuserPresenter
    lateinit var materialuserAdapter: MaterialuserAdapter
    lateinit var material: DataMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materialuser)
        presenter = MaterialuserPresenter(this)
        presenter.getMaterial()
        }

    override fun onStart() {
        super.onStart()
        presenter.getMaterial()
    }

    override fun initActivity() {
    }

    override fun initListener() {

        materialuserAdapter = MaterialuserAdapter(this, arrayListOf()){
                dataMaterial: DataMaterial, position: Int, type: String ->
            Constant.MATERIAL_ID = dataMaterial.kd_material!!

            material = dataMaterial

            when (type ){
                "detail" -> showDialogDetail( dataMaterial, position )
            }
        }

        edtSearchM.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchMaterial(edtSearchM.text.toString() )
                true
            } else {
                false
            }
        }

        rcvMaterialuser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialuserAdapter
        }

        swipeMuser.setOnRefreshListener {
            presenter.getMaterial()
        }

        btn_jasabangunanB.setOnClickListener{
            startActivity(Intent(this, MatrialprodukActivity::class.java))
        }

        btn_materialbangunanB.setOnClickListener{
            startActivity(Intent(this, MaterialuserActivity::class.java))
        }

        btn_kembalihomeB.setOnClickListener{
            startActivity(Intent(this, UserActivity::class.java))
        }

    }

    override fun onLoadingMaterialUser(loading: Boolean) {
        when(loading) {
            true -> swipeMuser.isRefreshing = true
            false -> swipeMuser.isRefreshing = false
        }
        }

    override fun onResultMaterialUser(responseMaterialList: ResponseMaterialList) {
        val dataMaterial: List<DataMaterial> = responseMaterialList.dataMaterial
        materialuserAdapter.setData(dataMaterial)
    }

    override fun showDialogDetail(dataMaterial: DataMaterial, position: Int) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_materialdetail, null)

        GlideHelper.setImage(applicationContext, "http://192.168.43.224/api_spesialisJB/public/"+ dataMaterial.gambar!!, view.imvGambartokoM)

        view.txvNameM.text = dataMaterial.nama_toko
        view.txvJenisM.text = dataMaterial.jenis_material
        view.txvAlamatM.text = dataMaterial.alamat
        view.txvPhoneM.text = dataMaterial.phone
        view.txvHargaM.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataMaterial.harga))
        view.txvDeskripsiM.text = dataMaterial.deskripsi

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapM) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.imvCloseM.setOnClickListener {
            supportFragmentManager.beginTransaction().remove(mapFragment).commit()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(material.latitude!!.toDouble(), material.longitude!!.toDouble())
        googleMap.addMarker( MarkerOptions().position(latLng).title(material.nama_toko))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}