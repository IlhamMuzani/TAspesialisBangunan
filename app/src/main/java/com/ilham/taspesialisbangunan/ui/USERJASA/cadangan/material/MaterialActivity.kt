package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.create.MaterialCreateActivity
import com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.material.update.MaterialUpdateActivity
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity(), MaterialContract.View {

    lateinit var presenter: MaterialPresenter
    lateinit var materialAdapter: MaterialAdapter
    lateinit var material: DataMaterial
    lateinit var prefsManager: PrefsManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        presenter = MaterialPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterial(prefsManager.prefsId)
    }

    override fun initActivity() {
        supportActionBar!!.title = "Material"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        MapsHelper.permissionMap(this, this)
    }

    override fun initListener() {

        materialAdapter = MaterialAdapter(this, arrayListOf()){
            dataMaterial: DataMaterial, position: Int, type: String ->

            material = dataMaterial

            when (type ){
                "update" -> startActivity(Intent(this, MaterialUpdateActivity::class.java))
                "delete" -> showDialogDelete( dataMaterial, position )
            }
        }

        rcvMaterial.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialAdapter
        }

        swipeM.setOnRefreshListener {
            presenter.getMaterial(prefsManager.prefsId)
        }

        fabMaterial.setOnClickListener { view ->
            startActivity(Intent(this, MaterialCreateActivity::class.java))
        }
    }

    override fun onLoadingMaterial(loading: Boolean) {
    when(loading){
        true -> swipeM.isRefreshing = true
        false -> swipeM.isRefreshing = false
    }
    }

    override fun onResultMaterial(responseMaterialList: ResponseMaterialList) {
     val dataMaterial: List<DataMaterial> = responseMaterialList.dataMaterial
        materialAdapter.setData(dataMaterial)
    }

    override fun onResultDelete(responseMaterialUpdate: ResponseMaterialUpdate) {
        showMessage( responseMaterialUpdate.msg )
    }

    override fun showDialogDelete(dataMaterial: DataMaterial, position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Hapus ${material.nama_toko}?" )

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deleteMaterial( Constant.MATERIAL_ID )
            materialAdapter.removeMaterial(position)
            dialog.dismiss()

        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

