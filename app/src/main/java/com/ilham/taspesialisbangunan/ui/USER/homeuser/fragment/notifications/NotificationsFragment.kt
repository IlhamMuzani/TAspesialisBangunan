package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.ui.USER.notifpelanggan.NotifikasiPelangganActivity
import com.ilham.taspesialisjasabangunan.ui.produkuser.MatrialprodukActivity
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment(), NotificationsContract.View {

    lateinit var prefsManageruser: PrefsManageruser
    lateinit var presenter: NotificationsPresenter

    lateinit var BtnPesan : RelativeLayout
    lateinit var BtnKeranjang : RelativeLayout

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
        prefsManageruser = PrefsManageruser(requireActivity())


        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {
        BtnPesan = view.findViewById(R.id.btn_Pesanan)
        BtnKeranjang = view.findViewById(R.id.btn_keranjang)


        BtnPesan.setOnClickListener {
            if (prefsManageruser.prefsisLoginuser) {
                startActivity(Intent(requireActivity(), NotifikasiPelangganActivity::class.java))
            } else {
                showMessage("Silakan Login Terlebih Dahulu")
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}