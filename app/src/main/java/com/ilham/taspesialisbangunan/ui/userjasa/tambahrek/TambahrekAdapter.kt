package com.ilham.taspesialisbangunan.ui.userjasa.tambahrek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import kotlinx.android.synthetic.main.adapter_tambahrekl.view.*

class TambahrekAdapter (val context: Context, var dataTambahrek: ArrayList<DataTambahrek>,
                        val clickListener: (DataTambahrek, Int, String) -> Unit):
        RecyclerView.Adapter<TambahrekAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_tambahrekl, parent, false)
    )

    override fun getItemCount() = dataTambahrek.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataTambahrek[position])

        holder.view.txvOptionstambahrek.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionstambahrek)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_update -> {
                        Constant.TAMBAHREK_ID = dataTambahrek[position].kd_rekening!!
                        clickListener(dataTambahrek[position], position, "update")
                    }
                    R.id.action_delete -> {
                        Constant.TAMBAHREK_ID = dataTambahrek[position].kd_rekening!!
                        clickListener(dataTambahrek[position], position, "delete")
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataTambahrek: DataTambahrek) {
            view.txvJenisBank.text = dataTambahrek.jenis_bank
            view.txv_norek.text = dataTambahrek.norek
            view.txv_atasnama.text = dataTambahrek.nama
        }
    }

    fun setData(newDataTambahrek: List<DataTambahrek>) {
        dataTambahrek.clear()
        dataTambahrek.addAll(newDataTambahrek)
        notifyDataSetChanged()
    }

    fun removeTambahrek(position: Int) {
        dataTambahrek.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataTambahrek.size)
    }
}