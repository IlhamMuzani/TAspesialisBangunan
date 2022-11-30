package com.ilham.taspesialisbangunan.ui.pelanggan.produkuser_materialuser.tabs.produk.produkdetail

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanInsert
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProdukdetailPresenter  (var view: ProdukdetailContract.View) : ProdukdetailContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoadingProdukdetail(false)
    }

    override fun getProdukdetail(id: Long) {
        view.onLoadingProdukdetail(true)
        ApiConfig.endpoint.produkDetail(id).enqueue(object : Callback<ResponseProdukDetail>{
            override fun onResponse(
                call: Call<ResponseProdukDetail>,
                response: Response<ResponseProdukDetail>
            ) {
                view.onLoadingProdukdetail(false)
                if (response.isSuccessful) {
                    val responseProdukDetail: ResponseProdukDetail? = response.body()
                    view.onResultProdukdetail( responseProdukDetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukDetail>, t: Throwable) {
                view.onLoadingProdukdetail(false)
            }
        } )
    }

    override fun getsaranproduk(id: Long) {
        view.onLoadingProdukdetail(true)
        ApiConfig.endpoint.semuasaranperproduk(id).enqueue(object : Callback<ResponseSaranList> {
            override fun onResponse(
                call: Call<ResponseSaranList>,
                response: Response<ResponseSaranList>
            ) {
                view.onLoadingProdukdetail(false)
                if (response.isSuccessful) {
                    val responseSaranList: ResponseSaranList? = response.body()
                    view.onResultSaranProduk(responseSaranList!!)
                }
            }

            override fun onFailure(call: Call<ResponseSaranList>, t: Throwable) {
                view.onLoadingProdukdetail(false)
            }
        })
    }

    override fun getProduklainya() {
        view.onLoadingProdukdetail(true)
        ApiConfig.endpoint.getProduk().enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProdukdetail(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduklainya( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProdukdetail(false)
            }

        })
    }

    override fun getRating(id: Long) {
        view.onLoadingProdukdetail(true)
        ApiConfig.endpoint.getRating(id).enqueue(object : Callback<ResponseRating> {
            override fun onResponse(
                call: Call<ResponseRating>,
                response: Response<ResponseRating>
            ) {
                view.onLoadingProdukdetail(false)
                if (response.isSuccessful) {
                    val responseRating: ResponseRating? = response.body()
                    view.onResultgetRating( responseRating!! )
                }
            }

            override fun onFailure(call: Call<ResponseRating>, t: Throwable) {
                view.onLoadingProdukdetail(false)
            }

        })
    }
}