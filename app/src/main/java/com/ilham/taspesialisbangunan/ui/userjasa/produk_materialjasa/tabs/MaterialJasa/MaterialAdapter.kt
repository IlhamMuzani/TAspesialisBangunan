package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_material.view.*
import kotlinx.android.synthetic.main.adapter_produk.view.*
import kotlinx.android.synthetic.main.adapter_produk.view.imvImage
import kotlinx.android.synthetic.main.adapter_produkuser.view.*
import kotlin.collections.ArrayList

class MaterialAdapter (val context: Context, var dataMaterial: ArrayList<DataMaterial>,
                       val clickListener: (DataMaterial, Int, String) -> Unit):
    RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_material, parent, false)
    )

    override fun getItemCount() = dataMaterial.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataMaterial[position])

        GlideHelper.setImage(context, "http://192.168.43.224/api_spesialisJB/public/"+  dataMaterial[position].gambar!!, holder.view.imvImageM)

//        Glide.with(context)
//            .load(dataMaterial[position].gambar)
//            .centerCrop()
//            .placeholder(R.drawable.img_no_image)
//            .error(R.drawable.img_no_image)
//            .into(holder.view.imvImageM)

        holder.view.txvOptionsM.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionsM)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.MATERIAL_ID = dataMaterial[position].kd_material!!
                        clickListener(dataMaterial[position], position, "update")
                    }
                    R.id.action_delete -> {
                        Constant.MATERIAL_ID = dataMaterial[position].kd_material!!
                        clickListener(dataMaterial[position], position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

//    holder.txvNamaTokoM.text =dataMaterial[position].nama_toko
//        holder.txvLocationM.text =dataMaterial[position].alamat

//        val gambar = "http://192.168.43.224/penjualanapp/public/uploads/material/"+ dataMaterial[position].gambar
//        Picasso.get()
//            .load("http://192.168.43.224/api_spesialisJB/public/uploads/material/"+ dataMaterial[position].gambar)
//            .placeholder(R.drawable.product)
//            .error(R.drawable.img_no_image)
//            .resize(325,300)
//            .into(holder.imgProduk)
//

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataMaterial: DataMaterial) {
            view.txvNamaTokoM.text = dataMaterial.nama_toko
            view.txvLocationM.text = dataMaterial.alamat
        }
    }
//        val txvNamaTokoM = view.findViewById<TextView>(R.id.txvNamaTokoM)
//        val txvLocationM = view.findViewById<TextView>(R.id.txvLocationM)
//        val imgProduk = view.findViewById<ImageView>(R.id.imvImageM)

    fun setData(newDataMaterial: List<DataMaterial>) {
        dataMaterial.clear()
        dataMaterial.addAll(newDataMaterial)
        notifyDataSetChanged()
    }

    fun removeMaterial(position: Int) {
        dataMaterial.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataMaterial.size)
    }
}