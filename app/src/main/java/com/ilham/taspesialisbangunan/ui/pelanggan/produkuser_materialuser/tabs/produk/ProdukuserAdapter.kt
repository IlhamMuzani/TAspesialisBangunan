package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk

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
import kotlinx.android.synthetic.main.adapter_produkuser.view.*
import kotlinx.android.synthetic.main.adapter_produkuser.view.imvImage
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProdukuserAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>,
                         val clickListener: (DataProduk, Int, String) -> Unit ):
        RecyclerView.Adapter<ProdukuserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_produkuser, parent, false)
    )

    override fun getItemCount() = dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE +  dataProduk[position].gambar!!, holder.view.imvImage)

        holder.view.txvDetailuser.setOnClickListener {
            Constant.PRODUK_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, ProdukdetailActivity::class.java ))
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataproduk: DataProduk) {
            view.txvJenispembuatan1.text = dataproduk.model
            view.txvModel.text = dataproduk.kelurahan
            view.txvHargauser.text =NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataproduk.harga))
//            view.txvHargauser.text ="Rp."+dataproduk.harga

//            if (dataproduk.rating != null) {
//                val df = DecimalFormat("#.#")
//                val rating = df.format(dataproduk.rating.toDouble())
//                view.txvbintang1.text = rating
//            } else {
//                view.txvbintang1.text = ""
//            }

            when (dataproduk.category){
                "Produk Jasa" -> {
                    view.imvprodukjasamaterial2.visibility = View.GONE
                    view.imvprodukjasa.visibility = View.VISIBLE
                }
                "Produk Jasa dan Material" -> {
                    view.imvprodukjasa.visibility = View.GONE
                    view.imvprodukjasamaterial2.visibility = View.VISIBLE
                }

            }

        }
    }

    fun setData(newDataProduk: List<DataProduk>) {
        dataProduk.clear()
        dataProduk.addAll(newDataProduk)
        notifyDataSetChanged()
    }
}