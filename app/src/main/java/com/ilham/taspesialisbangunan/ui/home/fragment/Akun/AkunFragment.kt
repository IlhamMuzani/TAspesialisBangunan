package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.home.UserActivity
import com.ilham.taspesialisbangunan.ui.home.fragment.Akun.profil.updateprofil.UbahProfilActivity


class AkunFragment : Fragment(), AkunuserContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunuserPresenter

    //user
    lateinit var BtnUbahProfil : RelativeLayout
    lateinit var TxvAkunUser : TextView
    lateinit var TxvEmailUser : TextView
    lateinit var TxvAlamatuser : TextView
    lateinit var Txvphoneuser : TextView
    lateinit var TxvLogoutUser : TextView

    //jasa
    lateinit var BtnUbahProfiljasa : RelativeLayout
    lateinit var TxvAkunUserjasa : TextView
    lateinit var TxvEmailUserjasa : TextView
    lateinit var TxvAlamatuserjasa : TextView
    lateinit var Txvphoneuserjasa : TextView
    lateinit var TxvLogoutUserjasa : TextView

    lateinit var layoutprofilpelanggan: LinearLayout
    lateinit var layoutprofiljasa: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_akun, container, false)

        initFragment(view)

        prefsManager = PrefsManager(requireActivity())
        presenter = AkunuserPresenter(this)
        presenter.doLogin(prefsManager)


        return view
    }

    override fun initFragment(view: View) {
//        supportActionBar!!.hide()

        BtnUbahProfil = view.findViewById(R.id.btn_ubahProfil)
        TxvAkunUser= view.findViewById(R.id.txvAkunuser)
        TxvEmailUser = view.findViewById(R.id.txvEmailuser)
        TxvAlamatuser = view.findViewById(R.id.txvAlamatuser)
        Txvphoneuser = view.findViewById(R.id.txvPhoneuser)
        TxvLogoutUser = view.findViewById(R.id.txvLogoutuser)
        layoutprofilpelanggan = view.findViewById(R.id.layout_profilPELANGGAN)

        BtnUbahProfiljasa = view.findViewById(R.id.btn_ubahProfiljasa)
        TxvAkunUserjasa= view.findViewById(R.id.txvAkunjasa)
        TxvEmailUserjasa = view.findViewById(R.id.txvEmailjasa)
        TxvAlamatuserjasa = view.findViewById(R.id.txvAlamatjasa)
        Txvphoneuserjasa = view.findViewById(R.id.txvPhonejasa)
        TxvLogoutUserjasa = view.findViewById(R.id.txvLogoutjasa)
        layoutprofiljasa = view.findViewById(R.id.layout_profilJASA)


        BtnUbahProfil.setOnClickListener{
            Constant.USERPELANGGAN_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), UbahProfilActivity::class.java))
        }
        TxvLogoutUser.setOnClickListener {
            presenter.doLogout(prefsManager)
        }

        TxvLogoutUserjasa.setOnClickListener {
            presenter.doLogout(prefsManager)
        }

    }

    override fun onStart() {
        super.onStart()
        presenter.profilDetail(prefsManager.prefsId)

    }

    override fun onResultLogin(prefsManageruser: PrefsManager) {
        TxvAkunUser.text = prefsManageruser.prefsUsername
        TxvEmailUser.text = prefsManageruser.prefsEmail
        TxvAlamatuser.text = prefsManageruser.prefsAlamat
        Txvphoneuser.text = prefsManageruser.prefsPhone

        TxvAkunUserjasa.text = prefsManageruser.prefsUsername
        TxvEmailUserjasa.text = prefsManageruser.prefsEmail
        TxvAlamatuserjasa.text = prefsManageruser.prefsAlamat
        Txvphoneuserjasa.text = prefsManageruser.prefsPhone

    }

    override fun onResultLogout() {
//        requireActivity().finish()
        startActivity(Intent(requireActivity(), UserActivity::class.java))
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.user.status == "pelanggan"){
            layoutprofilpelanggan.visibility = View.VISIBLE
            layoutprofiljasa.visibility = View.GONE
        } else {
            layoutprofilpelanggan.visibility = View.GONE
            layoutprofiljasa.visibility = View.VISIBLE
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}