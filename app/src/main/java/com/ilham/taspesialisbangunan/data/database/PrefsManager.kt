package com.ilham.taspesialisbangunan.data.database

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.Krate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.stringPref

class PrefsManager(context: Context) : Krate {

    private val PREFS_IS_LOGIN: String = "prefs_is_login"
    private val PREFS_ID: String = "prefs_id"
    private val PREFS_USERNAME: String = "prefs_is_username"
    private val PREFS_NAMATOKO: String = "prefs_is_namatoko"
    private val PREFS_EMAIL: String = "prefs_is_email"
    private val PREFS_PASSWORD: String = "prefs_is_password"
    private val PREFS_KECAMATAN: String = "prefs_is_kecamatan"
    private val PREFS_KELURAHAN: String = "prefs_is_kelurahan"
    private val PREFS_ALAMAT: String = "prefs_is_alamat"
    private val PREFS_LATITUDE: String = "prefs_is_latitude"
    private val PREFS_LONGITUDE: String = "prefs_is_longitude"
    private val PREFS_PHONE: String = "prefs_is_phone"
    private val PREFS_STATUS: String = "prefs_is_status"
    private val PREFS_GAMBAR: String = "prefs_is_gambar"

    override val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.applicationContext.getSharedPreferences(
            "SpesialisJB", Context.MODE_PRIVATE
        )
    }

    var prefsIsLogin by booleanPref(PREFS_IS_LOGIN, false)
    var prefsId by stringPref(PREFS_ID, "")
    var prefsUsername by stringPref(PREFS_USERNAME, "")
    var prefsNamatoko by stringPref(PREFS_NAMATOKO, "")
    var prefsEmail by stringPref(PREFS_EMAIL, "")
    var prefsPassword by stringPref(PREFS_PASSWORD, "")
    var prefsKecamatan by stringPref(PREFS_KECAMATAN, "")
    var prefsKelurahan by stringPref(PREFS_KELURAHAN, "")
    var prefsAlamat by stringPref(PREFS_ALAMAT, "")
    var prefsLatitude by stringPref(PREFS_LATITUDE, "")
    var prefsLongitude by stringPref(PREFS_LONGITUDE, "")
    var prefsPhone by stringPref(PREFS_PHONE, "")
    var prefsStatus by stringPref(PREFS_STATUS, "")
    var prefsGambar by stringPref(PREFS_GAMBAR, "")


    fun logout(){
        sharedPreferences.edit().clear().apply()
    }
}