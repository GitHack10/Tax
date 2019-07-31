package com.example.needtechnology.domain.global.models

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("message") val message: String,
    @SerializedName("errors") val errors: List<NestedError>? = null
) {
    data class NestedError(@SerializedName("message") val message: String)
}