package com.ilham.taspesialisbangunan.ui.pelanggan.register.phoneferifi

import com.google.firebase.auth.PhoneAuthCredential
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import java.io.File

interface PhoneverifiContract {

    interface Presenter {

        fun getverifi(id: Long)

        fun phone_baru(id: Long, phone: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun showMessage(message: String)
        fun setupInput()
        fun callback()
        fun onResultDatauserupdate(userdetail: ResponseUserdetail)
        fun onResultphonebaru(responseUserUpdate: ResponseUserUpdate)
        fun startPhoneNumberVerification(phone: String)
        fun verifyPhoneNumberWithCode(verificationId: String?, code: String)
        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)
    }
}