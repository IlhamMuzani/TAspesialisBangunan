package com.ilham.taspesialisbangunan.ui.pelanggan.lihattoko.KategoriToko

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriTokoPresenter (val view: KategoriTokoContract.View): KategoriTokoContract.Presenter {

    override fun getProduktampilkanopi(kd_user: Long, jenis_pembuatan: String) {
        view.onLoading(true,"Menampilkan Produk...")
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object :
            Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResulttampilkanopi( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getProduktampilpagar(kd_user: Long, jenis_pembuatan: String) {
        view.onLoading(true,"Menampilkan Produk...")
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object :
            Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResulttampilpagar( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getProduktampiltangga(kd_user: Long, jenis_pembuatan: String) {
        view.onLoading(true,"Menampilkan Produk...")
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object :
            Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResulttampiltangga( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getProduktampiltralis(kd_user: Long, jenis_pembuatan: String) {
        view.onLoading(true,"Menampilkan Produk...")
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object :
            Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResulttampiltralis( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun getProduktampilhalaman(kd_user: Long, jenis_pembuatan: String) {
        view.onLoading(true,"Menampilkan Produk...")
        ApiConfig.endpoint.tampilprodukperjenispembuatan(kd_user, jenis_pembuatan).enqueue(object :
            Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResulttampilhalaman( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}