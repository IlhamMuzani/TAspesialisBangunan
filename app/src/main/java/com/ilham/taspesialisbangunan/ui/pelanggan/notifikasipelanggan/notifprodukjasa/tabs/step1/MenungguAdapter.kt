package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.tabsprodukjasa.step1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.detailnotifprodukjasa.DetailPelangganActivity
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

        holder.view.imvPengajuanmenunggu.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPelangganActivity::class.java ))
        }
        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].produk.gambar, holder.imvPengajuanM)

        holder.view.txvOptionss.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionss)
            popupMenu.inflate(R.menu.menu_optionspengajuan)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_deletepengajuan -> {
                        Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
                        clickListener(dataPengajuan[position], position, "Delete")
                    }
                }
                true
            }

            popupMenu.show()
            }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
//            view.txvDeskripsimenunggu.text = datapengajuan.deskripsi
            view.txvJenisPem.text = datapengajuan.produk.jenis_pembuatan
            view.txv__Status.text = datapengajuan.status
        }
        val imvPengajuanM = view.findViewById<ImageView>(R.id.imvPengajuanmenunggu)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }

    fun removePengajuanmenunggu(position: Int) {
        dataPengajuan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataPengajuan.size)
    }

}