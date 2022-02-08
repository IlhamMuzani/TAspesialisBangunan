package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.material.DataMaterial
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.MaterialJasa.materialjasadetail.MaterialJasaDetailActivity
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.ProdukAdapter
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa.produkjasadetail.ProdukjasadetailActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_material.view.*
import kotlinx.android.synthetic.main.adapter_produk.view.*
import kotlinx.android.synthetic.main.adapter_produk.view.imvImage
import kotlinx.android.synthetic.main.adapter_produkuser.view.*
import kotlin.collections.ArrayList

class MaterialAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>,
                       val clickListener: (DataProduk, Int, String) -> Unit ):
    RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context).inflate( R.layout.adapter_material, parent, false)
    )

    override fun getItemCount()= dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE +dataProduk[position].gambar!!, holder.view.imvImageM)

        holder.view.imvImageM.setOnClickListener {
            Constant.PRODUK_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, MaterialJasaDetailActivity::class.java ))
        }

        holder.view.txvOptionsM.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionsM)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_update -> {
                        Constant.PRODUK_ID = dataProduk[position].id!!
                        clickListener(dataProduk[position], position, "Update")
                    }
                    R.id.action_delete -> {
                        Constant.PRODUK_ID = dataProduk[position].id!!
                        clickListener(dataProduk[position], position, "Delete")
                    }
                }

                true
            }

            popupMenu.show()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val view = view
        fun bing(dataProduk: DataProduk) {
            view.txvNamaTokoM.text = dataProduk.nama_toko
            view.txvLocationM.text = dataProduk.alamat
        }
    }

    fun setData(newDataMaterial: List<DataProduk>) {
        dataProduk.clear()
        dataProduk.addAll(newDataMaterial)
        notifyDataSetChanged()
    }

    fun removeProduk(position: Int) {
        dataProduk.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataProduk.size)
    }

}