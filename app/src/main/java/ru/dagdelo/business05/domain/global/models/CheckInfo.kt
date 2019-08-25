package ru.dagdelo.business05.domain.global.models

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class CheckInfo(
    @SerializedName("totalSum") val totalSum: String,
    @SerializedName("status") val status: Status,
    @SerializedName("inn") val inn: String,
    @SerializedName("date_scan") val dateScan: String,
    @SerializedName("date_check") var dateCheck: String,
    @SerializedName("address") val address: String
) : Parcelable {

    @Parcelize
    data class Status(
        @SerializedName("status") val type: Int,
        @SerializedName("message") val message: String
    ) : Parcelable

    @SuppressLint("SimpleDateFormat")
    fun convertTime() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(dateCheck)

        dateCheck = SimpleDateFormat("dd.MM.yyyy' 'HH:mm").format(calendar.time)
    }
}