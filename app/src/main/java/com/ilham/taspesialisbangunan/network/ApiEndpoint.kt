package com.ilham.taspesialisbangunan.network

import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.bahanproduk.ResponseBahanprodukList
import com.ilham.taspesialisbangunan.data.model.harga.ResponseHargaList
import com.ilham.taspesialisbangunan.data.model.pengajuan.*
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukDetail
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.register.ResponseModel
import com.ilham.taspesialisbangunan.data.model.saran.ResponseRating
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranInsert
import com.ilham.taspesialisbangunan.data.model.saran.ResponseSaranList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekDetail
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekTampilrekening
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate
import com.ilham.taspesialisbangunan.data.model.user.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {


//    REGISTRASI DAN LOGIN PELANGGAN

    @FormUrlEncoded
    @POST("register_pelanggan")
    fun register_pelanggan(
        @Field("username") username: String,
//        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String,
        ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("login_pelanggan")
    fun login_pelanggan(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String
    ): Call<ResponseUser>

    @POST("lupapassword_pelanggan")
    fun lupapassword_pelanggan(
        @Query("phone") phone: String,
        @Query("status") status: String,
    ): Call<ResponseUser>

    @POST("userUpdatePassword/{id}")
    fun Perbaruipassword(
        @Path("id") id: Long,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String,
        @Query("_method") _method: String
    ): Call<ResponseUserUpdate>

    @POST("lupapassword_jasa")
    fun lupapassword_jasa(
        @Query("phone") phone: String,
        @Query("status") status: String,
    ): Call<ResponseUser>

    //    UBAH PROFIL PELANGGAN

    @Multipart
    @POST("user/{id}")
    fun updatePelanggan(
        @Path("id") id: Long,
        @Query("username") username: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("phone_baru") phone_baru: String,
        @Part gambar: MultipartBody.Part,
        @Query("_method") _method: String
    ): Call<ResponsePelangganUpdate>

    @Multipart
    @POST("fotoprofil/{id}")
    fun fotoprofil(
        @Path("id") id: Long,
        @Part gambar: MultipartBody.Part,
        @Query("_method") method: String,
    ): Call<ResponseUserUpdate>


    @POST("passwordbaru_pelanggan/{id}")
    fun passwordbaru(
        @Path("id") id: Long,
        @Query("password") password: String,
        @Query("_method") method: String,
    ): Call<ResponseUserUpdate>

    @POST("userUpdatePhone/{id}")
    fun userupdatephone(
        @Path("id") id: Long,
        @Query("phone") phone: String,
        @Query("_method") method: String,
    ): Call<ResponseUserUpdate>


    //    REGISTRASI DAN LOGIN JASA
//
    @FormUrlEncoded
    @POST("register_jasa")
    fun register_jasa(
        @Field("username") username: String,
        @Field("nama_toko") nama_toko: String,
//        @Field("email") email: String,
        @Field("kecamatan") kecamatan: String,
        @Field("kelurahan") kelurahan: String,
        @Field("alamat") alamat: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String
    ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("login_jasa")
    fun login_jasa(
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("fcm") fcm: String
    ): Call<ResponseUser>

    //  UBAH PROFIL JASA

    @Multipart
    @POST("updateprofiljasa/{id}")
    fun updateJasa(
        @Path("id") id: Long,
        @Query("username") username: String,
        @Query("nama_toko") nama_toko: String,
        @Query("kecamatan") kecamatan: String,
        @Query("kelurahan") kelurahan: String,
        @Query("alamat") alamat: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("phone") phone: String,
        @Query("phone_baru") phone_baru: String,
        @Query("deskripsi") deskripsi: String,
        @Part gambar: MultipartBody.Part,
        @Query("_method") _method: String
    ): Call<ResponseUser>

    //  USER PROFIL DAN DETAIL

    @GET("userDetail/{id}")
    fun userDetail(
        @Path("id") id: String
    ): Call<ResponseUserdetail>


    //    PRODUK

    @GET("produkjasa")
    fun getProduk(): Call<ResponseProdukList>

    @GET("produkjasadanmaterial")
    fun getProdukdanmaterial(): Call<ResponseProdukList>


    @GET("commentRating/{id}")
    fun getRating(
        @Path("id") id: Long
    ): Call<ResponseRating>

    @GET("produkmaterial")
    fun getProdukmaterial(): Call<ResponseProdukList>

    @GET("myproduct/{kd_user}")
    fun myproduct(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseProdukList>

    @GET("tampilprodukuser")
    fun mytampilprodukuser(
    ): Call<ResponseProdukList>

    @GET("tampilprodukpertoko/{kd_user}")
    fun tampilprodukperuser(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseProdukList>

    @POST("tampilprodukperjenispembuatan/{kd_user}")
    fun tampilprodukperjenispembuatan(
        @Path("kd_user") kd_user: Long,
        @Query("jenis_pembuatan") jenis_pembuatan: String
    ): Call<ResponseProdukList>

    @GET("search_produkjasa")
    fun searchProdukjasa(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    //spinner dengan search jenispembuatan
    @GET("searchjenispembuatan")
    fun searchJenisPembuatan(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    //spinner dengan search bahanproduk
    @GET("searchbahanproduk")
    fun searchBahanproduk(
        @Query("keyword") keyword: String
    ): Call<ResponseBahanprodukList>

    @GET("searchalamatkecamatan")
    fun searchAlamatkecamatan(
        @Query("keyword") keyword: String
    ): Call<ResponseALamatList>

    @GET("searchukuranjasadanmaterialkanopi")
    fun searchukuranjasadanmaterialkanopi(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchukuranjasadanmaterialpagar")
    fun searchukuranjasadanmaterialpagar(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchukuranjasadanmaterialtangga")
    fun searchukuranjasadanmaterialtangga(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchukuranjasadanmaterialtralis")
    fun searchukuranjasadanmaterialtralis(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchukuranjasadanmaterialhalaman")
    fun searchukuranjasadanmaterialhalaman(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>



    @GET("searchukuranjasasaja")
    fun searchukuranjasasaja(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasasaja")
    fun searchHargaprodukjasasaja(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasadanmaterial")
    fun searchHargajasadanmaterial(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasadanmaterialpagar")
    fun searchHargajasadanmaterialpagar(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasadanmaterialtangga")
    fun searchHargajasadanmaterialtangga(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasadanmaterialtralis")
    fun searchHargajasadanmaterialtralis(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>

    @GET("searchhargaprodukjasadanmaterialhalaman")
    fun searchHargajasadanmaterialhalaman(
        @Query("keyword") keyword: String
    ): Call<ResponseHargaList>


    @GET("searchkecamatan")
    fun searchkecamatan(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    @GET("userVerify/{id}")
    fun userverifi(
        @Path("id") id: Long
    ): Call<ResponseUserdetail>

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
        @Query("category") category: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("model") model: String,
        @Query("ukuran") ukuran: String,
        @Query("kecamatan") kecamatan: String,
        @Query("kelurahan") kelurahan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
    ): Call<ResponseProdukUpdate>

    @Multipart
    @POST("tambahjasasaja")
    fun insertProdukjasasaja(
        @Query("kd_user") kd_user: String,
        @Query("category") category: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("model") model: String,
        @Query("kecamatan") kecamatan: String,
        @Query("kelurahan") kelurahan: String,
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
        @Query("category") category: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("model") model: String,
        @Query("ukuran") ukuran: String,
        @Query("kecamatan") kecamatan: String,
        @Query("kelurahan") kelurahan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("harga") harga: String,
        @Query("latitude") latidude: String,
        @Query("longitude") longitude: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("_method") _method: String
    ): Call<ResponseProdukUpdate>

    @Multipart
    @POST("updatejasasaja/{id}")
    fun updateProdukjasasaja(
        @Path("id") id: Long,
        @Query("category") category: String,
        @Query("jenis_pembuatan") jenis_pembuatan: String,
        @Query("model") model: String,
        @Query("kecamatan") kecamatan: String,
        @Query("kelurahan") kelurahan: String,
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


    //    PENGAJUAN JASA

    //TAMPIL
    @GET("pengajuanjasamenunggu/{id}")
    fun pengajuanjasamenunggu(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasadikonfirmasi/{id}")
    fun PengajuanjasaDikonfirmasi(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasasudahbertemu/{id}")
    fun Pengajuanjasabertemu(
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

    @GET("pengajuanjasatampilpelunasan/{id}")
    fun pengajuanjasatampilpelunasan(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanjasatampilselesai/{id}")
    fun pengajuanjasatampilselesai(
        @Path("id") id: String,
    ): Call<ResponsePengajuanList1>



    //PROSES MENUNGGU KE DITERIMA
    //Memberi harga ke pelanggan
    @POST("hargaPengajuan/{id}")
    fun hargaPengajuan(
        @Path("id") id: Long,
        @Query("harga") harga: String,
        @Query("deskripsi_kesepakatan") deskripsi_kesepakatan: String,
        @Query("status_dp") status_dp: String,
        @Query("panjang") panjang: String,
        @Query("lebar") lebar: String,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    //tolak kesepakatan
    @POST("tolakkesepakatan/{id}")
    fun tolakKesepakatan(
        @Path("id") id: Long,
        @Query("pesan") pesan: String,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //konfirmasi ke tempat lokasi
    @POST("cektempatpembuatan/{id}")
    fun cektempatpembuatan(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //  PROSES DP KE DIPROSES

    @POST("pengajuanjasadpkeproses/{id}")
    fun pengajuanjasaDPkeProses(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanjasadpkeprosesbayarcash/{id}")
    fun pengajuanjasaDPkeProsesbayarcash(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    //PROSES DIPROSES KE PELUNASAN

    @POST("pengajuanjasaproseskepelunasan/{id}")
    fun pengajuanjasaProseskePelunasan(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanjasaproseskepelunasanbayarcash/{id}")
    fun pengajuanjasaProseskePelunasanbayarcash(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    @POST("pengajuanusersudahbertemu/{id}")
    fun pengajuanuserkonfirmasibertemu(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanbayarditempat/{id}")
    fun pengajuanbayarditempat(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @POST("pengajuanuserselesaicash/{id}")
    fun pengajuanselesaicash(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //Selesai Ke histori Bagian User
    @POST("pengajuanselesaikehistori/{id}")
    fun pengajuanselesaikehistori(
        @Path("id") id: Long,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //    PENGAJUAN PELANGGAN

    //  MELAKUKAN PENGAJUAN BAGIAN PELANGGAN
    @Multipart
    @POST("pengajuan")
    fun insertPengajuan(
        @Query("kd_produk") kd_produk: String,
        @Query("kd_user") kd_user: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("status") status: String,
        @Query("categori_pesanan") categori_pesanan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("category") category: String
    ): Call<ResponsePengajuanInsert>

    //PENGAJUAN JASA DAN MATERIAL
    @Multipart
    @POST("Pengajuanjasadanmaterial")
    fun insertPengajuanjasadanmaterial(
        @Query("kd_produk") kd_produk: String,
        @Query("kd_user") kd_user: String,
        @Part gambar: MultipartBody.Part,
        @Query("deskripsi") deskripsi: String,
        @Query("status") status: String,
        @Query("categori_pesanan") categori_pesanan: String,
        @Query("alamat") alamat: String,
        @Query("phone") phone: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("category") category: String
    ): Call<ResponsePengajuanInsert>

    // TAMPIL
    @GET("pengajuanusermenunggu/{kd_userpelanggan}")
    fun Pengajuanusermenunggu(
        @Path("kd_userpelanggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserdikonfirmasi/{kd_userpelanggan}")
    fun Pengajuanuserdikonfirmasi(
        @Path("kd_userpelanggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    @GET("pengajuanuserbertemu/{kd_userpelanggan}")
    fun Pengajuanuserbertemu(
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

    //tampil diproses user
    @GET("pengajuanuserdiproses/{kd_userpelenggan}")
    fun Pengajuanuserdiproses(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    //tampil pelunasan user
    @GET("pengajuanuserpelunasan/{kd_userpelenggan}")
    fun Pengajuanuserpelunasan(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>

    //tampil selesai user
    @GET("pengajuanuserselesai/{kd_userpelenggan}")
    fun Pengajuanuserselesai(
        @Path("kd_userpelenggan") kd_userpelenggan: Long
    ): Call<ResponsePengajuanList1>


    // DELETE PENGAJUAN

    @DELETE("pengajuan/{id}")
    fun deletePengajuanmenunggu(
        @Path("id") id: Long
    ): Call<ResponsePengajuanUpdate>

    //DETAIL PENGAJUAN

    @GET("detailpengajuan/{id}")
    fun detailPengajuan(
        @Path("id") id: Long
    ): Call<ResponsePengajuanDetail>

    //  PROSES DARI DITERIMA KE DP PELANGGAN

    @Multipart
    @POST("buktiPengajuan/{id}")
    fun buktiPengajuan(
        @Path("id") id: Long,
        @Part bukti: MultipartBody.Part,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>

    @Multipart
    @POST("buktiCast/{id}")
    fun buktiCast(
        @Path("id") id: Long,
        @Part bukti: MultipartBody.Part,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    // PROSES DARI DIPROSES KE PELUNASAN
    @Multipart
    @POST("buktiPelunasan/{id}")
    fun buktiPelunasan(
        @Path("id") id: Long,
        @Part bukti: MultipartBody.Part,
        @Query("_method") method: String,
    ): Call<ResponsePengajuanUpdate>


    //      SARAN

    @GET("saranperproduk/{id}")
    fun saranperproduk(
        @Path("id") id: Long,
    ): Call<ResponseSaranList>

    @GET("semuasaranperproduk/{id}")
    fun semuasaranperproduk(
        @Path("id") id: Long,
    ): Call<ResponseSaranList>

    @POST("saran")
    fun insertSaran(
        @Query("kd_produk") kd_produk: String,
        @Query("kd_user") kd_user: String,
        @Query("kd_pengajuan") kd_pengajuan: String,
        @Query("deskripsi") deskripsi: String,
        @Query("rating") rating: String,
        @Query("status") status: String
    ): Call<ResponseSaranInsert>


    //    TAMBAHREKENING

    @POST("myproducttambahrek")
    fun myproducttambahrek(
        @Query("kd_user") kd_user: String,
    ): Call<ResponseTambahrekList>

    @POST("semuasaranperproduk")
    fun saran(
        @Query("id") id: String,
    ): Call<ResponseSaranList>

    @POST("tambahrek")
    fun insertTambahrek(
        @Query("kd_user") kd_user: String,
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

    @GET("productrekening/{kd_user}")
    fun produkrekeningtampil(
        @Path("kd_user") kd_user: String,
    ): Call<ResponseTambahrekTampilrekening>

}