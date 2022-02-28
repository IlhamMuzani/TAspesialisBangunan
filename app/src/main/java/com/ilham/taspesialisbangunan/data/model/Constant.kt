package com.ilham.taspesialisbangunan.data.model

class Constant {
    companion object {
        var IP: String = "http://192.168.43.224/api_spesialisJB/"
//        var IP: String = "http://192.168.176.47/api_spesialisJB/"
//        var IP: String = "http://192.168.1.88/api_spesialisJB/"
        var IP_IMAGE: String = IP + "public/storage/uploads/"

        var LATITUDE: String = ""
        var LONGITUDE: String = ""
        const val LOCATION_PERMISSION_REQUEST_CODE = 1;

        var USERPELANGGAN_ID: Long = 0
        var USERJASA_ID: Long = 0

        var PRODUK_ID: Long = 0
        var PENGAJUAN_ID: Long = 0

        var MATERIAL_ID: Long = 0
        var MATERIAL_NAME: String = ""

        var TAMBAHREK_ID: Long = 0
        var TAMBAHREK_NAME: String = ""

        var SARAN_ID: Long = 0

        var IS_CHANGED: Boolean = false
    }
}