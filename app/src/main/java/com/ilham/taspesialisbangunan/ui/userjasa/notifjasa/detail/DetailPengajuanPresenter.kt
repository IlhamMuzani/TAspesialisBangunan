package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSarandetail
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPengajuanPresenter(val view: DetailPengajuanContract.View): DetailPengajuanContract.Presenter {

   init {
       view.initActivity()
       view.initListener()
       view.onLoading(true)
   }
    override fun getDetail(id: Long) {
        view.onLoading(true,"Menampilkan Detail...")
        ApiConfig.endpoint.detailPengajuan(id).enqueue(object: Callback<ResponsePengajuanDetail>{
            override fun onResponse(
                call: Call<ResponsePengajuanDetail>,
                response: Response<ResponsePengajuanDetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanDetail: ResponsePengajuanDetail? = response.body()
                    view.onResultDetail(responsePengajuanDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanDetail>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun cektempatlokasi(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.cektempatpembuatan(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun hargaPengajuan(id: Long, harga: String, deskripsi_kesepakatan: String, status_dp: String,  panjang: String,  lebar: String) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.hargaPengajuan(id, harga, deskripsi_kesepakatan, status_dp, panjang, lebar, "PUT").enqueue(object: Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateharga(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun pengajuanjasaDPkeProses(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.pengajuanjasaDPkeProses(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun pengajuanjasaDPkeProsesbayarcash(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.pengajuanjasaDPkeProsesbayarcash(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun pengajuanjasaProseskePelunasan(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.pengajuanjasaProseskePelunasan(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun pengajuanjasaProseskePelunasanbayarcash(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.pengajuanjasaProseskePelunasanbayarcash(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultUpdateproses(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getSaranperproduk(id: Long) {
        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.saranperproduk(id).enqueue(object : Callback<ResponseSaranList> {
            override fun onResponse(
                call: Call<ResponseSaranList>,
                response: Response<ResponseSaranList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseSaranList: ResponseSaranList? = response.body()
                    view.onResultSaranJasaProduk(responseSaranList!!)
                }
            }

            override fun onFailure(call: Call<ResponseSaranList>, t: Throwable) {
                view.onLoading(false)
            }
        })
    }

    override fun deletePengajuanjasa(id: Long) {
//        view.onLoading(true,"Loading...")
        ApiConfig.endpoint.deletePengajuanmenunggu(id).enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultDelete( responsePengajuanUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}