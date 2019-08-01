package ru.dagdelo.business05.domain.global.models

import com.google.gson.annotations.SerializedName

data class EditProfile(@SerializedName("full_name") val username: String)