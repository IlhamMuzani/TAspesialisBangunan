package com.ilham.taspesialisbangunan.ui.fragment.Akun

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.ui.fragment.UserActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.passwordbaru.PasswordbaruActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.UbahProfilActivity
import com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.perbaruipassword.PerbaruiPasswordActivity
import com.ilham.taspesialisbangunan.ui.userjasa.detailalamat.DetailAlamatActivity
import com.ilham.taspesialisbangunan.ui.userjasa.tambahrek.TambahRekActivity
import com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa.UbahProfiljasaActivity
import com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa.perbaruipasswordjasa.PerbaruiPasswordjasaActivity
import com.ilham.taspesialisbangunan.ui.utils.GlideHelper
import com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog


class AkunFragment : Fragment(), AkunuserContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunuserPresenter
//    lateinit var user: DataUser
    lateinit var sLoading: SweetAlertDialog

    //user
    lateinit var BtnUbahProfil : RelativeLayout
    lateinit var Btnubahpasswordbaru : RelativeLayout
    lateinit var imvAkunUser : ImageView
    lateinit var TxvAkunUser : TextView
//    lateinit var TxvEmailUser : TextView
    lateinit var TxvAlamatuser : TextView
    lateinit var Txvphoneuser : TextView
    lateinit var TxvLogoutUser : TextView
    lateinit var progresakun : ProgressBar

    //jasa
    lateinit var BtnUbahProfiljasa : RelativeLayout
    lateinit var Btnubahpasswordbarujasa : RelativeLayout
    lateinit var Btndetailalamatjasa : RelativeLayout
    lateinit var ImvAkunJasa : ImageView
    lateinit var TxvAkunUserjasa : TextView
//    lateinit var TxvEmailUserjasa : TextView
    lateinit var Txvphoneuserjasa : TextView
    lateinit var TxvLogoutUserjasa : TextView
    lateinit var tambahrek : RelativeLayout
    lateinit var txtnamatoko : TextView

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

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun initFragment(view: View) {
//        supportActionBar!!.hide()

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)

//        progresakun = view.findViewById(R.id.progressakun)
        BtnUbahProfil = view.findViewById(R.id.btn_ubahProfil)
        Btnubahpasswordbaru = view.findViewById(R.id.btn_ubahpassword)
        imvAkunUser= view.findViewById(R.id.imvAkunuser)
        TxvAkunUser= view.findViewById(R.id.txvAkunuser)
//        TxvEmailUser = view.findViewById(R.id.txvEmailuser)
        TxvAlamatuser = view.findViewById(R.id.txvAlamatuser)
        Txvphoneuser = view.findViewById(R.id.txvPhoneuser)
        TxvLogoutUser = view.findViewById(R.id.txvLogoutuser)
        layoutprofilpelanggan = view.findViewById(R.id.layout_profilPELANGGAN)



        BtnUbahProfiljasa = view.findViewById(R.id.btn_ubahProfiljasa)
        Btnubahpasswordbarujasa = view.findViewById(R.id.btn_ubahpasswordjasa)
        TxvAkunUserjasa= view.findViewById(R.id.txvAkunjasa)
//        TxvEmailUserjasa = view.findViewById(R.id.txvEmailjasa)
        ImvAkunJasa= view.findViewById(R.id.imvAkunjasa)
        Txvphoneuserjasa = view.findViewById(R.id.txvPhonejasa)
        TxvLogoutUserjasa = view.findViewById(R.id.txvLogoutjasa)
        layoutprofiljasa = view.findViewById(R.id.layout_profilJASA)
        Btndetailalamatjasa = view.findViewById(R.id.btn_detailalamat)
        tambahrek = view.findViewById(R.id.btn_tambahrek)
        txtnamatoko = view.findViewById(R.id.txtNamatoko)



        BtnUbahProfil.setOnClickListener{
            Constant.USERPELANGGAN_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), UbahProfilActivity::class.java))
        }

        Btnubahpasswordbaru.setOnClickListener {
            Constant.USERPELANGGAN_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), PerbaruiPasswordActivity::class.java))
        }

        BtnUbahProfiljasa.setOnClickListener {
            Constant.USERPELANGGAN_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), UbahProfiljasaActivity::class.java ))
        }

        Btnubahpasswordbarujasa.setOnClickListener {
            Constant.USERJASA_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), PerbaruiPasswordjasaActivity::class.java))
        }

        Btndetailalamatjasa.setOnClickListener {
            Constant.USERJASA_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), DetailAlamatActivity::class.java ))
        }

        tambahrek.setOnClickListener{
            startActivity(Intent(requireContext(), TambahRekActivity::class.java))
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

    override fun onResultLogin(responseUser: ResponseUser) {

    }

    override fun onResultLogout() {
//        requireActivity().finish()
        startActivity(Intent(requireActivity(), UserActivity::class.java))
    }

    override fun onLoadingAkun(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        if (responseUserdetail.user.status == "pelanggan") {
            layoutprofilpelanggan.visibility = View.VISIBLE
            layoutprofiljasa.visibility = View.GONE

            val akun = responseUserdetail.user

            GlideHelper.setImage(requireActivity(), Constant.IP_IMAGE + akun!!.gambar!!, imvAkunUser)
            TxvAkunUser.setText(akun!!.username)
//            TxvEmailUser.setText(akun!!.email)
            TxvAlamatuser.setText(akun!!.alamat)
            Txvphoneuser.setText(akun!!.phone)

        } else {
                layoutprofilpelanggan.visibility = View.GONE
                layoutprofiljasa.visibility = View.VISIBLE

            val akun = responseUserdetail.user

            GlideHelper.setImage(requireActivity(), Constant.IP_IMAGE + akun!!.gambar!!, ImvAkunJasa)
            txtnamatoko.setText(akun!!.nama_toko)
            TxvAkunUserjasa.setText(akun!!.username)
//            TxvEmailUserjasa.setText(akun!!.email)
            Txvphoneuserjasa.setText(akun!!.phone)
        }
    }

    override fun onResultUpdate(responseUserUpdate: ResponseUserUpdate) {
        Toast.makeText(requireContext(),"Berhasil Menambahkan Foto Profil", Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvAkunUser.setImageURI(uriImage)
        }
    }
}