package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckInfo(
    @SerializedName("totalSum") val totalSum: String,
    @SerializedName("status") val status: Int,
    @SerializedName("date_scan") val date: String
): Parcelable