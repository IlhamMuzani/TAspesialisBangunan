package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.Pembuatan.PembuatanActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko.Pembuatan.PembuatanPresenter
import com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog

class KategoriTokoFragment : Fragment(), KategoriTokoContract.View {

    lateinit var sLoading: SweetAlertDialog
    lateinit var presenter: KategoriTokoPresenter
    lateinit var produk: DataProduk

    lateinit var layoutkanopi : LinearLayout
    lateinit var layoutpagar : LinearLayout
    lateinit var layouttangga : LinearLayout
    lateinit var layouttralis : LinearLayout
    lateinit var layouthalaman : LinearLayout

    lateinit var tv_jumlahkanopi : TextView
    lateinit var tv_jumlahpagar : TextView
    lateinit var tv_jumlahtangga : TextView
    lateinit var tv_jumlahtralis : TextView
    lateinit var tv_jumlahhalaman : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_kategoritoko, container, false)
        presenter = KategoriTokoPresenter(this)

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduktampilkanopi(Constant.USERJASA_ID , "Pembuatan Kanopi")
        presenter.getProduktampilpagar(Constant.USERJASA_ID , "Pembuatan Pagar")
        presenter.getProduktampiltangga(Constant.USERJASA_ID , "Pembuatan Tangga")
        presenter.getProduktampiltralis(Constant.USERJASA_ID , "Pembuatan Tralis")
        presenter.getProduktampilhalaman(Constant.USERJASA_ID , "Pembuatan Halaman")
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.JENISPEMBUATAN_NAME
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

        layoutkanopi = view.findViewById(R.id.layoutkanopi)
        layoutpagar = view.findViewById(R.id.layoutpagar)
        layouttangga = view.findViewById(R.id.layoutTangga)
        layouttralis = view.findViewById(R.id.layoutTralis)
        layouthalaman = view.findViewById(R.id.layoutHalaman)

        tv_jumlahkanopi = view.findViewById(R.id.txt_jummlahkanopi)
        tv_jumlahpagar = view.findViewById(R.id.txt_jumlahpagar)
        tv_jumlahtangga = view.findViewById(R.id.txt_jumlahtangga)
        tv_jumlahtralis = view.findViewById(R.id.txt_jumlahtralis)
        tv_jumlahhalaman = view.findViewById(R.id.txt_jumlahhalaman)

        layoutkanopi.setOnClickListener {
            Constant.JENISPEMBUATAN_NAME = "Pembuatan Kanopi"
            startActivity(Intent(requireContext(), PembuatanActivity::class.java))
        }

        layoutpagar.setOnClickListener {
            Constant.JENISPEMBUATAN_NAME = "Pembuatan Pagar"
            startActivity(Intent(requireContext(), PembuatanActivity::class.java))
        }

        layouttangga.setOnClickListener {
            Constant.JENISPEMBUATAN_NAME = "Pembuatan Tangga"
            startActivity(Intent(requireContext(), PembuatanActivity::class.java))
        }

        layouttralis.setOnClickListener {
            Constant.JENISPEMBUATAN_NAME = "Pembuatan Tralis"
            startActivity(Intent(requireContext(), PembuatanActivity::class.java))
        }

        layouthalaman.setOnClickListener {
            Constant.JENISPEMBUATAN_NAME = "Pembuatan Halaman"
            startActivity(Intent(requireContext(), PembuatanActivity::class.java))
        }


    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResulttampilkanopi(responseProdukList: ResponseProdukList) {
        tv_jumlahkanopi.text = responseProdukList.dataProduk.size.toString()

    }

    override fun onResulttampilpagar(responseProdukList: ResponseProdukList) {
        tv_jumlahpagar.text = responseProdukList.dataProduk.size.toString()
    }

    override fun onResulttampiltangga(responseProdukList: ResponseProdukList) {
        tv_jumlahtangga.text = responseProdukList.dataProduk.size.toString()
    }

    override fun onResulttampiltralis(responseProdukList: ResponseProdukList) {
        tv_jumlahtralis.text = responseProdukList.dataProduk.size.toString()
    }

    override fun onResulttampilhalaman(responseProdukList: ResponseProdukList) {
        tv_jumlahhalaman.text = responseProdukList.dataProduk.size.toString()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}