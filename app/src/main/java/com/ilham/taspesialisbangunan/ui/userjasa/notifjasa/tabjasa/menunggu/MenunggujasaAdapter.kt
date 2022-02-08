package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.menunggu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail.DetailPengajuanActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_menunggujasa.view.*
import kotlin.collections.ArrayList


class MenunggujasaAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                           val clickListener: (DataPengajuan, Int, String) -> Unit ):
        RecyclerView.Adapter<MenunggujasaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_menunggujasa, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        holder.view.detailpengajuanjasa.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPengajuanActivity::class.java ))
        }
        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].gambar, holder.imvPengajuan)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txvDeskripsimenunggujasa.text = datapengajuan.deskripsi
            view.txvNamajasa.text = datapengajuan.user.username
        }
        val imvPengajuan = view.findViewById<ImageView>(R.id.imvPengajuanmenunggu)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}