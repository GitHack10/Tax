package com.example.needtechnology.data.global.netwotk.utils

import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLConnection

fun createBasicAuthHeader(username: String, password: String): String = Credentials.basic(username, password)

fun String.createPart(): RequestBody = RequestBody.create(MultipartBody.FORM, this)

fun File.createPart(partName: String): MultipartBody.Part {
    val contentType = MediaType.parse(URLConnection.guessContentTypeFromName(this.name))
    val requestFile = RequestBody.create(contentType, this)
    return MultipartBody.Part.createFormData(partName, this.name, requestFile)
}