package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step3.selesai

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
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detail_produknotif.DetailPelangganActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_selesai.view.*
import kotlin.collections.ArrayList


class SelesaiAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                      val clickListener: (DataPengajuan, Int, String) -> Unit ):
    RecyclerView.Adapter<SelesaiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_selesai, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        holder.view.crv_selesai.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPelangganActivity::class.java ))
        }
        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].produk.gambar, holder.imvPengajuanSelesai)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txv_StatusSelesai.text = datapengajuan.status
            view.txvJenisselesai.text = datapengajuan.produk.jenis_pembuatan
        }
        val imvPengajuanSelesai = view.findViewById<ImageView>(R.id.imvPengajuanselesaiPel)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}