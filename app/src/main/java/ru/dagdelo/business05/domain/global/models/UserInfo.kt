package ru.dagdelo.business05.domain.global.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    var name: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var password: String? = null,
    var birth: String? = null,
    var gender: Int? = null
): Parcelable