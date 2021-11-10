package com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import kotlinx.android.synthetic.main.adapter_rekening.view.*
import kotlin.collections.ArrayList


class RekeningAdapter (val context: Context, var datatambahrek: ArrayList<DataTambahrek>,
                       val clickListener: (DataTambahrek, Int, String) -> Unit ):
    RecyclerView.Adapter<RekeningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_rekening, parent, false)
    )

    override fun getItemCount() = datatambahrek.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(datatambahrek[position])

//        holder.view.crvPelanggan.setOnClickListener {
//            Constant.PENGAJUAN_ID = datatambahrek[position].kd_rekening!!
//            context.startActivity(Intent(context, DetailPelangganActivity::class.java ))
//        }
//              GlideHelper.setImage(context, Constant.IP_IMAGE + "uploads/" + datatambahrek[position].gambar, holder.imvPengajuanDP)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataTambahrek: DataTambahrek) {
            view.txtNorek.text = dataTambahrek.norek
            view.txtAtasNama.text = dataTambahrek.nama
        }
//        val imvPengajuanDP = view.findViewById<ImageView>(R.id.imvPengajuanDP)
    }

    fun setData(newDataRekening: List<DataTambahrek>) {
        datatambahrek.clear()
        datatambahrek.addAll(newDataRekening)
        notifyDataSetChanged()
    }
}