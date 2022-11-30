package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil.perbaruipassword

import android.widget.Toast
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.PUT
import java.io.File

class PasswordbaruPresenter(val view: PasswordbaruContract.View) : PasswordbaruContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun Perbaruipassword(id: Long,password: String, password_confirmation: String) {
        view.onLoading(true, "Loading...")
        ApiConfig.endpoint.Perbaruipassword(id,password, password_confirmation,"PUT")
            .enqueue(object : Callback<ResponseUserUpdate> {
                override fun onResponse(
                    call: Call<ResponseUserUpdate>,
                    response: Response<ResponseUserUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseUserupdate: ResponseUserUpdate? = response.body()
                        view.onResult( responseUserupdate!! )
                    }
                }

                override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }
}