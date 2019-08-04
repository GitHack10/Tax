package ru.dagdelo.business05.domain.global.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Check(
    val fd: String,
    val fpd: String,
    val fn: String,
    var date: String,
    val type: Int,
    val sum: String
) {

    @SuppressLint("SimpleDateFormat")
    fun convertTime() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyyMMdd'T'HHmm").parse(date)

        date = SimpleDateFormat("dd.MM.yyyy' 'HH:mm").format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTimeSendFormat() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("dd.MM.yyyy' 'HH:mm").parse(date)

        date = SimpleDateFormat("yyyyMMdd'T'HHmmss").format(calendar.time)
    }
}