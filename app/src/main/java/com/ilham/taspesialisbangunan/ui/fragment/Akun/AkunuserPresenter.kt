package com.ilham.taspesialisbangunan.ui.fragment.Akun

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
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
import java.io.File

class AkunuserPresenter (val view: AkunuserContract.View): AkunuserContract.Presenter {

    override fun doLogout(prefsManager: PrefsManager) {
        prefsManager.logout()
        view.showMessage("Berhasil Keluar")
        view.onResultLogout()
    }

    override fun profilDetail(id: String) {
        view.onLoadingAkun(true,"Menampilkan Data...")
        ApiConfig.endpoint.userDetail(id).enqueue(object : Callback<ResponseUserdetail>{
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoadingAkun(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    if (responseUserdetail!!.status)
                        view.onResult(responseUserdetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoadingAkun(false)
            }

        })
    }

    override fun fotoProfil(id: Long, gambar: File) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("gambar",
            gambar.name, requestBody)
        view.onLoadingAkun(true)
        ApiConfig.endpoint.fotoprofil(id, multipartBody, "PUT").enqueue(object: Callback<ResponseUserUpdate>{
            override fun onResponse(
                call: Call<ResponseUserUpdate>,
                response: Response<ResponseUserUpdate>
            ) {
                view.onLoadingAkun(false)
                if (response.isSuccessful) {
                    val responseUserUpdate: ResponseUserUpdate? = response.body()
                    view.onResultUpdate(responseUserUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                view.onLoadingAkun(false)
            }
        })
    }

}