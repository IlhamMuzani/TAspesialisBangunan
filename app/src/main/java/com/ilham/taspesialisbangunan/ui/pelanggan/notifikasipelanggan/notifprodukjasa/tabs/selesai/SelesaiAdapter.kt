package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai

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
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detail_produknotif.DetailPelangganActivity
import kotlinx.android.synthetic.main.adapter_selesai.view.*
import java.text.NumberFormat
import java.util.*
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
//        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].produk.gambar, holder.imvPengajuanSelesai)

        holder.view.txvOptionselesai.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionselesai)
            popupMenu.inflate(R.menu.menu_optionsmasukhistori)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_hapus -> {
                        Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
                        clickListener(dataPengajuan[position], position, "Delete")
                    }
//                    R.id.action_laporkan -> {
//                        Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
//                        clickListener(dataPengajuan[position], position, "Laporkan")
//                    }
                }
                true
            }

            popupMenu.show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txv_StatusSelesai.text = datapengajuan.produk.model
            view.txvJenisselesai.text = datapengajuan.categori_pesanan
            view.txv_tanggal.text = datapengajuan.created_at
            view.hargaselesai.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format (Integer.valueOf(datapengajuan.harga))

        }
//        val imvPengajuanSelesai = view.findViewById<ImageView>(R.id.imvPengajuanselesaiPel)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }

    fun removePengajuanselesai(position: Int) {
        dataPengajuan.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataPengajuan.size)
    }
}