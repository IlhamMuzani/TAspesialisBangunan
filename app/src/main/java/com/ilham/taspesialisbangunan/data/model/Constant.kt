package com.ilham.taspesialisbangunan.data.model

class Constant {
    companion object {
//        var IP: String = "http://192.168.43.224/api_spesialisJB/"
//        var IP: String = "http://192.168.1.9/api_spesialisJB/"
//        var IP: String = "http://192.168.1.88/api_spesialisJB/"
        var IP: String = "https://spesialisjb.ufomediategal.com/"
        var IP_IMAGE: String = IP + "public/storage/uploads/"

        var LATITUDE: String = ""
        var LONGITUDE: String = ""
        const val LOCATION_PERMISSION_REQUEST_CODE = 1;

        var USERPELANGGAN_ID: Long = 0
        var USERJASA_ID: Long = 0

        var FORGET: Boolean = false

            var UPDATE: Boolean = false

        var PRODUK_ID: Long = 0
        var PENGAJUAN_ID: Long = 0

        var MATERIAL_ID: Long = 0
        var MATERIAL_NAME: String = ""

        var TAMBAHREK_ID: Long = 0
        var TAMBAHREK_NAME: String = ""

        var CATEGORY_ID: Int = 0
        var CATEGORY_NAME: String = ""

        var CATEGORIPESANAN_ID: Int = 0
        var CATEGORIPESANAN_NAME: String = ""

        var BAHANBESI_ID: Int = 0
        var BAHANBESI_NAME: String = ""

        var WARNA_ID: Int = 0
        var WARNA_NAME: String = ""

        var HARGA_ID: Int = 0
        var HARGA_NAME: String = ""

        var KECAMATAN_ID: Int = 0
        var KECAMATAN_NAME: String = ""

        var KELURAHAN_ID: Int = 0
        var KELURAHAN_NAME: String = ""

        var PANJANG_ID: Int = 0
        var PANJANG_NAME: String = ""

        var PANJANGCREATE_ID: Int = 0
        var PANJANGCREATE_NAME: String = ""

        var LEBAR_ID: Int = 0
        var LEBAR_NAME: String = ""

        var UKURAN_ID: Int = 0
        var UKURAN_NAME: String = ""

        var BANK_ID: Int = 0
        var BANK_NAME: String = ""

        var JENISPEMBUATAN_ID: Int = 0
        var JENISPEMBUATAN_NAME: String = ""

        var KELURAHANPRODUK_ID: Int = 0
        var KELURAHANPRODUK_NAME: String = ""

        var RtRw_ID: Int = 0
        var RtRw_NAME: String = ""

        var STATUSDP_ID: Int = 0
        var STATUSDP_NAME: String = ""



        var SARAN_ID: Long = 0

        var IS_CHANGED: Boolean = false
    }
}