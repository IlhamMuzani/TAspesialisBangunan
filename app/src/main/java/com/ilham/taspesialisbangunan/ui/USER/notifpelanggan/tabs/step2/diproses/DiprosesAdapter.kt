package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.tabs.step2.diproses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import kotlinx.android.synthetic.main.adapter_diproses.view.*
import kotlin.collections.ArrayList


class DiprosesAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                       val clickListener: (DataPengajuan, Int, String) -> Unit ):
    RecyclerView.Adapter<DiprosesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_diproses, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

//        holder.view.txvDetailuser.setOnClickListener {
//            Constant.PENGAJUAN_ID = dataPengajuan[position].kd_pengajuan!!
//            context.startActivity(Intent(context, ProdukdetailActivity::class.java ))
//        }
//        holder.view.crv_diproses.setOnClickListener {
//            Constant.MATERIAL_ID = dataProduk[position].kd_produkjasa!!
//        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txvDeskripsidiproses.text = datapengajuan.deskripsi
            view.txvStatusdiproses.text = datapengajuan.status

        }
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}