package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.saran.DataSaran
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail.ProdukdetailActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_produkuser.view.*
import kotlinx.android.synthetic.main.adapter_produkuser.view.imvImage
import kotlinx.android.synthetic.main.adapter_saran.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class SaranAdapter (val context: Context, var dataSaran: ArrayList<DataSaran>,
                    val clickListener: (DataSaran, Int, String) -> Unit ):
        RecyclerView.Adapter<SaranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_saran, parent, false)
    )

    override fun getItemCount() = dataSaran.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataSaran[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataSaran: DataSaran) {
            view.username.text = dataSaran.user.username
            view.txvsaran.text = dataSaran.deskripsi

        }
    }

    fun setData(newDataSaran: List<DataSaran>) {
        dataSaran.clear()
        dataSaran.addAll(newDataSaran)
        notifyDataSetChanged()
    }
}