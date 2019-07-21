package com.example.needtechnology.domain.global.mappers

import android.util.Base64
import java.nio.charset.StandardCharsets

fun authEncode(data: String) =
    "Basic ${Base64.encodeToString(data.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)}"