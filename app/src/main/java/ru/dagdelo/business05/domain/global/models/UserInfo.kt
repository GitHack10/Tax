package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserInfo(
    var user: User? = null
): Parcelable