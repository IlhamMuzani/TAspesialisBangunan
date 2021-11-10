package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.materialuser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_materialuser.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MaterialuserAdapter (val context: Context, var dataMaterial: ArrayList<DataMaterial>,
                           val clickListener: (DataMaterial, Int, String) -> Unit ):
        RecyclerView.Adapter<MaterialuserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_materialuser, parent, false)
    )

    override fun getItemCount()= dataMaterial.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bing(dataMaterial[position])

        GlideHelper.setImage(context, "http://192.168.43.224/api_spesialisJB/public/"+  dataMaterial[position].gambar!!, holder.view.imvImageMuser)
        holder.view.txvDetailMuser.setOnClickListener {
            Constant.MATERIAL_ID = dataMaterial[position].kd_material!!
            clickListener(dataMaterial[position], position, "detail")
        }
        holder.view.crvMaterialuser.setOnClickListener {
            Constant.MATERIAL_ID = dataMaterial[position].kd_material!!
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataMaterial: DataMaterial) {
            view.txvNameTokoMuser.text = dataMaterial.nama_toko
            view.txvLokasiMuser.text = dataMaterial.alamat
            view.txvJenismaterialMuser.text = dataMaterial.jenis_material
            view.txvHargaMuser.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataMaterial.harga))

        }
    }

    fun setData(newDataMaterialuser: List<DataMaterial>){
        dataMaterial.clear()
        dataMaterial.addAll(newDataMaterialuser)
        notifyDataSetChanged()
    }
}
