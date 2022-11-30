package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.produkjasadetail.ProdukjasadetailActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_produk.view.*
import kotlinx.android.synthetic.main.adapter_produk.view.imvImage
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ProdukAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>,
                    val clickListener: (DataProduk, Int, String) -> Unit ):
        RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context).inflate( R.layout.adapter_produk, parent, false)
    )

    override fun getItemCount()= dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE + dataProduk[position].gambar!!, holder.view.imvImage)

        holder.view.imvImage.setOnClickListener {
            Constant.PRODUK_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, ProdukjasadetailActivity::class.java ))
        }

        holder.view.txvOptions.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptions)
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
            view.txvNamaToko.text = dataProduk.model
            view.txvJenispembuatan.text = dataProduk.kelurahan
            view.harga.text = NumberFormat.getCurrencyInstance(Locale("in","ID")).format(Integer.valueOf(dataProduk.harga))
        }
    }

    fun setData(newDataProduk: List<DataProduk>) {
        dataProduk.clear()
        dataProduk.addAll(newDataProduk)
        notifyDataSetChanged()
    }

    fun removeProduk(position: Int) {
        dataProduk.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataProduk.size)
    }

}