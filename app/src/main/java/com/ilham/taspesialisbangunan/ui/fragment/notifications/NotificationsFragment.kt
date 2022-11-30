package com.ilham.taspesialisbangunan.ui.fragment.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.NotifPjasaFragment
import com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.ViewPagerAdapter
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.NotifProdukjasaFragment


class NotificationsFragment : Fragment(), NotificationsContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: NotificationsPresenter

    //user
    lateinit var layoutuser : LinearLayout
    lateinit var viewpageruser : ViewPager
    lateinit var btn_tabsuser : TabLayout

    //jasa
    lateinit var layoutjasa : LinearLayout
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
        viewpageruser = view.findViewById(R.id.btn_viepagerNotifuser)
        btn_tabsuser = view.findViewById(R.id.btn_tabsNotifuser)

        val adapteruser = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapteruser.addFragment(NotifPjasaFragment(), "Notifikasi Pesanan Jasa")
        viewpageruser.adapter = adapteruser
        btn_tabsuser.setupWithViewPager(viewpageruser)

        btn_tabsuser.getTabAt(0)!!.setIcon(R.drawable.jasa)



        //jasa
        layoutjasa = view.findViewById(R.id.layoutjasa)
        viewpager = view.findViewById(R.id.btn_viepagerNotifjasa)
        btn_tabs = view.findViewById(R.id.btn_tabsNotifjasa)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(NotifProdukjasaFragment(), "Notifikasi Pengajuan Jasa")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)

        btn_tabs.getTabAt(0)!!.setIcon(R.drawable.jasa)

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