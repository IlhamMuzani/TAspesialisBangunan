package com.ilham.taspesialisbangunan.ui.pelanggan.lupapassword

import android.widget.Toast
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LupapasswordPresenter(val view: LupapasswordContract.View) : LupapasswordContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)

    }

    override fun lupapassword_pelanggan(phone: String, status: String) {
        view.onLoading(true, "Loading...")
        ApiConfig.endpoint.lupapassword_pelanggan(phone, status)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseUser: ResponseUser? = response.body()
                        view.onResult( responseUser!! )
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}