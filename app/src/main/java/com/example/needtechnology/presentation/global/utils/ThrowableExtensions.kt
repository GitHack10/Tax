package com.example.needtechnology.presentation.global.utils

import com.example.needtechnology.R
import com.example.needtechnology.domain.ResourceManager
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}