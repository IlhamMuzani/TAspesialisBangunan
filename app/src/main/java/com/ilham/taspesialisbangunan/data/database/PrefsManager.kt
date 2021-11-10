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
    private val PREFS_EMAIL: String = "prefs_is_email"
    private val PREFS_PASSWORD: String = "prefs_is_password"
    private val PREFS_ALAMAT: String = "prefs_is_alamat"
    private val PREFS_PHONE: String = "prefs_is_phone"

    override val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.applicationContext.getSharedPreferences(
            "SpesialisJB", Context.MODE_PRIVATE
        )
    }

    var prefsIsLogin by booleanPref(PREFS_IS_LOGIN, false)
    var prefsId by stringPref(PREFS_ID, "")
    var prefsUsername by stringPref(PREFS_USERNAME, "")
    var prefsEmail by stringPref(PREFS_EMAIL, "")
    var prefsPassword by stringPref(PREFS_PASSWORD, "")
    var prefsAlamat by stringPref(PREFS_ALAMAT, "")
    var prefsPhone by stringPref(PREFS_PHONE, "")

    fun logout(){
        sharedPreferences.edit().clear().apply()
    }
}