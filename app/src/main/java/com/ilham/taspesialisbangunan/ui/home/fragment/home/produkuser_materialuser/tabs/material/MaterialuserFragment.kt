package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.dialog_materialdetail.view.*
import java.text.NumberFormat
import java.util.*

class MaterialuserFragment : Fragment(), MaterialuserContract.View, OnMapReadyCallback {

    lateinit var presenter: MaterialuserPresenter
    lateinit var materialuserAdapter: MaterialuserAdapter
    lateinit var material: DataMaterial
    lateinit var prefsManager: PrefsManager

    lateinit var rcvMaterial: RecyclerView
    lateinit var swipe: SwipeRefreshLayout
    lateinit var EdtSearchMaterial: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_material, container, false)

        presenter = MaterialuserPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getMaterial()
    }

    override fun initFragment(view: View) {
        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvMaterial = view.findViewById(R.id.rcvMaterial)
        swipe = view.findViewById(R.id.swipe)
        EdtSearchMaterial = view.findViewById(R.id.edtSearchMaterial)

        materialuserAdapter = MaterialuserAdapter(requireActivity(), arrayListOf()){
                dataMaterial: DataMaterial, position: Int, type: String ->
            Constant.MATERIAL_ID = dataMaterial.kd_material!!

            material = dataMaterial

            when (type ){
                "detail" -> showDialogDetail( dataMaterial, position )
            }
        }

        EdtSearchMaterial.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.searchMaterial(EdtSearchMaterial.text.toString() )
                true
            } else {
                false
            }
        }

        rcvMaterial.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialuserAdapter
        }

        swipe.setOnRefreshListener {
            presenter.getMaterial()
        }
    }

    override fun onLoadingMaterialUser(loading: Boolean) {
        when (loading) {
            true -> swipe.isRefreshing = true
            false -> swipe.isRefreshing = false
        }
    }

    override fun onResultMaterialUser(responseMaterialList: ResponseMaterialList) {
        val dataMaterial: List<DataMaterial> = responseMaterialList.dataMaterial
        materialuserAdapter.setData(dataMaterial)
    }

    override fun showDialogDetail(dataMaterial: DataMaterial, position: Int) {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.dialog_materialdetail, null)

        GlideHelper.setImage(requireActivity(), "http://192.168.43.224/api_spesialisJB/public/"+ dataMaterial.gambar!!, view.imvGambartokoM)

        view.txvNameM.text = dataMaterial.nama_toko
        view.txvJenisM.text = dataMaterial.jenis_material
        view.txvAlamatM.text = dataMaterial.alamat
        view.txvPhoneM.text = dataMaterial.phone
        view.txvHargaM.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataMaterial.harga))
        view.txvDeskripsiM.text = dataMaterial.deskripsi

        val mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.mapM) as SupportMapFragment
        mapFragment.getMapAsync(this)

        view.imvCloseM.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(mapFragment).commit()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(material.latitude!!.toDouble(), material.longitude!!.toDouble())
        googleMap.addMarker( MarkerOptions().position(latLng).title(material.nama_toko))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}