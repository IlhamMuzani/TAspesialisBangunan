package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.KategoriToko

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.MenungguAdapter
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.lihattoko.KategoriToko.PembuatanKanopi.PembuatanKanopiActivity

class KategoriTokoFragment : Fragment(), KategoriTokoContract.View {

    lateinit var layoutkanopi : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kategoritoko, container, false)

        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        layoutkanopi = view.findViewById(R.id.layoutkanopi)

        layoutkanopi.setOnClickListener {
            Constant.JENISPEMBUATAN_ID
            startActivity(Intent(requireContext(), PembuatanKanopiActivity::class.java))
        }


    }
}