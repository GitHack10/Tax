package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.format.DateTimeFormatter

@Parcelize
data class CheckInfo(
    @SerializedName("id") val id: Long,
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
}