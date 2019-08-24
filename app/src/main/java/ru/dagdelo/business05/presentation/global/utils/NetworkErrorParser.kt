package ru.dagdelo.business05.presentation.global.utils

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.ResourceManager
import ru.dagdelo.business05.domain.global.models.ApiError
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

    fun parseMessage(error: HttpException): ApiError {
        return try {
            error.response().errorBody()?.let { converter.convert(it) }
                ?: ApiError(resourceManager.getString(R.string.error_network_get_response))
        } catch (e: IOException) {
            ApiError(resourceManager.getString(R.string.error_network_convert_response))
        }
    }
}