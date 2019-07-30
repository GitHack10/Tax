package com.example.needtechnology.data.global.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Created by Kamil Abdulatipov on 22.06.2019. */

interface PreferenceStorage {
    var username: String
    var email: String
    var phone: String
    var password: String
    var maskedPhone: String
    var birth: String
    var gender: String
    var isLogin: Boolean
    var deviceId: String
    var token: String
}

class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)

    override var username by StringPreference(prefs, PREF_USERNAME, "")

    override var email by StringPreference(prefs, PREF_EMAIL, "")

    override var phone by StringPreference(prefs, PREF_PHONE, "")

    override var password by StringPreference(prefs, PREF_PASSWORD, "")

    override var maskedPhone by StringPreference(prefs, PREF_MASKED_PHONE, "")

    override var birth by StringPreference(prefs, PREF_BIRTH, "")

    override var gender by StringPreference(prefs, PREF_GENDER, "")

    override var deviceId by StringPreference(prefs, PREF_DEVICE_ID, "")

    override var token by StringPreference(prefs, PREF_TOKEN, "")

    override var isLogin by BooleanPreference(prefs, PREF_IS_LOGIN, false)

    companion object {
        const val PREF_USERNAME = "PREF_USERNAME"
        const val PREF_EMAIL = "PREF_EMAIL"
        const val PREF_PHONE = "PREF_PHONE"
        const val PREF_PASSWORD = "PREF_PASSWORD"
        const val PREF_MASKED_PHONE = "PREF_MASKED_PHONE"
        const val PREF_BIRTH = "PREF_BIRTH"
        const val PREF_GENDER = "PREF_GENDER"
        const val PREF_PROFILE = "PROFILE_STORE"
        const val PREF_DEVICE_ID = "PREF_DEVICE_ID"
        const val PREF_TOKEN = "PREF_TOKEN"
        const val PREF_IS_LOGIN = "PREF_IS_LOGIN"
    }
}

class StringPreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return pref.getString(key, defaultValue)!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        pref.edit()
            .putString(key, value)
            .apply()
    }
}

class BooleanPreference(
    private val pref: SharedPreferences,
    private val key: String,
    private val defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        pref.edit().putBoolean(key, value).apply()

    override fun getValue(thisRef: Any, property: KProperty<*>) =
        pref.getBoolean(key, defaultValue)
}