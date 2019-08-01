package ru.dagdelo.business05.presentation.global

import android.content.Context
import ru.dagdelo.business05.domain.ResourceManager
import javax.inject.Inject

/** Created by Kamil Abdulatipov on 22.06.2019. */

class AndroidResourceManager @Inject constructor(
    private val context: Context
) : ResourceManager {

    override fun getString(idRes: Int): String =
        context.getString(idRes)

    override fun getArrayOfStrings(resourceId: Int): Array<String> =
        context.resources.getStringArray(resourceId)
}