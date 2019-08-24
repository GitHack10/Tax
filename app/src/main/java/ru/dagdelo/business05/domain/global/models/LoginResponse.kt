package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LoginResponse(
    @SerializedName("email") val email: String,
    @SerializedName("fullName") val name: String,
    @SerializedName("phone") val phone: String
): Parcelable