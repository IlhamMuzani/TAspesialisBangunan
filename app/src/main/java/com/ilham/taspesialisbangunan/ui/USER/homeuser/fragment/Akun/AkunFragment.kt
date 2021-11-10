package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.ui.USER.homeuser.UserActivity
import com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.Akun.profil.updateprofil.UbahProfilActivity
import com.ilham.taspesialisbangunan.ui.USER.loginuser.Masuk.MasukActivity


class AkunFragment : Fragment(), AkunuserContract.View {

    lateinit var prefsManageruser: PrefsManageruser
    lateinit var presenter: AkunuserPresenter

    lateinit var BtnUbahProfil : RelativeLayout
    lateinit var TxvAkunUser : TextView
    lateinit var TxvEmailUser : TextView
    lateinit var TxvAlamatuser : TextView
    lateinit var Txvphoneuser : TextView
    lateinit var TxvLogoutUser : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_akun, container, false)

        initFragment(view)

        prefsManageruser = PrefsManageruser(requireActivity())
        presenter = AkunuserPresenter(this)
        presenter.doLogin(prefsManageruser)


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

        BtnUbahProfil.setOnClickListener{
            Constant.USERPELANGGAN_ID = prefsManageruser.prefsId.toLong()
            startActivity(Intent(requireActivity(), UbahProfilActivity::class.java))
        }
        TxvLogoutUser.setOnClickListener {
            presenter.doLogout(prefsManageruser)
        }

    }

    override fun onResultLogin(prefsManageruser: PrefsManageruser) {
        TxvAkunUser.text = prefsManageruser.prefsUsername
        TxvEmailUser.text = prefsManageruser.prefsEmail
        TxvAlamatuser.text = prefsManageruser.prefsAlamat
        Txvphoneuser.text = prefsManageruser.prefsPhone

    }

    override fun onResultLogout() {
//        requireActivity().finish()
        startActivity(Intent(requireActivity(), UserActivity::class.java))
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}