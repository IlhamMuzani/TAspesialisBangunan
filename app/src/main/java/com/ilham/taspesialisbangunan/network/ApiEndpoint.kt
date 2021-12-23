package com.ilham.taspesialisbangunan.network

import com.ilham.taspesialisbangunan.data.model.aduanjasa.ResponseAduanInsert
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialDetail
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialList
import com.ilham.taspesialisbangunan.data.model.material.ResponseMaterialUpdate
import com.ilham.taspesialisbangunan.data.model.pengajuan.*
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.register.ResponseModel
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekDetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail
import com.ilham.taspesialisbangunan.data.model.userpelanggan.ResponsePelangganUpdate
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {


//    REGISTRASI DAN LOGIN PELANGGAN

    @FormUrlEncoded
    @POST("register_pelanggan")
    fun register_pelanggan(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("login_pelanggan")
    fun login_pelanggan(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseUser>

    //    REGISTRASI DAN LOGIN JASA
//
    @FormUrlEncoded
    @POST("register_jasa")
    fun register_jasa(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("login_jasa")
    fun login_jasa(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseUser>

    @GET("userDetail/{id}")
    fun userDetail(
        @Path("id") id: String
    ): Call<ResponseUserdetail>

    //    UBAH PROFIL PELANGGAN
//
    @GET("userpelanggan/{id}")
    fun getPelangganDetail(
        @Path("id") id: Long
    ): Call<ResponseUserdetail>

    @POST("userpelanggan/{id}")
    fun updatePelanggan(
        @Path("id") id: Long,
        @Query("username") username: String,
        @Query("email") email: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("_method") _method: String
    ): Call<ResponsePelangganUpdate>

    @GET("ProfilDetail/{id}")
    fun ProfilDetail(
        @Path("id") id: String
    ): Call<ResponseUserdetail>

    //    PRODUK
//
    @GET("produkjasa")
    fun getProduk(): Call<ResponseProdukList>

    @GET("produkmaterial")
    fun getProdukmaterial(): Call<ResponseProdukList>

    @GET("myproduct/{kd_user}")
    fun myproduct(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseProdukList>

    @GET("search_produkjasa")
    fun searchProdukjasa(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    @GET("produk_detail/{id}")
    fun produkDetail(
        @Path("id") id: Long
    ): Call<ResponseProdukDetail>

    @GET("mymaterials/{kd_user}")
    fun mymaterials(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseProdukList>

    @GET("search_material")
    fun searchMaterial(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    @GET("produkjasa_detail/{id}")
    fun produkjasaDetail(
        @Path("id") id: Long
    ): Call<ResponseProdukDetail>


    @Multipart
    @POST("produkjasa")
    fun insertProduk(
        @Query("kd_user") kd_user: String,
        @Query("nama_toko") nama_toko: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
    ): Call<ResponseProdukUpdate>

    @Multipart
    @POST("produkmaterial")
    fun insertProdukmaterial(
        @Query("kd_user") kd_user: String,
        @Query("nama_toko") nama_toko: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
    ): Call<ResponseProdukUpdate>

    @Multipart
    @POST("produkjasa/{kd_produkjasa}")
    fun updateProduk(
        @Path("kd_produkjasa") kd_produkjasa: Long,
        @Query("nama_toko") nama_toko: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("_method") _method: String
    ): Call<ResponseProdukUpdate>

    @DELETE("produkjasa/{kd_produkjasa}")
    fun deleteProduk(
        @Path("kd_produkjasa") kd_produkjasa: Long
    ): Call<ResponseProdukUpdate>

    //    MATERIAL
//
    @GET("material")
    fun getMaterial(): Call<ResponseMaterialList>

    @POST("myproductmaterial")
    fun myproductmaterial(
        @Query("kd_user") kd_user: String,
    ): Call<ResponseMaterialList>


    @Multipart
    @POST("material")
    fun insertMaterial(
        @Query("jasausers_id") jasausers_id: String,
        @Query("nama_toko") nama_toko: String,
        @Query("jenis_material") jenis_material: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String
    ): Call<ResponseMaterialUpdate>

    @Multipart
    @POST("material/{kd_material}")
    fun updateMaterial(
        @Path("kd_material") kd_material: Long,
        @Query("nama_toko") nama_toko: String,
        @Query("jenis_material") jenis_material: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("_method") _method: String
    ): Call<ResponseMaterialUpdate>

    @DELETE("material/{kd_material}")
    fun deleteMaterial(
        @Path("kd_material") kd_material: Long
    ): Call<ResponseMaterialUpdate>

    //    PENGADUAN JASA
//
    @Multipart
    @POST("aduanjasa")
    fun insertAduanjasa(
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String
    ): Call<ResponseAduanInsert>

    //    PENGAJUAN JASA

    @GET("pengajuanjasamenunggu/{id}")
    fun pengajuanjasamenunggu(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasaditerima/{id}")
    fun pengajuanjasaditerima(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasaDP/{id}")
    fun pengajuanjasaDP(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasatampildiproses/{id}")
    fun pengajuanjasatampildiproses(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasatampilselesai/{id}")
    fun pengajuanjasatampilselesai(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @Multipart
    @POST("pengajuan")
    fun insertPengajuan(
        @Query("kd_produk") kd_produk: String,
        @Query("kd_user") kd_user: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("status") status: String
    ): Call<ResponsePengajuanInsert>

    @POST("mypengajuan")
    fun mypengajuan(
        @Query("kd_jasa") kd_jasa: String
    ): Call<ResponsePengajuanList>

    @GET("detailpengajuan/{id}")
    fun detailPengajuan(
        @Path("id") id: Long
    ): Call<ResponsePengajuanDetail>

    @POST("hargaPengajuan/{id}")
    fun hargaPengajuan(
        @Path("id") id: Long,
        @Query("harga") harga: String,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @Multipart
    @POST("buktiPengajuan/{kd_pengajuan}")
    fun buktiPengajuan(
        @Path("kd_pengajuan") kd_pengajuan: Long,
        @Part bukti: MultipartBody.Part,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanjasadiproses/{kd_pengajuan}")
    fun pengajuanjasadiproses(
        @Path("kd_pengajuan") kd_pengajuan: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanjasaprosesselesai/{kd_pengajuan}")
    fun pengajuanjasaprosesselesai(
        @Path("kd_pengajuan") kd_pengajuan: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanjasaprosesselesai/{kd_pengajuan}")
    fun pengajuanjasaselesai(
        @Path("kd_pengajuan") kd_pengajuan: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //    PENGAJUAN PELANGGAN

    @GET("pengajuanterima/{kd_pengajuan}")
    fun Pengajuanterima(
        @Path("kd_pengajuan") kd_pengajuan: Long
    ): Call<ResponsePengajuanUpdate>

    @GET("pengajuantolak/{kd_pengajuan}")
    fun Pengajuantolak(
        @Path("kd_pengajuan") kd_pengajuan: Long
    ): Call<ResponsePengajuanUpdate>

    @GET("pengajuanusermenunggu/{kd_userpelanggan}")
    fun Pengajuanusermenunggu(
        @Path("kd_userpelanggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserditerima/{kd_userpelenggan}")
    fun Pengajuanuserditerima(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserDP/{kd_userpelenggan}")
    fun PengajuanuserDP(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserdiproses/{kd_userpelenggan}")
    fun Pengajuanuserdiproses(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserselesai/{kd_userpelenggan}")
    fun Pengajuanuserselesai(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    //      SARAN
//
    @GET("saran")
    fun getSaran(): Call<ResponseSaranList>

    @POST("saran")
    fun insertSaran(
        @Query("kd_jasa") kd_jasa: String,
        @Query("kd_userpelanggan") kd_userpelanggan: String,
        @Query("deskripsi") deskripsi: String
    ): Call<ResponseSaranInsert>

    @POST("mysaran")
    fun mysaran(
        @Query("kd_jasa") kd_jasa: String,
    ): Call<ResponseSaranList>

    @POST("mynotifpelanggan")
    fun mynotifpelanggan(
        @Query("kd_userpelanggan") kd_userpelanggan: String
    ): Call<ResponsePengajuanList>

    //    TAMBAHREKENING
//
    @GET("tambahrek")
    fun getTambahrek(): Call<ResponseTambahrekList>

    @POST("myproducttambahrek")
    fun myproducttambahrek(
        @Query("jasausers_id") jasausers_id: String,
    ): Call<ResponseTambahrekList>

    @POST("tambahrek")
    fun insertTambahrek(
        @Query("jasausers_id") jasausers_id: String,
        @Query("jenis_bank") jenis_bank: String,
        @Query("norek") norek: String,
        @Query("nama") nama: String
    ): Call<ResponseTambahrekUpdate>

    @GET("tambahrek/{kd_rekening}")
    fun getTambahrekDetail(
        @Path("kd_rekening") kd_rekening: Long
    ): Call<ResponseTambahrekDetail>

    @POST("tambahrek/{kd_rekening}")
    fun updateTambahrek(
        @Path("kd_rekening") kd_rekening: Long,
        @Query("jenis_bank") jenis_bank: String,
        @Query("norek") norek: String,
        @Query("nama") nama: String,
        @Query("_method") _method: String
    ): Call<ResponseTambahrekUpdate>

    @DELETE("tambahrek/{kd_rekening}")
    fun deleteTambahrek(
        @Path("kd_rekening") kd_rekening: Long
    ): Call<ResponseTambahrekUpdate>

    @GET("productrekening/{kd_jasa}")
    fun produkrekeningtampil(
        @Path("kd_jasa") kd_jasa: String,
    ): Call<ResponseTambahrekList>

}