package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.notifikasi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_notifpengajuan.view.*

class NotifPengajuanAdapter (
    val context: Context,
    var dataPengajuan: ArrayList<DataPengajuan>,
    val clickListener: (DataPengajuan, Int, String) -> Unit
):
        RecyclerView.Adapter<NotifPengajuanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from( parent.context).inflate( R.layout.adapter_notifpengajuan, parent, false )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        val dataPengajuan = dataPengajuan[position]
        val user = dataPengajuan.userpelanggan

        GlideHelper.setImage(context, Constant.IP_IMAGE + "uploads/" + dataPengajuan.gambar, holder.imvPengajuan)

        holder.txvNamaUser.text = user.username
        holder.txvDeskripsiNotif.text = dataPengajuan.deskripsi

//        holder.detailpengajuan.setOnClickListener {
//            Constant.PENGAJUAN_ID=dataPengajuan.kd_pengajuan!!
//            context.startActivity(Intent(context, DetailPengajuanActivity::class.java ))
//        }

    }

    override fun getItemCount() = dataPengajuan.size

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataPengajuan: DataPengajuan) {
            view.txvDeskripsiNotif.text = dataPengajuan.deskripsi
        }
        val imvPengajuan = view.findViewById<ImageView>(R.id.imvPengajuan)
        val txvNamaUser = view.findViewById<TextView>(R.id.txvNamaUser)
        val txvDeskripsiNotif = view.findViewById<TextView>(R.id.txvDeskripsiNotif)
        val detailpengajuan = view.findViewById<LinearLayout>(R.id.detailpengajuan)
    }

    fun setData(newDataNotifPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataNotifPengajuan)
        notifyDataSetChanged()
    }

    fun removePengajuan(position: Int) {
        dataPengajuan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataPengajuan.size)
    }
}