package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("full_name") var fullName: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("date") var birth: String?,
    @SerializedName("gender") val gender: String
): Parcelable