package ru.dagdelo.business05.data.global.netwotk.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isNetworkAvailable(context: Context): Boolean {
    val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = cm?.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}