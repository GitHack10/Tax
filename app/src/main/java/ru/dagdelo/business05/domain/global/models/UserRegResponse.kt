package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRegResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("token") val token: String,
    @SerializedName("success") val success: Boolean
): Parcelable