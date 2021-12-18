package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk.ProdukCreateActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.update.ProdukUpdateActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper
import kotlinx.android.synthetic.main.dialog_detailproduk.view.*
import kotlinx.android.synthetic.main.dialog_materialdetailjasa.view.*

class MaterialFragment : Fragment(), MaterialContract.View, OnMapReadyCallback {

    lateinit var presenter: MaterialPresenter
    lateinit var materialadapter: MaterialAdapter
    lateinit var material: DataMaterial
    lateinit var prefsManager: PrefsManager

    lateinit var rcvMaterial: RecyclerView
    lateinit var swipematerial: SwipeRefreshLayout
    lateinit var Fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_materialjasa, container, false)

        presenter = MaterialPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterial(prefsManager.prefsId)
    }

    override fun initFragment(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        MapsHelper.permissionMap(requireContext(),requireActivity())

        rcvMaterial = view.findViewById(R.id.rcvMaterial)
        swipematerial = view.findViewById(R.id.swipeMJ)
        Fab = view.findViewById(R.id.fabMaterial)

        materialadapter = MaterialAdapter(requireActivity(), arrayListOf()) {
                dataMaterial: DataMaterial, position: Int, type: String ->

            material = dataMaterial


            when (type) {
                "Update" -> startActivity(Intent(requireActivity(), ProdukUpdateActivity::class.java))
                "Delete" -> showDialogDelete( dataMaterial, position )
                "Detail" -> showDialogDetail( dataMaterial, position )

            }
        }

        rcvMaterial.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialadapter
        }

        swipematerial.setOnRefreshListener {
            presenter.getMaterial(prefsManager.prefsId)
        }

        Fab.setOnClickListener { view ->
            startActivity(Intent(requireActivity(), ProdukCreateActivity::class.java))
        }
    }

    override fun onLoadingMaterial(loading: Boolean) {
        when (loading) {
            true -> swipematerial.isRefreshing = true
            false -> swipematerial.isRefreshing = false
        }
    }

    override fun onResultMaterial(responseMaterialList: ResponseMaterialList) {
        val dataMaterial: List<DataMaterial> = responseMaterialList.dataMaterial
        materialadapter.setData(dataMaterial)
    }

    override fun onResultDelete(responseMaterialUpdate: ResponseMaterialUpdate) {
        showMessage( responseMaterialUpdate. msg )
    }

    override fun showDialogDelete(dataMaterial: DataMaterial, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle( "Konfirmasi" )
        dialog.setMessage( "Hapus ${material.nama_toko}?" )

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deleteMaterial( Constant.PRODUK_ID )
            materialadapter.removeMaterial( position )
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showDialogDetail(dataMaterial: DataMaterial, position: Int) {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.dialog_materialdetailjasa, null)

        GlideHelper.setImage( requireActivity(),"http://192.168.43.224/api_spesialisJB/public/"+dataMaterial.gambar!!, view.imvGambartoko)

        view.txvNameMaterial.text = dataMaterial.nama_toko
        view.txvJenisMaterial.text = dataMaterial.jenis_material
        view.txvAlamatMaterial.text = dataMaterial.alamat
        view.txvPhoneMaterial.text = dataMaterial.phone
        view.txvHargaMaterial.text = dataMaterial.harga
        view.txvDeskripsiMaterial.text = dataMaterial.deskripsi

    }


    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng (material.latitude!!.toDouble(), material.longitude!!.toDouble())
        googleMap.addMarker ( MarkerOptions(). position(latLng).title( material.nama_toko ))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}
