package com.ilham.taspesialisbangunan.ui.fragment.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.pelanggan.notifpelanggan.NotifikasiPelangganActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.NotifikasiJasaActivity
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diproses.DiprosesjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.diterima.DiterimajasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.dp.DPjasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.menunggu.MenunggujasaFragment
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabjasa.selesai.SelesaijasaFragment
import kotlinx.android.synthetic.main.activity_notifikasi_jasa.*


class NotificationsFragment : Fragment(), NotificationsContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: NotificationsPresenter

    //user
    lateinit var layoutjasa : LinearLayout
    lateinit var BtnPesan : RelativeLayout
    lateinit var BtnNotifikasi : RelativeLayout
    lateinit var BtnLaporkan : RelativeLayout

    //jasa
    lateinit var layoutuser : LinearLayout
    lateinit var viewpager : ViewPager
    lateinit var btn_tabs : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        presenter = NotificationsPresenter(this)
        prefsManager = PrefsManager(requireActivity())


        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        //user
        layoutuser = view.findViewById(R.id.layoutuser)
        BtnPesan = view.findViewById(R.id.btn_Pesanan)
        BtnNotifikasi = view.findViewById(R.id.btn_notifikasi)
        BtnLaporkan = view.findViewById(R.id.btnLaporkanjasa)

        //jasa
        layoutjasa = view.findViewById(R.id.layoutjasa)
        viewpager = view.findViewById(R.id.btn_viepagerNotifjasa)
        btn_tabs = view.findViewById(R.id.btn_tabsNotifjasa)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(MenunggujasaFragment(), "Menunggu")
        adapter.addFragment(DiterimajasaFragment(), "Diterima")
        adapter.addFragment(DPjasaFragment(), "DP")
        adapter.addFragment(DiprosesjasaFragment(), "Proses")
        adapter.addFragment(SelesaijasaFragment(), "Selesai")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)


        BtnPesan.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

        BtnNotifikasi.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }

        BtnNotifikasi.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("SIlakan Login Terlebih Dahulu")
            }
        }

        BtnLaporkan.setOnClickListener {
            if (prefsManager.prefsIsLogin) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.userDetail(prefsManager.prefsId)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoading(loading: Boolean) {

    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.user.status == "pelanggan") {
            layoutuser.visibility = View.VISIBLE
            layoutjasa.visibility = View.GONE
        } else {
            layoutjasa.visibility = View.VISIBLE
            layoutuser.visibility = View.GONE
        }
    }
}