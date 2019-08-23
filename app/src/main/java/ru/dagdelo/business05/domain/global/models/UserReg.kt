package ru.dagdelo.business05.domain.global.models

import com.google.gson.annotations.SerializedName

data class UserReg(
    @SerializedName("full_name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("birthday") val birth: String
)