package com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.tabs.step2

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
import kotlinx.android.synthetic.main.adapter_diterima.view.crvPelanggan
import kotlinx.android.synthetic.main.adapter_user_dp.view.*
import kotlin.collections.ArrayList


class DPAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                 val clickListener: (DataPengajuan, Int, String) -> Unit ):
    RecyclerView.Adapter<DPAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_dp, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        holder.view.crvPelanggan.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPelangganActivity::class.java ))
        }
              GlideHelper.setImage(context, Constant.IP_IMAGE + "uploads/" + dataPengajuan[position].gambar, holder.imvPengajuanDP)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txvDeskripsiDP.text = datapengajuan.deskripsi
            view.txvNamaDP.text = datapengajuan.user.username
            view.btn_statusDPproses.text = datapengajuan.status
        }
        val imvPengajuanDP = view.findViewById<ImageView>(R.id.imvPengajuanDP)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}