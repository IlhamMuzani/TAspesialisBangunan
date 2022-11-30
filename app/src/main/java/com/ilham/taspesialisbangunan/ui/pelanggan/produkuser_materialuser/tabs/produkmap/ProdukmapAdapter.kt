package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produkmap

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail.ProdukdetailActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_produkmap.view.*
import kotlinx.android.synthetic.main.adapter_produkuser.view.*
import kotlinx.android.synthetic.main.adapter_produkuser.view.imvImage
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProdukmapAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>, ):
        RecyclerView.Adapter<ProdukmapAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_produkmap, parent, false)
    )

    override fun getItemCount() = dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE +  dataProduk[position].gambar!!, holder.view.imvImagemap)

        holder.view.crv_produkmap.setOnClickListener {
            Constant.PRODUK_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, ProdukdetailActivity::class.java ))
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataproduk: DataProduk) {
            view.txvNamemap.text = dataproduk.jenis_pembuatan
            view.txvJenismap.text = dataproduk.model
            view.txvHargamap.text =NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataproduk.harga))

            }
    }

    fun setData(newDataProduk: List<DataProduk>) {
        dataProduk.clear()
        dataProduk.addAll(newDataProduk)
        notifyDataSetChanged()
    }
}