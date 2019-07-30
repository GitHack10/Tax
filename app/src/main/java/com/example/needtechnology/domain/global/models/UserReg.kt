package com.example.needtechnology.domain.global.models

import com.google.gson.annotations.SerializedName

data class UserReg(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("birthday") val birth: String,
    @SerializedName("gender") val gender: Int
)