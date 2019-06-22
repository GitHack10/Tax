package com.example.needtechnology.data.global.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Created by Kamil Abdulatipov on 24.04.2019. */

interface PreferenceStorage {
    var phone: String
    var userCount: String
    var cardCode: String
    var cardBalance: String
    var holderId: String
    var isLogin: Boolean
}

class SharedPreferenceStorage @Inject constructor(context: Context) : PreferenceStorage {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)

    override var phone by StringPreference(prefs, PREF_PHONE, "")

    override var userCount by StringPreference(prefs, PREF_USER_COUNT, "")

    override var cardCode by StringPreference(prefs, PREF_CARD_CODE, "")

    override var cardBalance by StringPreference(prefs, PREF_CARD_BALANCE, "0")

    override var holderId by StringPreference(prefs, PREF_HOLDER_ID, "")

    override var isLogin by BooleanPreference(prefs, PREF_IS_LOGIN, false)

    companion object {
        const val PREF_PHONE = "PREF_PHONE"
        const val PREF_USER_COUNT = "PREF_USER_COUNT"
        const val PREF_PROFILE = "PROFILE_STORE"
        const val PREF_CARD_CODE = "PREF_CARD_CODE"
        const val PREF_CARD_BALANCE = "PREF_CARD_BALANCE"
        const val PREF_HOLDER_ID = "PREF_HOLDER_ID"
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
