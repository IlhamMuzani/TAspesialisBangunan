package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

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
import kotlinx.android.synthetic.main.adapter_saranjasa.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class SaranJasaAdapter (val context: Context, var dataSaran: ArrayList<DataSaran>,
                        val clickListener: (DataSaran, Int, String) -> Unit ):
        RecyclerView.Adapter<SaranJasaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_saranjasa, parent, false)
    )

    override fun getItemCount() = dataSaran.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataSaran[position])

        GlideHelper.setImage( context,Constant.IP_IMAGE + dataSaran[position].user.gambar, holder.view.imvsaranjasa)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataSaran: DataSaran) {
            view.usernamejasa.text = dataSaran.user.username
            view.txvsaranjasa.text = dataSaran.deskripsi
            view.rating_barr2.rating = dataSaran.rating!!.toFloat()

        }
    }

    fun setData(newDataSaran: List<DataSaran>) {
        dataSaran.clear()
        dataSaran.addAll(newDataSaran)
        notifyDataSetChanged()
    }
}