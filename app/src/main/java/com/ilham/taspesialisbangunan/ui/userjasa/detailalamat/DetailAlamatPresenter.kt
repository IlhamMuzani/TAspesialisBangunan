package com.ilham.taspesialisbangunan.ui.userjasa.detailalamat

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DetailAlamatPresenter (val view: DetailAlamatContract.View) : DetailAlamatContract.Presenter {

    init {
        view.initActivity()
        view.initListener()

    }

    override fun getDetailProfiljasa(id: String) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailProfiljasa( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }


    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUsername = dataUser.username!!
        prefsManager.prefsEmail = dataUser.email!!
        prefsManager.prefsKecamatan = dataUser.kecamatan!!
        prefsManager.prefsKelurahan = dataUser.kelurahan!!
        prefsManager.prefsAlamat = dataUser.alamat!!
        prefsManager.prefsPhone = dataUser.phone!!
        prefsManager.prefsGambar = dataUser.gambar!!

    }
}