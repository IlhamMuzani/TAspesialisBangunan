package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun.AkunFragment
import com.ilham.taspesialisbangunan.ui.USERJASA.cadangan.Aduanjasa.ReportActivity
import com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.NotifikasiPelangganActivity
import com.ilham.taspesialisjasabangunan.ui.produkuser.MatrialprodukActivity

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManageruser: PrefsManageruser
    lateinit var presenter: HomePresenter

    lateinit var crvLihatProduk: CardView
    lateinit var crvNotifPelanggan: CardView
    lateinit var crvLaporkan: CardView
    lateinit var crvProfilUser: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

       presenter = HomePresenter(this)
        prefsManageruser = PrefsManageruser(requireActivity())

        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        crvLihatProduk = view.findViewById(R.id.crv_lihatproduk)
        crvNotifPelanggan = view.findViewById(R.id.crv_notifpelanggan)
        crvLaporkan = view.findViewById(R.id.crv_Laporkan)
        crvProfilUser = view.findViewById(R.id.crv_profiluser)


        crvLihatProduk.setOnClickListener {
            if (prefsManageruser.prefsisLoginuser) {
                startActivity(Intent(requireActivity(), MatrialprodukActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

        crvLaporkan.setOnClickListener {
            if (prefsManageruser.prefsisLoginuser) {
                startActivity(Intent(requireActivity(), ReportActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

        crvProfilUser.setOnClickListener {
            if (prefsManageruser.prefsisLoginuser) {
                startActivity(Intent(requireActivity(), AkunFragment::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

        crvNotifPelanggan.setOnClickListener {
            if (prefsManageruser.prefsisLoginuser) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

    }


        override fun onStart() {
        super.onStart()

    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}