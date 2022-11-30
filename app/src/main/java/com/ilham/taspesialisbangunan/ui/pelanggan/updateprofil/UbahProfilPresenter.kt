package com.ilham.taspesialisbangunan.ui.pelanggan.updateprofil

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.user.DataUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.user.ResponsePelangganUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UbahProfilPresenter (val view: UbahProfilContract.View) : UbahProfilContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getDetailProfil(id: String) {
        view.onLoading(true,"Mendapatkan Detail...")
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailProfil( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updateProfil(
        id: Long,
        username: String,
        alamat: String,
        phone: String,
        phone_baru: String,
        gambar: File?,
        ) {

        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (gambar != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
            multipartBody = MultipartBody.Part.createFormData("gambar",
                gambar.name, requestBody)
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody= MultipartBody.Part.createFormData("gambar",
                "", requestBody)
        }

        view.onLoading(true)
        ApiConfig.endpoint.updatePelanggan(id, username, alamat, phone, phone_baru, multipartBody ,"PUT") .enqueue(object : Callback<ResponsePelangganUpdate> {
            override fun onResponse(
                call: Call<ResponsePelangganUpdate>,
                response: Response<ResponsePelangganUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePelangganUpdate: ResponsePelangganUpdate? = response.body()
                    view.onResultUpdateProfil( responsePelangganUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponsePelangganUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUsername = dataUser.username!!
        prefsManager.prefsEmail = dataUser.email!!
        prefsManager.prefsAlamat = dataUser.alamat!!
        prefsManager.prefsPhone = dataUser.phone!!
    }
}