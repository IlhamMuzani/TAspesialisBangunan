package com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.notifikasi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.saran.DataSaran
import kotlinx.android.synthetic.main.adapter_notifsaran.view.*

class NotifSaranAdapter (val context: Context, var dataSaran: ArrayList<DataSaran>, ):
    RecyclerView.Adapter<NotifSaranAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from( parent.context).inflate( R.layout.adapter_notifsaran, parent, false )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataSaran[position])
    }

    override fun getItemCount() = dataSaran.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataSaran: DataSaran) {
            view.txvDeskripsisaran.text = dataSaran.deskripsi
        }
    }

    fun setData(newDataNotifSaran: List<DataSaran>) {
        dataSaran.clear()
        dataSaran.addAll(newDataNotifSaran)
        notifyDataSetChanged()
    }
}