package com.example.needtechnology.presentation.global.utils

import com.example.needtechnology.R
import com.example.needtechnology.domain.ResourceManager
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkErrorParser @Inject constructor(
    retrofit: Retrofit,
    private val resourceManager: ResourceManager
) {

    private val converter: Converter<ResponseBody, ApiError> =
        retrofit.responseBodyConverter(ApiError::class.java, emptyArray<Annotation>())

    fun parseMessage(error: HttpException): String {
        val apiError = try {
            error.response().errorBody()?.let { converter.convert(it) }
                ?: ApiError(resourceManager.getString(R.string.error_network_get_response))
        } catch (e: IOException) {
            ApiError(resourceManager.getString(R.string.error_network_convert_response))
        }

        val errors = apiError.errors
        return if (!errors.isNullOrEmpty()) errors.first().message else apiError.message
    }
}

private data class ApiError(
    @SerializedName("message") val message: String,
    @SerializedName("errors") val errors: List<NestedError>? = null
) {
    data class NestedError(@SerializedName("message") val message: String)
}