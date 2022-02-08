package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step1

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
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.detail.DetailPelangganActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_menunggu.view.*
import kotlin.collections.ArrayList


class MenungguAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                       val clickListener: (DataPengajuan, Int, String) -> Unit ):
        RecyclerView.Adapter<MenungguAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_menunggu, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        holder.view.crv_menunggu.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPelangganActivity::class.java ))
        }
        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].gambar, holder.imvPengajuanM)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txvDeskripsimenunggu.text = datapengajuan.deskripsi
            view.txvNama.text = datapengajuan.user.username
            view.txv__Status.text = datapengajuan.status
        }
        val imvPengajuanM = view.findViewById<ImageView>(R.id.imvPengajuanmenunggu)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}