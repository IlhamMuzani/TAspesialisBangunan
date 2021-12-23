package com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.material.materialdetail.MaterialDetailActivity
import com.ilham.taspesialisbangunan.ui.home.fragment.home.produkuser_materialuser.tabs.produk.ProdukuserAdapter
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_materialuser.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MaterialuserAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>,
                           val clickListener: (DataProduk, Int, String) -> Unit ):
        RecyclerView.Adapter<MaterialuserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_materialuser, parent, false)
    )
    override fun getItemCount()= dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE +  dataProduk[position].gambar!!, holder.view.imvImageprodukmat)

        holder.view.txvDetailprodukmat.setOnClickListener {
            Constant.MATERIAL_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, MaterialDetailActivity::class.java ))
        }

//        holder.view.crvMaterialuser.setOnClickListener {
//            Constant.MATERIAL_ID = dataMaterial[position].kd_material!!
//        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataProduk: DataProduk) {
            view.txvNameprodukmat.text = dataProduk.nama_toko
            view.txvJenisprodukmat.text = dataProduk.jenis_pembuatan
            view.txvHargaprodukmat.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataProduk.harga))

        }
    }

    fun setData(newDataMaterialuser: List<DataProduk>){
        dataProduk.clear()
        dataProduk.addAll(newDataMaterialuser)
        notifyDataSetChanged()
    }
}
