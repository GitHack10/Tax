package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Complaint(
    @SerializedName("complaint_description") val problemDesc: String,
    @SerializedName("place_address") val address: String,
    @SerializedName("date") val date: String
) : Parcelable