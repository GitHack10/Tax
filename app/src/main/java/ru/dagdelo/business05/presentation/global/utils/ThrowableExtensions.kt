package ru.dagdelo.business05.presentation.global.utils

import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.ResourceManager
import java.io.IOException

fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is IOException -> resourceManager.getString(R.string.error_network)
    else -> resourceManager.getString(R.string.error_unknown)
}